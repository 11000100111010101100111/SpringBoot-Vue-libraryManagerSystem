<style>
  .tab-but:hover{
    background-color: #E6B03C;
    border-color: #E6B03C;
  }
  .detail-label{
    font-size: 18px;
    font-weight: bold;
  }
</style>
<template>
        <el-tabs v-model="activeMap[activeIndex].name" @tab-click="handleClick" >
          <el-tab-pane name="first" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-menu"></i> 库位存书信息</span>
            <el-row :gutter="20">
              <el-col :span="6">
                <Picture :srcList="stock.entity.picList" v-if="undefined != stock.entity.picList"/>
                <el-descriptions column="1" style="margin-bottom: 3px; text-align: center;" :colon="false">
                  <el-descriptions-item v-if="undefined != stock.entity.book.name">
                    {{stock.entity.book.name}}
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>
              <el-col :span="18">
                <el-divider content-position="left">
                <i class="el-icon-s-management">当前库位存书信息</i>
              </el-divider>
                <el-descriptions column="1" style="margin-bottom: 3px;">
                  <el-descriptions-item  label="库位位置" v-if="undefined != stock.location">
                    <el-tag
                      v-for="lab in stock.location"
                      :key="lab"
                      :type="lab.type"
                      effect="dark"
                      v-if="lab.label != undefined || '' !== lab.label"
                      style="margin-left: 3px;">{{lab.label}}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
                <el-descriptions column="3" contentStyle="font-size: 16px;font-wight:bold;">
                  <el-descriptions-item  label="图书名称" v-if="undefined != stock.entity.book.name">
                    {{stock.entity.book.name}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="图书编码" v-if="undefined != stock.entity.createStockU.userName">
                    {{stock.entity.book.code}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="图书类型">
                    <el-tag size="small" effect="dark" type="success" v-if="undefined != stock.entity.book.code " style="margin-right: 5px;">
                      {{stock.entity.bookType.name}}
                    </el-tag>
                    <el-tag size="small" effect="dark" type="warning" v-if="undefined != stock.entity.book.code ">
                      {{stock.entity.bookType.code}}
                    </el-tag>
                  </el-descriptions-item>
                  <el-descriptions-item  label="ISBN" v-if="undefined != stock.entity.book.code">
                    {{stock.entity.book.code}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="发行量" v-if="undefined != stock.entity.book.circulation">
                    {{stock.entity.book.circulation}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="版本号" v-if="undefined != stock.entity.book.version">
                    {{stock.entity.book.version}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="丛书系列" v-if="undefined != stock.entity.book.series">
                    {{stock.entity.book.series}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="单价" v-if="undefined != stock.entity.book.price">
                    {{stock.entity.book.price}}
                  </el-descriptions-item>
                </el-descriptions>

                <el-descriptions column="1">
                  <el-descriptions-item  label="关键字" v-if="undefined != stock.entity.book.keyWords">
                    <el-tag
                      v-for="lab in stock.entity.book.keyWords.split(',')"
                      :key="lab"
                      type="success"
                      effect="dark"
                      v-if="lab != undefined || '' !== lab"
                      style="margin-left: 3px;">{{lab}}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>

                <el-descriptions column="1">
                  <el-descriptions-item  label="其他信息" v-if="undefined != stock.entity.book.other">
                    {{stock.entity.book.other}}
                  </el-descriptions-item>
                </el-descriptions>

                <el-descriptions column="1">
                  <data-group :data="stock.entity.file" :group-style="''"/>
<!--                  <el-descriptions-item content-style="margin:5px;" >-->
<!--                      <el-button-group>-->
<!--                        <el-button-->
<!--                          v-if="undefined != stock.entity.file.picFile"-->
<!--                          v-for="(file,index) in stock.entity.file.picFile"-->
<!--                          :key="file"-->
<!--                          type="primary"-->
<!--                          size="small"-->
<!--                          icon="el-icon-download"-->
<!--                        >图片类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>-->

<!--                        <el-button-->
<!--                          v-if="undefined != stock.entity.file.pdfFile"-->
<!--                          v-for="(file,index) in stock.entity.file.pdfFile"-->
<!--                          :key="file"-->
<!--                          type="primary"-->
<!--                          size="small"-->
<!--                          icon="el-icon-download"-->
<!--                        >pdf类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>-->
<!--                        <el-button-->
<!--                          v-if="undefined != stock.entity.file.docFile"-->
<!--                          v-for="(file,index) in stock.entity.file.docFile"-->
<!--                          :key="file"-->
<!--                          type="primary"-->
<!--                          size="small"-->
<!--                          icon="el-icon-download"-->
<!--                        >文档类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>-->
<!--                        <el-button-->
<!--                          v-if="undefined != stock.entity.file.videoFile"-->
<!--                          v-for="(file,index) in stock.entity.file.videoFile"-->
<!--                          :key="file"-->
<!--                          type="primary"-->
<!--                          size="small"-->
<!--                          icon="el-icon-download"-->
<!--                        >视频类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>-->
<!--                        <el-button-->
<!--                          v-if="undefined != stock.entity.file.otherFile"-->
<!--                          v-for="(file,index) in stock.entity.file.otherFile"-->
<!--                          :key="file"-->
<!--                          type="primary"-->
<!--                          size="small"-->
<!--                          icon="el-icon-download"-->
<!--                        >其他相关资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>-->
<!--                      </el-button-group>-->
<!--                  </el-descriptions-item>-->
<!--                    </template>-->
                </el-descriptions>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-divider content-position="left">
                  <i class="el-icon-s-custom">作者信息</i>
                </el-divider>
                <el-descriptions column="5" contentStyle="font-size: 16px;font-wight:bold;">
                  <el-descriptions-item  label="原名" v-if="undefined != stock.entity.auth.name">
                    {{stock.entity.auth.name}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="性别" v-if="undefined != stock.entity.auth.sex">
                    <SexLabel :sex="stock.entity.auth.sex"/>
                  </el-descriptions-item>
                  <el-descriptions-item  label="国籍" v-if="undefined != stock.entity.auth.country">
                    {{stock.entity.auth.country}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="别名">
                    {{stock.entity.auth.nickName}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="英文名" v-if="undefined != stock.entity.auth.englishName">
                    {{stock.entity.auth.englishName}}
                  </el-descriptions-item>
                </el-descriptions>

                <el-descriptions  column="1" contentStyle="font-size: 16px;font-wight:bold;">
                  <el-descriptions-item  label="社会身份" v-if="undefined != stock.entity.auth.identity">
                    <el-tag
                      v-for="lab in stock.entity.auth.identity.split(',')"
                      :key="lab"
                      type="success"
                      effect="dark"
                      v-if="lab != undefined || '' !== lab"
                      style="margin-left: 3px;">{{lab}}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
                <el-descriptions  column="1" contentStyle="font-size: 12px;font-wight:bold;">
                  <el-descriptions-item  label="简介" v-if="undefined != stock.entity.auth.synopsis">
                    {{stock.entity.auth.synopsis}}
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>

              <el-col :span="12">
                <el-divider content-position="left">
                  <i class="el-icon-s-custom">作者信息</i>
                </el-divider>
                <el-descriptions column="5" contentStyle="font-size: 16px;font-wight:bold;">
                  <el-descriptions-item  label="原名" v-if="undefined != stock.entity.auth.name">
                    {{stock.entity.auth.name}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="性别" v-if="undefined != stock.entity.auth.sex">
                    <SexLabel :sex="stock.entity.auth.sex"/>
                  </el-descriptions-item>
                  <el-descriptions-item  label="国籍" v-if="undefined != stock.entity.auth.country">
                    {{stock.entity.auth.country}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="别名">
                    {{stock.entity.auth.nickName}}
                  </el-descriptions-item>
                  <el-descriptions-item  label="英文名" v-if="undefined != stock.entity.auth.englishName">
                    {{stock.entity.auth.englishName}}
                  </el-descriptions-item>
                </el-descriptions>

                <el-descriptions  column="1" contentStyle="font-size: 16px;font-wight:bold;">
                  <el-descriptions-item  label="社会身份" v-if="undefined != stock.entity.auth.identity">
                    <el-tag
                      v-for="lab in stock.entity.auth.identity.split(',')"
                      :key="lab"
                      type="success"
                      effect="dark"
                      v-if="lab != undefined || '' !== lab"
                      style="margin-left: 3px;">{{lab}}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
                <el-descriptions  column="1" contentStyle="font-size: 12px;font-wight:bold;">
                  <el-descriptions-item  label="简介" v-if="undefined != stock.entity.auth.synopsis">
                    {{stock.entity.auth.synopsis}}
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane name="second" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-edit-outline"></i> 库位基本信息</span>
            配置管理---{{auth.name}}
          </el-tab-pane>
          <el-tab-pane name="third" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-tickets"></i> 库存变更记录</span>
            库存变更记录
          </el-tab-pane>
          <el-tab-pane name="fourth" style="min-height: 60vh;">
            <span slot="label"><i class="el-icon-paperclip"></i> 其他</span>
            其他
          </el-tab-pane>

          <el-row :gutter="20" style="margin-bottom: 10px;">
            <el-col :span="8" :push="19">
              <el-button-group>
                <el-button type="primary" class="tab-but" icon="el-icon-arrow-left" :disabled="activeMap[activeIndex].isLastDisabled" @click="toLastTab">上一页</el-button>
                <el-button type="primary" class="tab-but" :disabled="activeMap[activeIndex].isNextDisabled" @click="toNextTab">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
              </el-button-group>
            </el-col>
          </el-row>
        </el-tabs>

</template>

<script>
import Picture from "../../base/Picture";
import SexLabel from "../../base/SexLabel";
import { getAuth } from "@/api/userApplication/auth";
    export default {
      props: {
        stock: {
          type: Object
        }
      },
      name: "Detail",
      components:{Picture,SexLabel},
      data(){
          return{
            activeIndex: 0,
            activeMap:[
              {name:'first',isLastDisabled:true,isNextDisabled:false},
              {name:'second',isLastDisabled:false,isNextDisabled:false},
              {name:'third',isLastDisabled:false,isNextDisabled:false},
              {name:'fourth',isLastDisabled:false,isNextDisabled:true},
            ],
            auth:{},
            // stock:{
            //   open:false,
            //   id:0,//当前查询库位id
            //   title:'',
            //   location:[],
            //   entity:{
            //     createStockU: undefined,//库位创建人
            //     updateStockU: undefined,//库位更新
            //     createStockBookU: undefined,//库位图书入库信息创建人
            //     updateStockBookU: undefined,//库位图书入库信息更新人
            //     useBookU: undefined,//库位图书当前借阅人
            //     stockMsg: undefined,//当前库位信息
            //     bookStock: undefined,//当前库位存书信息
            //     book: undefined,//图书基本信息
            //     auth: undefined,//图书作者
            //     bookType: undefined,//图书类别信息
            //     mechanism: undefined,//出版社信息信息
            //     fileList: [],//图书相关路径列表
            //     file: [],//图书相关附件信息
            //   },//查询实体信息
            // },
          }
      },
      created() {
        // this.openDetail();
      },
      methods: {
        handleClick(tab, event) {
          for (let i =0;i<this.activeMap.length;i++){
            if (tab.name === this.activeMap[i].name){
              this.activeIndex = i;
              return ;
            }
          }

          // this.$modal.msgSuccess('');
          this.auth.name = "111";
          getAuth(this.$parent.stock.detail.id).then(response => {
            this.auth = response.data;
          });
        },
        toLastTab(){
            this.activeIndex -= 1;
        },
        toNextTab(){
          this.activeIndex += 1;
        },

        // formatNumberIndex(num){
        //   return formatNumber(num);
        // }
      }
    }
</script>

<style scoped>

</style>
