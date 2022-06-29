<style>
  .keyWords-form{
    width: 100%;
    text-align: center;
  }
  .key-input{
    width: 30vw;
    transition: all 0.5s;
  }
  .center-input input{
    text-align: center;
  }
  .key-input:hover{
    width: 50vw;
    /*border-color: #1ab394;*/
    /*outline-color: #1ab394;*/
    box-shadow: 0 0 6px #ccc;
  }

  .stock-con{
    width: 280px;
    height: 300px;
    margin: 20px;
  }
  .stock-card{
    /*background-color: #1ab394;*/
    height: 300px;
    width: 300px;

    background-position: 100% 100%;
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
    height: 100px;
    padding: 14px;
    transform: translateY(-100px);
    background-color: #fff;
    opacity: 0.5;
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
    height: 100%;
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
</style>
<template>
  <div class="app-container">
    <el-form ref="queryForm"
             @submit.native.prevent
             :inline="true"
             v-show="query.showSearch"
             label-width="68px">
      <el-form-item class="keyWords-form" prop="name" label-width="0">
        <el-input
          class="key-input center-input"
          style=""
          v-model="query.key"
          placeholder="关键字"
          clearable
          size="medium"
          prefix-icon="el-icon-search"
          @keydown.enter.native="reflush"
        />
      </el-form-item>
    </el-form>
    <div v-loading="loading">
      <el-row>
        <el-col>
          <pagination
            v-show="query.total>0"
            :total="query.total"
            :page.sync="query.queryParams.pageNum"
            :limit.sync="query.queryParams.pageSize"
            @pagination="getList"
            background
            layout="prev, pager, next"
          />
        </el-col>
      </el-row>
      <el-row>
        <el-col
          v-for="(row,index) in query.list"
          :key="row" :offset="index > 0 ? 2 : 0"
          class="stock-con">
          <el-card
            class="stock-card"
            :body-style="{ padding: '0px',display:'inline-block',width:'100%',height:'100%' }"
          >
            <Picture
              class="stock-image"
              :src-list="row.srcList"
              :my-stype="query.picStyle"/>
            <div class="stock-txt" style="padding: 14px;"  @click="showData(row)">
              <el-tag>{{row.clazCode}}</el-tag>
              <el-divider direction="vertical"/>
              <el-tooltip :content="row.bookName">
                <span style="font-size: 18px;font-weight: bold;">
                  {{row.bookName.substring(0,12)}}{{row.bookName.length>12?'...':''}}
                </span>
              </el-tooltip>
              <div class="bottom clearfix">
                <el-tooltip :content="row.authName">
                  <time class="stock-time">{{row.authName.substring(0,10)}}</time>
                </el-tooltip>
                <el-divider direction="vertical"/>
                <span>{{parseTime(row.bookPublicationData, '{y}-{m}-{d}') }}</span>
              </div>
              <div class="bottom clearfix">
                <el-button style="float: right;margin-right: 10px;" type="text" class="button" @click="showData(row)">点击查看</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <el-dialog
      :visible.sync="show.show"
      :title="show.title"
      destroy-on-close
      append-to-body>
      <el-divider content-position="left" v-if="undefined!=show.data.parentDept&&null!=show.data.parentDept">
        <el-tag v-for="(dept,index) in show.data.parentDept" style="margin-left: 10px;">{{dept.deptName}}</el-tag>
        >
        <el-tag style="margin-left: 10px;">{{show.data.deptName}}</el-tag>
      </el-divider>
      <el-divider
        content-position="left"
        v-if="undefined==show.data.parentDept||null==show.data.parentDept||show.data.parentDept.length<1">
        <el-tag style="margin-left: 10px;" type="danger" effect="dark">暂无图书机构收录此书</el-tag>
      </el-divider>
      <el-descriptions class="margin-top" title="图书基本信息" :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            图书编码
          </template>
          {{show.data.bookCode}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            图书ISBN
          </template>
          {{show.data.bookISBN}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            图书系列
          </template>
          {{show.data.bookSeries}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            出版日期
          </template>
          <span>{{ parseTime(show.data.bookPublicationData, '{y}年{m}月{d}日') }}</span>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            发行数量
          </template>
          {{show.data.bookCirculation}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            图书版本
          </template>
          {{show.data.bookVersion}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            图书单价
          </template>
          {{show.data.bookPrice}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            图书类别
          </template>
          {{show.data.clazCode}} - {{show.data.clazName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            可取数量
          </template>
          <el-tag effect="dark" :type="show.data.nowNumber>show.data.saveNumber/10?'success':'warning'">{{show.data.nowNumber}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            在库数量
          </template>
          <el-tag effect="dark">{{show.data.saveNumber}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" :column="1" border>
        <el-descriptions-item v-if="undefined != show.data.bookKeyWords && null !=show.data.bookKeyWords">
          <template slot="label">
            <i class="el-icon-location-outline"></i>
          </template>
          <el-tag size="small" v-for="tag in show.data.bookKeyWords.split(',')" style="margin-left: 5px;">{{tag}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item
          label-style="width:40px;"
          v-if="undefined!=show.data.bookMark && null!=show.data.bookMark">
          <template slot="label">
            <i class="el-icon-tickets"></i>
          </template>
          <el-popover
            v-if="show.data.bookMark.length>80"
            placement="top-start"
            title="人物介绍"
            trigger="hover"
            width="800"
            :content="show.data.bookMark">
            <span slot="reference">{{show.data.bookMark.substring(0,80)}}......</span>
          </el-popover>
          <span v-if="show.data.bookMark.length<=80">{{show.data.bookMark}}</span>
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions class="margin-top" title="作者基本信息" :column="2" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            作者名称
          </template>
          {{show.data.authName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            作者别名
          </template>
          {{show.data.authNickName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            英文名称
          </template>
          {{show.data.authEnglishName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            国家信息
          </template>
          {{show.data.authCountry}}
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" :column="1" border>
        <el-descriptions-item v-if="undefined !=show.data.authIdentity && null != show.data.authIdentity">
          <el-tag size="small" v-for="(tag,index) in show.data.authIdentity.split(',')" style="margin-left: 5px;">{{tag}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item v-if="undefined!=show.data.authSynopsis && null!=show.data.authSynopsis">
          <el-popover
            v-if="show.data.authSynopsis.length>80"
            placement="top-start"
            title="人物介绍"
            trigger="hover"
            width="800"
            :content="show.data.authSynopsis">
            <span slot="reference">{{show.data.authSynopsis.substring(0,80)}}......</span>
          </el-popover>
          <span v-if="show.data.authSynopsis.length<=80">{{show.data.authSynopsis}}</span>
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions class="margin-top" title="出版社" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            出版社名称
          </template>
          {{show.data.pressName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            联系邮箱
          </template>
          {{show.data.pressEmail}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            联系电话
          </template>
          {{show.data.pressPhone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            联系地址
          </template>
          <el-tag size="small">{{show.data.pressAddr}}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building"></i>
            详细信息
          </template>
          {{show.data.pressMark}}
        </el-descriptions-item>
      </el-descriptions>

      <el-descriptions
        class="margin-top"
        title="相关部门信息"
        v-if="undefined!=show.data.deptId&&null!=show.data.deptId"
        :column="4" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            部门名称
          </template>
          {{show.data.deptName}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            核心人物
          </template>
          {{show.data.deptLeader}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            联系电话
          </template>
          {{show.data.deptPhone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            联系邮箱
          </template>
          <el-tag size="small">{{show.data.deptEmail}}</el-tag>
        </el-descriptions-item>
      </el-descriptions>

      <el-dialog
        width="30%"
        title="图书借阅码"
        :visible.sync="show.code.visible"
        append-to-body
        center
      >
        <el-input class="center-input" v-model="show.code.code" readonly></el-input>
        <div slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            class="copy"
            :data-clipboard-text="show.code.code"
            @click="copyCode">一键复制</el-button>
        </div>
      </el-dialog>

      <div slot="footer" class="dialog-footer">
        <el-button
          v-if="undefined!=show.data.parentDept&&null!=show.data.parentDept"
          type="primary"
          @click="getBookCode">获取图书借阅码</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import BookDetial from "./bookDetial";
import Template from "../../template/index";
import Picture from "../../base/Picture";
import Clipboard from 'clipboard';
import {keyWords} from '@/api/userApplication/book'
import {browBook, getCode} from '@/api/userApplication/bookStock'
    export default {
        name: "seacherBook",
      components: {Picture, Template, BookDetial},
      data(){
          return{
            loading:false,
            query:{
              showSearch:true,
              key:'',
              queryParams:{
                pageNum:1,
                pageSize:10,
              },
              picStyle:'width: 100%;display: block;cursor: crosshair;box-shadow: 0 0 5px #666',
              list:[],
              loading:false,
              total:0,
            },
            show:{
              show:false,
              title:'',
              data:{
                bookId:null,
                bookName:null,
                bookCode:null,
                bookType:null,
                bookLeavel:null,
                bookClaz:null,
                bookAuthor:null,
                bookMechanism:null,
                bookPublicationData:null,
                bookVersion:null,
                bookCirculation:null,
                bookISBN:null,
                bookSeries:null,
                bookKeyWords:null,
                bookPrice:null,
                bookMark:null,
                authName:null,
                authCountry:null,
                authNickName:null,
                authEnglishName:null,
                authSynopsis:null,
                authIdentity:null,
                clazCode:null,
                clazName:null,
                pressName:null,
                pressAddr:null,
                pressMark:null,
                pressEmail:null,
                pressPhone:null,
                deptName:null,
                deptLeader:null,
                deptPhone:null,
                deptEmail:null,
                deptId:null,
                nowNumber:0,
                saveNumber:0,
                parentDept:[],
                fileList:[],
                srcList:[]
              },
              code:{
                code:null,//图书借阅码
                visible:false,
              }
            }
          }
      },
      methods:{
        handleQuery(){
          this.getList();
        },
        reflush(){
          this.query.total = 0;
          this.query.queryParams.pageNum = 1;
          this.getList();
        },
        getList(){
          let data = {
            keyWords : this.query.key ,
            pageNum : this.query.queryParams.pageNum ,
            pageSize : this.query.queryParams.pageSize
          };
          this.loading = true;
          keyWords( data ).then(res=>{
            this.query.list = res.rows;
            this.query.total = res.total;

            if(undefined != this.query.list && null != this.query.list) {
              for (let i = 0; i < this.query.list.length; i++) {
                if (undefined == this.query.list[i].srcList || null == this.query.list[i].srcList) {
                  continue;
                }
                for (let j = 0; j < this.query.list[i].srcList.length; j++) {
                  this.query.list[i].srcList[j] = process.env.VUE_APP_BASE_API + this.query.list[i].srcList[j];
                }
              }
            }
            this.loading =false;
          }).catch(_=>{
            this.loading =false;
          });

        },
        showData(row){
          this.show.data = row;
          this.show.title = row.bookName;
          this.show.show= true;
        },
        //关闭
        cancel(){
          this.show.show= false;
        },
        //获取图书借书码
        getBookCode(){
          let query = {
            id:this.show.data.bookId,
          };
          getCode(query).then(res=>{
            this.show.code.code = res.data;
            this.show.code.visible = true;
          });
        },
        getDeptList(){

        },
        copyCode(){
          let clipboard = new Clipboard('.copy');
          clipboard.on('success', e => {
            console.log('复制成功');
            this.$modal.msgSuccess('复制成功,可前往图书借阅');
            // 释放内存
            clipboard.destroy();
            this.show.code.visible = false;
          });
          clipboard.on('error', e => {
            // 不支持复制
            console.log('该浏览器不支持自动复制');
            // 释放内存
            clipboard.destroy()
          });
        }
      }
    }
</script>

<style scoped>

</style>
