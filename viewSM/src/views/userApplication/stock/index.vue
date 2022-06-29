<style>
  .stock-con{
    width: 200px;
    height: 200px;
    margin: 10px;
  }
  .stock-card{
    /*background-color: #1ab394;*/
    height: 200px;
    border-radius: 10px;
    border: 2px solid #ccc;
  }
  .bookshelf-tab tr:hover{
    color: #04A78C;
    font-weight: bolder;
  }
  .stock-time {
    font-size: 13px;
    color: #999;
  }

  .stock-txt{
    padding: 14px;
    transform: translateY(-70px);
    background-color: #fff;
    opacity: 0.84;
    transition: opacity 0.5s;
  }
  .stock-txt:hover{
    opacity: 1;
  }
  .stock-bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .stock-image {
    width: 100%;
    display: block;
    cursor: crosshair;
  }
  .stock-add{
    cursor: pointer;
    background-size: 100% 100%;
    background-position: 50% 50%;
    background-repeat: no-repeat;
    width: 80%;
    margin: 20px;
    box-shadow: 0 0 5px #666;
    border-radius: 50%;
  }
  .stock-card-add{
    border-radius: 50%;
    transition: all 0.5s;
  }
  .stock-card-add:hover{
    box-shadow: 0 0 5px #1ab394;
  }
  .stock-clearfix:before,
  .stock-clearfix:after {
    display: table;
    content: "";
  }

  .stock-clearfix:after {
    clear: both;
  }
  .el-tree .el-tree-node__label:hover{ font-weight: bolder; color: #04A78C; }
</style>
<template>
  <div class="app-container">
    <el-row  type="flex">
      <el-col style="width: 15%">
        <!--部门数据-->
        <dept-tree v-on:selectNode="selectDept"/></el-col>
      <el-col
      style="border-right: 1px solid #04A78C !important;width: 15%">
        <div class="grid-content bg-purple" style="margin-left: 1vh;max-height: 80vh;min-height: 80vh;">
          <el-table
            class="bookshelf-tab"
            v-loading="shelf.loadingBookshelf"
            :data="shelf.bookshelfList"
            stripe="stripe"
            highlight-current-row="highlight-current-row"
            style="max-height: 80vh;min-height: 80vh;overflow-y: auto;"
            @row-click="clickBookshelf" >
            <el-table-column label="书架列表"  align="center" prop="name" fixed >
              <template slot-scope="scope">
                  <span style="cursor: pointer !important;" >{{scope.row.name}}（{{scope.row.code}}）</span>
              </template>
            </el-table-column>
          </el-table>
      </div></el-col>
      <el-col style="width: 70%"><div class="grid-content bg-purple-light">
        <el-divider content-position="left">
          <i class="el-icon-location-information"></i>
          <i style="color: #666;font-weight: lighter;" v-if="undefined == shelf.queryBookshelfParams.deptName">请先选择部门</i>
          <el-tag
            v-if="shelf.queryBookshelfParams.curStoreyNode !== undefined"
            :type="shelf.queryBookshelfParams.curStoreyNode.type"
            effect="dark" style="margin-right: 5px;">
            {{ shelf.queryBookshelfParams.curStoreyNode.label }}
          </el-tag>
          <el-tag
            v-if="shelf.queryBookshelfParams.bookShelfNode !== undefined"
            type="danger"
            effect="dark" style="margin-right: 5px;">
            {{ shelf.queryBookshelfParams.bookShelfNode.label }}
          </el-tag>
          <el-tag
            v-for="lab in shelf.queryBookshelfParams.deptAllNode"
            :key="lab"
            :type="lab.type"
            effect="dark"
            v-if="lab.label != undefined || '' !== lab.label"
            style="margin-left: 3px;">{{lab.label}}</el-tag>
        </el-divider>
        <el-divider content-position="center" v-if="undefined == storey.storeyList || storey.storeyList.length<1">
          <i style="color: #aaa;">请先从书架列表中选择书架</i>
        </el-divider>

        <div
          style="margin-left: 5px;overflow: auto;max-height: 75vh;min-height: 75vh">
          <el-menu
            :default-active="storey.storeyList[0].id"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            v-if="undefined != storey.storeyList && storey.storeyList.length>0">
            <el-menu-item :index="storey.id"
                          v-for="storey in storey.storeyList"
                          :key="storey"
            ><label style="color: #666;font-weight: bolder;">{{storey.name}}（{{storey.code}}）</label></el-menu-item>
          </el-menu>


          <el-row>
            <el-divider
              content-position="left"
              v-if="undefined != stock.stockList && stock.stockList.length>0"
            >库位详情(当前书架层合计库位：<el-tag effect="dark" style="margin: 2px;border-radius: 50%;">{{stock.stockList.length}}</el-tag>条)</el-divider>

            <el-col
              :span="8" v-for="(stock, index ) in stock.stockList"
              :key="stock" :offset="index > 0 ? 2 : 0"
              class="stock-con">
              <el-card :body-style="{ padding: '0px' }" class="stock-card">
                <img
                  :src="stock.url"
                  class="stock-image">
                <div class="stock-txt" @click="showStockDetial(stock.id)">
                  <span>
                    <el-tag effect="dark" type="danger" style="font-weight:bolder;cursor: pointer;opacity: 1;">
                        <el-tooltip class="item" effect="dark" :content="stock.stockCode" placement="top-start">
                          <span>{{stock.stockCode.substring(0,10)}}...</span>
                        </el-tooltip>
                    </el-tag>
                  </span>
                  <div class="stock-bottom stock-clearfix" >
                    <time class="stock-time">{{stock.createTime}}</time>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col  :span="8" class="stock-con stock-con-add" v-if="undefined != storey.storeyList && storey.storeyList.length>0">
              <el-card :body-style="{ padding: '0px' }" class="stock-card stock-card-add">
                <img src="/dev-api/profile/avatar/2022/system/icon_add_stock.png" class="stock-add stock-image" @click="handleStockAdd">
              </el-card>
            </el-col>
          </el-row>
        </div>
      </div></el-col>
    </el-row>
    <!-- 添加或修改图书库存信息对话框 -->
    <el-dialog :title="stock.title" :visible.sync="stock.open" width="800px" append-to-body style="z-index: 99;">
      <el-divider content-position="left">
        <el-tag
          v-if="shelf.queryBookshelfParams.curStoreyNode !== undefined"
          :type="shelf.queryBookshelfParams.curStoreyNode.type"
          effect="dark" style="margin-right: 5px;">
          {{ shelf.queryBookshelfParams.curStoreyNode.label }}
        </el-tag>
        <el-tag
          v-if="shelf.queryBookshelfParams.bookShelfNode !== undefined"
          type="danger"
          effect="dark" style="margin-right: 5px;">
          {{ shelf.queryBookshelfParams.bookShelfNode.label }}
        </el-tag>
        <el-tag
          v-for="lab in shelf.queryBookshelfParams.deptAllNode"
          :key="lab"
          :type="lab.type"
          effect="dark"
          v-if="lab.label != undefined || '' !== lab.label"
          style="margin-left: 3px;">{{lab.label}}</el-tag>
      </el-divider>
      <el-form ref="form" :model="stock.form" :rules="stock.rules" label-width="80px">
        <el-form-item label-width="120px" label="库存编码" prop="entityCode">
          <el-input v-model="stock.form.stockCode" placeholder="请输入库存实体编码" />
        </el-form-item>
        <el-form-item label-width="120px" label="库位" prop="localtion">
          <el-input type="textarea"
                    :rows="2"
                    maxlength="250"
                    v-model="stock.form.localtion"
                    placeholder="请输入库位具体位置，例如：xx楼xx库xx夹xx排..." />
        </el-form-item>
        <el-form-item label-width="120px" label="备注">
            <el-input type="textarea"
                      :rows="5"
                      maxlength="2000"
                      v-model="stock.form.mark"
                      placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label-width="120px" label="库存状态" prop="status">
             <el-switch
              v-model="stock.form.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
        <el-button @click="toAddMore" type="danger">我想批量添加</el-button>
      </div>
    </el-dialog>
    <!-- 批量添加图书库存信息对话框 -->
    <el-dialog :title="stocks.title" :visible.sync="stocks.open" width="800px" append-to-body style="z-index: 99;">
      <el-divider content-position="left">
        <el-tag
          v-if="shelf.queryBookshelfParams.curStoreyNode !== undefined"
          :type="shelf.queryBookshelfParams.curStoreyNode.type"
          effect="dark" style="margin-right: 5px;">
          {{ shelf.queryBookshelfParams.curStoreyNode.label }}
        </el-tag>
        <el-tag
          v-if="shelf.queryBookshelfParams.bookShelfNode !== undefined"
          type="danger"
          effect="dark" style="margin-right: 5px;">
          {{ shelf.queryBookshelfParams.bookShelfNode.label }}
        </el-tag>
        <el-tag
          v-for="lab in shelf.queryBookshelfParams.deptAllNode"
          :key="lab"
          :type="lab.type"
          effect="dark"
          v-if="lab.label != undefined || '' !== lab.label"
          style="margin-left: 3px;">{{lab.label}}</el-tag>
      </el-divider>
      <el-form ref="forms" :model="stocks.form" :rules="stocks.rules" label-width="80px">
        <el-form-item label-width="120px" label="库存编码前缀" prop="entityCode">
          <el-input v-model="stocks.form.stockCode" placeholder="请输入库存实体编码" />
        </el-form-item>
        <el-form-item label-width="120px" label="库位名称前缀" prop="localtion">
          <el-input type="textarea"
                    :rows="2"
                    maxlength="250"
                    v-model="stocks.form.localtion"
                    placeholder="请输入库位具体位置，例如：xx楼xx库xx夹xx排..." />
        </el-form-item>
        <el-form-item label-width="120px" label="库位备注信息">
            <el-input type="textarea"
                      :rows="5"
                      maxlength="2000"
                      v-model="stocks.form.mark"
                      placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label-width="120px" label="库存使用状态" prop="status">
             <el-switch
              v-model="stocks.form.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="0">
          </el-switch>
        </el-form-item>
        <el-form-item label-width="120px" label="库存添加数量" prop="number">
          <el-slider v-model="stocks.form.number" :min="1" :max="50" show-input></el-slider>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForms">确 定</el-button>
        <el-button @click="cancels">取 消</el-button>
      </div>
    </el-dialog>

    <el-drawer
      :title="stock.detail.title"
      :visible.sync="stock.detail.open"
      direction="ttb"
      size="90%">
        <el-tabs v-model="active.activeMap[active.activeIndex].name" @tab-click="handleClick" >
          <el-tab-pane name="first" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-menu"></i> 库位存书信息</span>
            <el-row >
              <el-col span="16">
                <el-row v-if="undefined != stock.detail.entity.book">
                  <el-col :push="1" span="8">
                    <Picture :srcList="stock.detail.entity.picList" v-if="undefined != stock.detail.entity.picList"/>
<!--                    <el-descriptions column="1" style="margin-bottom: 3px; text-align: center;" :colon="false">-->
<!--                      <el-descriptions-item v-if="undefined != stock.detail.entity.book && undefined != stock.detail.entity.book.name">-->
<!--                        {{stock.detail.entity.book.name}}-->
<!--                      </el-descriptions-item>-->
<!--                    </el-descriptions>-->
                  </el-col>
                  <el-col span="16">
                    <el-divider content-position="left">
                      <i class="el-icon-s-management">当前库位存书信息</i>
                    </el-divider>
                    <el-descriptions column="1" style="margin-bottom: 3px;">
                      <el-descriptions-item  label="库位位置" v-if="undefined != stock.detail.location">
                        <el-tag
                          v-for="lab in stock.detail.location"
                          :key="lab"
                          :type="lab.type"
                          effect="dark"
                          v-if="lab.label != undefined || '' !== lab.label"
                          style="margin-left: 3px;">{{lab.label}}</el-tag>
                      </el-descriptions-item>
                    </el-descriptions>
                    <el-descriptions column="2" contentStyle="font-size: 16px;font-wight:bold;">
                      <el-descriptions-item  label="图书名称" v-if="undefined != stock.detail.entity.book.name">
                        {{stock.detail.entity.book.name}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="图书编码" v-if="undefined != stock.detail.entity.book.code">
                        {{stock.detail.entity.book.code}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="图书类型">
                        <el-tag size="small" effect="dark" type="success" v-if="undefined != stock.detail.entity.bookType.name " style="margin-right: 5px;">
                          {{stock.detail.entity.bookType.name}}
                        </el-tag>
                        <el-tag size="small" effect="dark" type="warning" v-if="undefined != stock.detail.entity.book.code ">
                          {{stock.detail.entity.bookType.code}}
                        </el-tag>
                      </el-descriptions-item>
                      <el-descriptions-item  label="ISBN" v-if="undefined != stock.detail.entity.book.isbn">
                        {{stock.detail.entity.book.isbn}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="发行量" v-if="undefined != stock.detail.entity.book.circulation">
                        {{stock.detail.entity.book.circulation}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="版本号" v-if="undefined != stock.detail.entity.book.version">
                        {{stock.detail.entity.book.version}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="丛书系列" v-if="undefined != stock.detail.entity.book.series">
                        {{stock.detail.entity.book.series}}
                      </el-descriptions-item>
                      <el-descriptions-item  label="单价" v-if="undefined != stock.detail.entity.book.price">
                        {{stock.detail.entity.book.price}}
                      </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions column="1">
                      <el-descriptions-item  label="关键字" v-if="undefined != stock.detail.entity.book.keyWords">
                        <el-tag
                          v-for="lab in stock.detail.entity.book.keyWords.split(',')"
                          :key="lab"
                          type="success"
                          effect="dark"
                          v-if="lab != undefined || '' !== lab"
                          style="margin-left: 3px;">{{lab}}</el-tag>
                      </el-descriptions-item>
                    </el-descriptions>

                    <el-descriptions column="1">
                      <el-descriptions-item  label="其他信息" v-if="undefined != stock.detail.entity.book.other">
                        {{stock.detail.entity.book.other}}
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-col>
                </el-row>
                <el-row>
                    <el-col  push="1" v-if="undefined != stock.detail.entity.press">
                      <el-divider></el-divider>
                      <data-group :data="stock.detail.entity.file" :group-style="''"/>
                    </el-col>
                </el-row>
              </el-col>
              <el-col span="8">
                <el-row >
                  <el-col v-if="undefined != stock.detail.entity.auth">
                    <el-row>
                      <el-col>
                        <el-divider content-position="left">
                          <i class="el-icon-s-custom">作者信息</i>
                        </el-divider>
                        <el-descriptions column="2"  contentStyle="font-size: 16px;font-wight:bold;">
                          <el-descriptions-item  label="原名" v-if="undefined != stock.detail.entity.auth.name">
                            <i class="el-icon-user"></i>
                            {{stock.detail.entity.auth.name}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="性别" v-if="undefined != stock.detail.entity.auth.sex">
                            <SexLabel :sex="stock.detail.entity.auth.sex"/>
                          </el-descriptions-item>
                          <el-descriptions-item  label="国籍" v-if="undefined != stock.detail.entity.auth.country">
                            <i class="el-icon-s-check"></i>
                            {{stock.detail.entity.auth.country}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="别名">
                            <i class="el-icon-help"></i>
                            {{stock.detail.entity.auth.nickName}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="英文名" v-if="undefined != stock.detail.entity.auth.englishName">
                            <i class="el-icon-user"></i>
                            {{stock.detail.entity.auth.englishName}}
                          </el-descriptions-item>
                        </el-descriptions>

                        <el-descriptions  column="1" contentStyle="font-size: 16px;font-wight:bold;">
                          <el-descriptions-item  label="社会身份" v-if="undefined != stock.detail.entity.auth.identity">
                            <el-tag
                              v-for="lab in stock.detail.entity.auth.identity.split(',')"
                              :key="lab"
                              type="success"
                              effect="dark"
                              v-if="lab != undefined || '' !== lab"
                              style="margin-left: 3px;">{{lab}}</el-tag>
                          </el-descriptions-item>
                        </el-descriptions>
                        <el-descriptions  column="1" contentStyle="font-size: 12px;font-wight:bold;">
                          <el-descriptions-item  label="简介" v-if="undefined != stock.detail.entity.auth.synopsis">
                            {{stock.detail.entity.auth.synopsis}}
                          </el-descriptions-item>
                        </el-descriptions>
                      </el-col>
                    </el-row>
                    <el-row>
                      <el-col>
                        <el-divider content-position="left">
                          <i class="el-icon-school">图书出版社信息</i>
                        </el-divider>
                        <el-descriptions column="1" contentStyle="font-size: 16px;font-wight:bold;">
                          <el-descriptions-item  label="出版社名称" v-if="undefined != stock.detail.entity.press.name">
                            {{stock.detail.entity.press.name}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="地址" v-if="undefined != stock.detail.entity.press.addr">
                            {{stock.detail.entity.press.addr}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="邮箱" v-if="undefined != stock.detail.entity.press.email">
                            {{stock.detail.entity.press.email}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="联系电话" v-if="undefined != stock.detail.entity.press.phone">
                            {{stock.detail.entity.press.phone}}
                          </el-descriptions-item>
                          <el-descriptions-item  label="简介" v-if="undefined != stock.detail.entity.press.mark">
                            {{stock.detail.entity.press.mark}}
                          </el-descriptions-item>
                        </el-descriptions>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
            <el-row v-if="undefined == stock.detail.entity.book || undefined == stock.detail.entity.auth ||undefined == stock.detail.entity.press">
              <el-col>
                <el-empty description="此库位暂无存书信息"></el-empty>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane name="second" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-edit-outline"></i> 库位基本信息</span>
            <div style="height: 65vh;max-height: 65vh;overflow: auto;overflow-x: hidden;">
              <el-row :gutter="40">
                  <el-col :span="20" push="1">
                    <el-divider
                      v-if="undefined != stock.detail.location"
                      content-position="right">
                      <el-tag
                        v-for="lab in stock.detail.location"
                        :key="lab"
                        :type="lab.type"
                        effect="dark"
                        v-if="lab.label != undefined || '' !== lab.label"
                        style="margin-left: 3px;">{{lab.label}}</el-tag>
                    </el-divider>
                    <el-empty
                      v-if="undefined == stock.detail.entity.stock"
                      description="库位信息为空"></el-empty>
                    <el-descriptions
                      v-if="undefined != stock.detail.entity.stock"
                      class="margin-top"
                      :column="3"
                      direction="horizontal" size="medium">
                      <template slot="extra">
                        <el-switch
                          v-model="stock.detail.query.status"
                          active-value="1"
                          inactive-value="0"
                          active-text="启用"
                          inactive-text="停用"
                          @change="statusChange">
                        </el-switch>
                      </template>
                      <el-descriptions-item label="库位编码">
                        <el-tag effect="dark" type="danger">
                          {{stock.detail.entity.stock.stockCode}}
                        </el-tag>
                      </el-descriptions-item>
                      <el-descriptions-item label="是否存放图书">
                        <HasBook :has-book="stock.detail.entity.stock.hasBook"/>
                      </el-descriptions-item>
                      <el-descriptions-item
                        v-if="undefined != stock.detail.entity.bookStock"
                        label="图书是否在库"
                        :span="2">
                        <BookIsLoan :is-loan="stock.detail.entity.bookStock.hasLoan"/>
                      </el-descriptions-item>
                    </el-descriptions>
                    <el-descriptions
                      v-if="undefined != stock.detail.entity.stock"
                      class="margin-top"
                      :column="1"
                      direction="horizontal" size="medium">
                      <el-descriptions-item label="具体位置">
                        <el-input
                          type="textarea"
                          :rows="2"
                          style="width: 500px"
                          v-model="stock.detail.query.localtion"
                        ></el-input>
                      </el-descriptions-item>
                      <el-descriptions-item label="备注信息">
                        <el-input
                          type="textarea"
                          :rows="2"
                          style="width: 500px"
                          v-model="stock.detail.query.mark"
                        ></el-input>
                      </el-descriptions-item>
                      <el-descriptions-item label="备注信息">
                        <el-button type="primary" @click="saveChange">保存修改</el-button>
                        <el-button
                          v-if="undefined == this.stock.detail.entity.book"
                          type="text"
                          @click="delStock">删除库位</el-button>
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-col>
              </el-row>
              <el-row :gutter="40">
                <el-col :push="1" >
                  <el-divider content-position="left">
                    <i class="el-icon-edit">库位创建人信息</i>
                  </el-divider>
                  <el-empty v-if="undefined == stock.detail.entity.createStockU" image-size="0" description="此库位暂无创建人信息"></el-empty>
                  <el-descriptions
                    v-if="undefined != stock.detail.entity.createStockU"
                    class="margin-top"
                    :column="1"
                    direction="horizontal"
                    :colon="false"
                    size="medium">
                    <el-descriptions-item
                      v-if="undefined != stock.detail.entity.createStockU.avatar && stock.detail.entity.createStockU.avatar!==''">
                      <el-image
                        style="width: 50px; height: 50px"
                        shape="square"
                        fit="scale-down"
                        :src="appendSrcPiffect(stock.detail.entity.createStockU.avatar)"/>
                    </el-descriptions-item>
                  </el-descriptions>
                  <el-descriptions
                    v-if="undefined != stock.detail.entity.createStockU"
                    class="margin-top"
                    :column="2"
                    direction="horizontal"
                    size="medium">
                    <el-descriptions-item label="创建人">{{stock.detail.entity.createStockU.userName}}</el-descriptions-item>
                    <el-descriptions-item label="别名">{{stock.detail.entity.createStockU.nickName}}</el-descriptions-item>
                    <el-descriptions-item label="联系邮箱" :span="2">{{stock.detail.entity.createStockU.email}}</el-descriptions-item>
                    <el-descriptions-item label="性别">
                      <SexLabel :sex="stock.detail.entity.createStockU.sex"/>
                    </el-descriptions-item>
                    <el-descriptions-item label="介绍">{{stock.detail.entity.createStockU.remark}}</el-descriptions-item>
                  </el-descriptions>
                </el-col>
              </el-row>
              <el-row :gutter="40">
              <el-col :push="1">
                <el-divider content-position="left">
                  <i class="el-icon-refresh">最近更新人信息</i>
                </el-divider>
                <el-empty
                  image-size="0"
                  v-if="undefined == stock.detail.entity.updateStockU"
                  description="此库位暂无更新人信息"></el-empty>
                <el-descriptions
                  v-if="undefined != stock.detail.entity.updateStockU"
                  class="margin-top"
                  :column="1"
                  direction="horizontal"
                  :colon="false" size="medium">
                  <el-descriptions-item >
                    <el-image
                      style="width: 50px; height: 50px"
                      shape="square"
                      fit="scale-down"
                      v-if="undefined != stock.detail.entity.updateStockU.avatar && stock.detail.entity.updateStockU.avatar!==''"
                      :src="appendSrcPiffect( stock.detail.entity.updateStockU.avatar)"/>
                  </el-descriptions-item>
                </el-descriptions>
                <el-descriptions
                  v-if="undefined != stock.detail.entity.updateStockU"
                  class="margin-top"
                  :column="2"
                  direction="horizontal"
                  size="medium">
                  <el-descriptions-item label="创建人">{{stock.detail.entity.updateStockU.userName}}</el-descriptions-item>
                  <el-descriptions-item label="别名">{{stock.detail.entity.updateStockU.nickName}}</el-descriptions-item>
                  <el-descriptions-item label="联系邮箱" :span="2">{{stock.detail.entity.updateStockU.email}}</el-descriptions-item>
                  <el-descriptions-item label="性别">
                    <SexLabel :sex="stock.detail.entity.updateStockU.sex"/>
                  </el-descriptions-item>
                  <el-descriptions-item label="介绍">{{stock.detail.entity.updateStockU.remark}}</el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
            </div>
          </el-tab-pane>
          <el-tab-pane name="third" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-tickets"></i> 库存变更记录</span>
            <el-empty
              v-if="undefined == stock.detail.entity.stockLog || stock.detail.entity.stockLog.length<=0"
              description="抱歉！暂无库存变更记录..."></el-empty>
            <div
              class="block"
              v-if="undefined != stock.detail.entity.stockLog && stock.detail.entity.stockLog.length>0"
              style="height: 65vh;max-height: 65vh;overflow: auto;overflow-x: hidden;">
              <el-divider content-position="right"><i class="el-icon-sort"></i>当前变更记录累计共：{{stock.detail.entity.stockLog.length}}条</el-divider>
              <el-timeline>
                <el-timeline-item
                  :color="index ===0?'#0bbd87':'#ccc'"
                  :size="index ===0?'large':'normal'"
                  v-for="(log,index) in stock.detail.entity.stockLog"
                  :key="log"
                  :timestamp="log.stockLog.createTime"
                  placement="top">
                  <el-card>
                    <p>
                      操作人：{{log.createUser.userName}}
                      <el-tag effect="dark" type="warning" v-if="log.stockLog.isLoan == -1">图书已借出</el-tag>
                      <el-tag effect="dark" type="success" v-if="log.stockLog.isLoan == 1">图书已归还</el-tag>
                      <el-tag effect="dark" type="danger" v-if="log.stockLog.isLoan == -2">图书出库</el-tag>
                      <el-tag effect="dark" type="info" v-if="log.stockLog.isLoan == 2">图书入库</el-tag>
                    </p>
                    <p>操作时间：{{log.stockLog.createTime}}</p>
                    <p><el-tag effect="plain" type="success" v-if="log.stockLog.operateType == 1">在线操作</el-tag></p>
                    <p><el-tag effect="plain" v-if="log.stockLog.operateType == 2">线下操作</el-tag></p>
                    <p><el-tag effect="plain" type="warning" v-if="log.stockLog.operateType == 3">他人代办</el-tag></p>
                    <p><el-tag effect="plain" type="info" v-if="log.stockLog.operateType == 0">其他方式办理</el-tag></p>
                    <p>处理人: {{log.otherUser.userName}} </p>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-tab-pane>
          <el-tab-pane name="fourth" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-paperclip"></i> 其他</span>
            <el-row :gutter="40" >
              <el-col :span="18">
                <el-empty description="抱歉！暂无其他信息可查阅..."></el-empty>
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-row  v-if="false" :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8" :push="19">
              <el-button-group>
                <el-button type="primary" class="tab-but" icon="el-icon-arrow-left" :disabled="active.activeMap[active.activeIndex].isLastDisabled" @click="toLastTab">上一页</el-button>
                <el-button type="primary" class="tab-but" :disabled="active.activeMap[active.activeIndex].isNextDisabled" @click="toNextTab">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
              </el-button-group>
            </el-col>
          </el-row>
        </el-tabs>
    </el-drawer>
  </div>
</template>

<script>
import { listBookshelf } from "@/api/userApplication/bookshelf";
import { appendSrcListPiffect } from "@/api/userApplication/file";
import { getLogs } from "@/api/userApplication/log";
import { listStock, getStock, delStock, addStock,addMoreStock, updateStock, detail } from "@/api/userApplication/stock";
import { treeselect } from "@/api/system/dept";
import { listStorey } from "@/api/userApplication/storey";
import Detail from "./msgpro/Detail"
import Treeselect from "@riophae/vue-treeselect";
import Template from "../template/index";
import SexLabel from "../base/SexLabel";
import BookIsLoan from "../base/BookIsLoan";
import HasBook from "../base/HasBook";
import Picture from "../base/Picture";
import DeptTree from "../base/libraryElement";
import DataGroup from "../base/dataGroup";
export default {
  name: "Stock",
  components: {DeptTree,HasBook, BookIsLoan, Template, Detail,Picture,SexLabel,DataGroup},
  data() {
    return {

      shelf:{
        loadingBookshelf:false,
        // 书架信息表格数据
        bookshelfList: undefined,
        bookshelfTotal:0,
        // 查询参数
        queryBookshelfParams: {
          pageNum: 1,
          pageSize: 100000,
          code: null,
          name: null,
          title: null,
          bookType: null,
          mark: null,
          status: null,
          dept: null,
          createPersonal: null,
          updatePersonal: null,
          bookShelfNode:undefined
        },
      },

      storey:{
        // 查询书架层参数
        queryStoreyParams: {
          pageNum: 1,
          pageSize: 100000,
          code: null,
          name: null,
          title: null,
          bookType: null,
          mark: null,
          status: null,
          bookshelf: null,
          createPersonal: null,
          updatePersonal: null
        },
        // 遮罩层
        loadingStorey: true,
        // 选中数组
        idsStorey: [],
        // 非单个禁用
        singleStorey: true,
        // 非多个禁用
        multipleStorey: true,
        // 显示搜索条件
        showSearchStorey: true,
        // 总条数
        totalStorey: 0,
        // 书架层信息表格数据
        storeyList: undefined,
        // 弹出层标题
        titleStorey: "",
        // 是否显示弹出层
        openStorey: false,
      },

      stock:{
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 图书库存信息表格数据
        stockList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 100000,
          bookId: null,
          mechanismId: null,
          entityCode: null,
          stockLocaltion: null,
          hasLoan: null,
          status: null,
          createPersonal: null,
          updatePersonal: null
        },
        detail:{
          open:false,
          id:0,//当前查询库位id
          title:'',
          location:[],
          entity:{
            createStockU: {},//库位创建人
            updateStockU: {},//库位更新
            createStockBookU: {},//库位图书入库信息创建人
            updateStockBookU: {},//库位图书入库信息更新人
            useBookU: {},//库位图书当前借阅人
            stockMsg: {},//当前库位信息
            bookStock: {},//当前库位存书信息
            book: {},//图书基本信息
            auth: {},//图书作者
            bookType: {},//图书类别信息
            press: {},//出版社信息信息
            fileList: [],//图书相关路径列表
            file: [],//图书相关附件信息
            stockLog:[],
          },//查询实体信息
          query:{
            mark:undefined,
            localtion:undefined,
            status:undefined,
          },
        },
        // 表单参数
        form: {},
        rules: {
          // dept:[{required: true, message: "书架所属部门不能为空", trigger: "blur"}],
          // bookClassName:[{required: true, message: "书架存书类别不能为空", trigger: "blur"}],
          stockCode:[
            { required: true, message: "库存编码不能为空", trigger: "blur" },
            { min: 2, max: 30, message: '书架名称长度必须介于 2 和 30 之间的字符串组成', trigger: 'blur' }
          ]
        }
      },
      stocks:{
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 图书库存信息表格数据
        stockList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 100000,
          bookId: null,
          mechanismId: null,
          entityCode: null,
          stockLocaltion: null,
          hasLoan: null,
          status: null,
          createPersonal: null,
          updatePersonal: null
        },
        detail:{
          open:false,
          id:0,//当前查询库位id
          title:'',
          location:[],
          entity:{
            createStockU: {},//库位创建人
            updateStockU: {},//库位更新
            createStockBookU: {},//库位图书入库信息创建人
            updateStockBookU: {},//库位图书入库信息更新人
            useBookU: {},//库位图书当前借阅人
            stockMsg: {},//当前库位信息
            bookStock: {},//当前库位存书信息
            book: {},//图书基本信息
            auth: {},//图书作者
            bookType: {},//图书类别信息
            press: {},//出版社信息信息
            fileList: [],//图书相关路径列表
            file: [],//图书相关附件信息
            stockLog:[],
          },//查询实体信息
          query:{
            mark:undefined,
            localtion:undefined,
            status:undefined,
          },
        },
        // 表单参数
        form: {},
        rules: {
          // dept:[{required: true, message: "书架所属部门不能为空", trigger: "blur"}],
          // bookClassName:[{required: true, message: "书架存书类别不能为空", trigger: "blur"}],
          stockCode:[
            { required: true, message: "库存编码不能为空", trigger: "blur" },
            { min: 2, max: 30, message: '书架名称长度必须介于 2 和 30 之间的字符串组成', trigger: 'blur' }
          ]
        }
      },
      active: {
        activeIndex: 0,
        activeMap: [
          {name: 'first', isLastDisabled: true, isNextDisabled: false},
          {name: 'second', isLastDisabled: false, isNextDisabled: false},
          {name: 'third', isLastDisabled: false, isNextDisabled: false},
          {name: 'fourth', isLastDisabled: false, isNextDisabled: true},
        ],
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    /** 查询图书库存信息列表 */
    getStockList() {
      this.stock.loading = true;
      this.stock.stockList = [];
      this.stock.total =0;
      listStock(this.stock.queryParams).then(response => {
        this.stock.stockList = response.rows;
        this.stock.total = response.total;
        this.stock.loading = false;
      });
    },
    getStockListByStoreyId(storeyId){
      this.stock.queryParams.storeId = storeyId;
      this.getStockList();
    },
    // 取消按钮
    cancel() {
      this.stock.open = false;
      this.reset();
    },
    // 取消批量按钮
    cancels() {
      this.stocks.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.stock.form = {
        id: null,
        bookId: null,
        mechanismId: null,
        entityCode: null,
        stockLocaltion: null,
        hasLoan: null,
        status: 0,
        createTime: null,
        createPersonal: null,
        updateTime: null,
        updatePersonal: null
      };
      this.resets();
      //this.stock.resetForm("form");
    },
    resets() {
      this.stocks.form = {
        id: null,
        bookId: null,
        mechanismId: null,
        entityCode: null,
        stockLocaltion: null,
        hasLoan: null,
        status: 0,
        createTime: null,
        createPersonal: null,
        updateTime: null,
        updatePersonal: null
      };
      //this.stock.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.stock.queryParams.pageNum = 1;
      // this.getStockList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.stock.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.stock.ids = selection.map(item => item.id)
      this.stock.single = selection.length!==1
      this.stock.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleStockAdd() {
      this.reset();
      this.stock.open = true;
      this.stock.title = "增加图书库位信息";
    },
    /** 修改按钮操作 */
    handleStockUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getStock(id).then(response => {
        this.stock.form = response.data;
        this.stock.open = true;
        this.stock.title = "修改图书库存信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      //设置当前书架层
      this.stock.form.storeId=this.shelf.queryBookshelfParams.curStoreyNode.id;

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.stock.form.id != null) {
            updateStock(this.stock.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.stock.open = false;
              this.getStockListByStoreyId(this.shelf.queryBookshelfParams.curStoreyNode.id);
            });
          } else {
            addStock(this.stock.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.stock.open = false;
              this.getStockListByStoreyId(this.shelf.queryBookshelfParams.curStoreyNode.id);
            });
          }
        }
      });
    },
    toAddMore(){
      this.stock.open = false;
      this.reset();
      this.stocks.open = true;
    },
    /** 批量提交按钮 */
    submitForms() {
      //设置当前书架层
      this.stocks.form.storeId=this.shelf.queryBookshelfParams.curStoreyNode.id;

      this.$refs["forms"].validate(valid => {
        if (valid) {
            addMoreStock(this.stocks.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.stocks.open = false;
              this.getStockListByStoreyId(this.shelf.queryBookshelfParams.curStoreyNode.id);
            });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.stock.ids;
      this.$modal.confirm('是否确认删除图书库存信息编号为"' + ids + '"的数据项？').then(function() {
        return delStock(ids);
      }).then(() => {
        // this.getStockList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/stock/export', {
        ...this.stock.queryParams
      }, `stock_${new Date().getTime()}.xlsx`)
    },


    cleanAllList(){
      this.stock.stockList = [];//清空当前书架层的库位信息列表
      this.storey.storeyList = undefined;//清空当前书架的书架层信息列表
      this.shelf.bookshelfList = undefined;//清空当前部门的书架列表
      this.shelf.queryBookshelfParams.curStoreyNode = undefined;//清空当前已选中的书架层
      this.shelf.queryBookshelfParams.bookShelfNode = undefined;//清空当前已选中的书架
    },
    // 部门树节点单击事件
    selectDept(selectData) {
      this.cleanAllList();
      this.shelf.queryBookshelfParams.dept = selectData.curData.id;
      this.shelf.queryBookshelfParams.deptName =selectData.curData.label;

      //获取当前结点全体祖先结点信息
      this.shelf.queryBookshelfParams.deptAllNode = [];
      let isStart = true;
      if(undefined != selectData.nodes ) {
        for (let i = 0; i < selectData.nodes.length; i++) {
          let node = selectData.nodes[i];
          if (undefined != node.id) {
            this.shelf.queryBookshelfParams.deptAllNode.push({
              'id': node.id,
              'label': node.label,
              'type': isStart ? 'warning' : 'success'
            });
          }
          if (isStart) {
            isStart = false;
          }
        }
      }
      this.handleQuery();
      this.getBookshelfList();
    },

    /** 查询书架信息列表 */
    getBookshelfList() {
      this.shelf.loading = true;
      listBookshelf(this.shelf.queryBookshelfParams).then(response => {
        this.shelf.bookshelfList = response.rows;
        this.shelf.total = response.total;
        this.shelf.loading = false;
      });
    },

    /**单击书架，获取书架层列表*/
    clickBookshelf(row, column,event){
      this.shelf.queryBookshelfParams.bookShelfNode = {};//当前选择的书架
      this.shelf.queryBookshelfParams.bookShelfNode = {'id': row.id, 'label': row.name+'('+ row.code+')','type':'danger'};
      this.shelf.loading = true;//重新加载
      this.shelf.loading = false;

      this.storey.queryStoreyParams.bookshelf = row.id;
      this.getStoreyList();
    },


    /** 查询书架层信息列表 */
    getStoreyList() {
      this.storey.loadingStorey = true;

      listStorey(this.storey.queryStoreyParams).then(response => {
        this.storey.storeyList = response.rows;
        this.storey.totalStorey = response.total;

        this.stock.stockList = [];//清空之前浏览的库位信息
        this.stock.total = 0;
        //默认打开显示第一个书架层的库位信息
        if (undefined != this.storey.storeyList && this.storey.storeyList.length>0){
          this.getStockListByStoreyId(this.storey.storeyList[0].id);
          //定位到第一个书架层
          this.setCurStorey(this.storey.storeyList[0]);
        }

        this.storey.loadingStorey = false;
      });
    },
    /**定位到当前书架层**/
    setCurStorey(cur){
      let curStorey = cur.name + '（'+cur.code+'）';
      this.shelf.queryBookshelfParams.curStoreyNode = {};//当前选择的书架
      this.shelf.queryBookshelfParams.curStoreyNode = {'id': cur.id, 'label': curStorey,'type':'warning'};
      this.shelf.loading = true;//重新加载
      this.shelf.loading = false;
    },
    //选中对应书架层，查询此层的库位信息
    handleSelect(key, keyPath){
      for (let index = 0;index < this.storey.storeyList.length;index++) {
        if (this.storey.storeyList[index].id === key){
          this.setCurStorey(this.storey.storeyList[index]);
          break;
        }
      }
      //找到当前对应书架层上的库位信息
      this.getStockListByStoreyId(key);
    },
    //查询当前库位的存书及其基本信息
    async showStockDetial(id){
      this.active.activeIndex = 0;

      this.stock.detail.id=id;
      this.stock.detail.title='库位基本信息';

      detail(id).then(res=>{
        this.stock.detail.entity = res.data;


        //库位可修改的参数
        this.stock.detail.query.mark = res.data.stock.mark;
        this.stock.detail.query.localtion = res.data.stock.localtion;
        this.stock.detail.query.status = res.data.stock.status;


        //文件路径处理，添加环境前缀
        this.stock.detail.entity.file.picFile = this.appendSrcListPiffect( this.stock.detail.entity.file.picFile);
        this.stock.detail.entity.file.docFile = this.appendSrcListPiffect( this.stock.detail.entity.file.docFile);
        this.stock.detail.entity.file.pdfFile = this.appendSrcListPiffect( this.stock.detail.entity.file.pdfFile);
        this.stock.detail.entity.file.videoFile = this.appendSrcListPiffect( this.stock.detail.entity.file.videoFile);
        this.stock.detail.entity.file.otherFile = this.appendSrcListPiffect( this.stock.detail.entity.file.otherFile);

        this.stock.detail.entity.picList = this.getSrcList(res.data.file.picFile);//处理图片信息列表得到图片路径列表

        this.stock.detail.location = [];
        this.stock.detail.location.push(this.shelf.queryBookshelfParams.curStoreyNode);
        this.stock.detail.location.push(this.shelf.queryBookshelfParams.bookShelfNode);


        for (let index =0;index<this.shelf.queryBookshelfParams.deptAllNode.length;index++){
          this.stock.detail.location.push(this.shelf.queryBookshelfParams.deptAllNode[index]);
        }

        this.stock.detail.open=true;
      });

    },
    saveChange(){
      if (
        this.stock.detail.entity.stock.mark === this.stock.detail.query.mark
        &&
        this.stock.detail.entity.stock.localtion === this.stock.detail.query.localtion
      ){
        this.$modal.confirm('检测到没有新的内容需要提交！');
        return;
      }
      //保存当前库位信息
      this.stock.detail.query.id=this.stock.detail.entity.stock.id;

      if (this.stock.detail.query.id != null) {
        updateStock(this.stock.detail.query).then(response => {
          this.stock.detail.entity.stock.mark = this.stock.detail.query.mark ;
          this.stock.detail.entity.stock.localtion = this.stock.detail.query.localtion
          this.$modal.msgSuccess("修改成功");
          this.stock.open = false;
          this.getStockListByStoreyId(this.shelf.queryBookshelfParams.curStoreyNode.id);
        });
      }
    },
    /** 状态修改*/
    statusChange() {
      if (undefined != this.stock.detail.entity.book){
        this.$modal.confirm('由于此库位已有图书入库信息，暂时无法停用！');
        this.stock.detail.query.status =  this.stock.detail.entity.stock.status;
        return;
      }

      let text = this.stock.detail.query.status == "1" ? "启用" : "停用";

      // this.$modal.confirm('确认要"' + text + '"这个编号为"' + this.stock.detail.entity.stock.stockCode + '"的库位吗？').then(()=> {
        let item = {
          id:this.stock.detail.entity.stock.id,
          status :this.stock.detail.query.status
        };
        updateStock( item).then(()=> {
          this.stock.detail.entity.stock.status = this.stock.detail.query.status;
          this.$modal.msgSuccess(text + "成功");
        }).catch(()=>{
          this.stock.detail.query.status = this.stock.detail.entity.stock.status ;
          this.$modal.msgSuccess(text + "失败");
        });
      // }).catch(function() {
      //   this.stock.detail.query.status = this.stock.detail.query.status == "0" ? "1" : "0";
      // });
    },
    delStock(){
      this.$modal.confirm('确认要删除这个编号为"' + this.stock.detail.entity.stock.stockCode + '"的库位吗？')
        .then(() =>{
          //删除当前库位信息
            return delStock(this.stock.detail.entity.stock.id);
        })
        .then(()=>{
          this.$modal.msgSuccess("删除成功");
          this.stock.open = false;
          this.getStockListByStoreyId(this.shelf.queryBookshelfParams.curStoreyNode.id);
          this.stock.detail.open = false;
        })
        .catch(()=>{
          this.$modal.msgError("操作失败");
        });
    },
    clockStock(){
      this.stock.detail.entity =  {};
      this.stock.detail.open=false;
    },
    appendSrcListPiffect(list){
      if (undefined == list){
        return list;
      }
      for (let index = 0;index<list.length;index++){
        list[index].url = this.appendSrcPiffect(list[index].url);
      }
      return list;
    },
    appendSrcPiffect(src){
      return process.env.VUE_APP_BASE_API + src;
    },
    getSrcList(file){
      let arr = [];
      if (undefined != file && file.length>0){
        for (let index = 0;index<file.length;index++){
          if(file[index].type===1) {
            arr.push(file[index].url);
          }
        }
      }
      return arr;
    },

    handleClick(tab, event) {
      if (tab.name === this.active.activeMap[2].name){
        this.getStockLog();
      }
      for (let i =0;i<this.active.activeMap.length;i++){
        if (tab.name === this.active.activeMap[i].name){
          this.active.activeIndex = i;
          break ;
        }
      }
    },
    toLastTab(){
      this.active.activeIndex -= 1;
      if (this.active.activeIndex<0){
        this.active.activeIndex = 0;
      }
    },
    toNextTab(){
      this.active.activeIndex += 1;
      if (this.active.activeIndex>=this.active.activeMap.length){
        this.active.activeIndex = this.active.activeMap.length-1;
      }
    },
    tabFactory(){

    },
    //获取库位基本信息
    getStockDetail(){

    },
    //获取库位图书出入记录
    getStockLog(){
      getLogs(this.stock.detail.entity.stock.id).then((res)=>{
        this.stock.detail.entity.stockLog = res.data;
      }).catch(()=>{
        this.stock.detail.entity.stockLog = [];
      })
    },
  }
};
</script>
