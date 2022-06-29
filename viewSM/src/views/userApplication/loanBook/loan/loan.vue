<style>
  .keyWords-form{
    width: 100%;
    /*text-align: center;*/
  }
  /*.key-input{*/
  /*  width: 30vw;*/
  /*  transition: all 0.5s;*/
  /*}*/
  /*.center-input input{*/
  /*  text-align: center;*/
  /*}*/
  /*.key-input:hover{*/
  /*  width: 50vw;*/
  /*  !*border-color: #1ab394;*!*/
  /*  !*outline-color: #1ab394;*!*/
  /*  box-shadow: 0 0 6px #ccc;*/
  /*}*/
</style>
<template>
  <div class="app-container">
    <el-form ref="queryForm"
             @submit.native.prevent
             :inline="true"
             label-width="68px">
      <el-form-item  prop="name" label-width="0">
        <el-input
          style="width: 30vw"
          placeholder="请输入借阅码......"
          clearable
          size="medium"
          v-model="code"
          prefix-icon="el-icon-search"
          @keydown.enter.native="getStockBook"
        />
      </el-form-item>
      <el-form-item >
        <el-button @click="getStockBook" icon="el-icon-search" type="primary">搜  索</el-button>
      </el-form-item>
      <el-form-item v-if="false">
        <el-button @click="scanVisible=true">扫一扫</el-button>
      </el-form-item>
    </el-form>
    <div v-if="showData"  v-loading="loading">
      <el-row :gutter="0" type="flex" justify="justify">
        <el-col :span="10">
          <Picture :my-stype="'width: 400px; height: 400px;box-shadow: 0 0 5px #666'" :srcList="data.imgSrc" v-if="undefined != data.imgSrc"/>
        </el-col>
        <el-divider direction="vertical"/>
        <el-col >
          <el-descriptions
            border
            :column="2"
            class="margin-top"
            title="图书基本信息" >
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                图书名称
              </template>
              {{data.book.name}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                图书编号
              </template>
              {{data.book.code}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                版本号码
              </template>
              {{data.book.version}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                发行数量
              </template>
              {{data.book.circulation}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                图书ISBN
              </template>
              {{data.book.isbn}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                系列丛书
              </template>
              {{data.book.series}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                发行数量
              </template>
              {{data.book.circulation}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                图书售价
              </template>
              {{data.book.price}}
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions
            border :column="1">
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                关键信息
              </template>
              <el-tag
                style="margin: 3px;"
                v-if="undefined!=data.book.keyWords&&null!=data.book.keyWords"
                v-for="(tag ,index) in data.book.keyWords.split(',')">{{tag}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-office-building"></i>
                详细说明
              </template>
              <el-popover
                placement="top-start"
                title="详细说明"
                width="200"
                trigger="hover"
                :content="data.book.mark">
                  <span slot="reference">{{data.book.mark.substring(0,50)}}{{data.book.mark.length>50?'......':''}}</span>
              </el-popover>
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-divider direction="vertical"/>
        <el-col>
          <el-row >
            <cl-col>
              <el-descriptions
                border class="margin-top" title="作者信息" :column="2" >
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-user"></i>
                    作者名称
                  </template>
                  {{data.auth.name}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    作者别名
                  </template>
                  {{data.auth.nickName}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    英文名称
                  </template>
                  {{data.auth.englishName}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-tickets"></i>
                    作者性别
                  </template>
                  <sex-label :sex="data.auth.sex"/>
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    作者国籍
                  </template>
                  <tag effect="dark" type="danger">{{data.auth.country}}</tag>
                </el-descriptions-item>
              </el-descriptions>
              <el-descriptions
                border :column="1">
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    人物标签
                  </template>
                  <el-tag
                    style="margin: 3px"
                    effect="dark"
                    type="success"
                    v-if="undefined!=data.auth.identity&&null!=data.auth.identity"
                    v-for="(tag ,index) in data.auth.identity.split(',')">{{tag}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-office-building"></i>
                    人物事迹
                  </template>
                  <el-popover
                    placement="top-start"
                    title="详细说明"
                    width="500"
                    trigger="hover"
                    :content="data.auth.synopsis">
                    <span slot="reference">{{data.auth.synopsis.substring(0,50)}}{{data.auth.synopsis.length>50?'......':''}}</span>
                  </el-popover>
                </el-descriptions-item>
              </el-descriptions>
            </cl-col>
          </el-row>
          <el-row>
            <cl-col>
              <el-descriptions
                border class="margin-top" title="出版社信息" :column="3" >
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-user"></i>
                    机构名称
                  </template>
                  {{data.press.name}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    联系电话
                  </template>
                  {{data.press.phone}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-email"></i>
                    联系邮箱
                  </template>
                  {{data.press.email}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    联系地址
                  </template>
                  {{data.press.addr}}
                </el-descriptions-item>
              </el-descriptions>
              <el-descriptions
                border :column="1">
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-office-building"></i>
                    机构简介
                  </template>
                  <el-popover
                    placement="top-start"
                    title="详细说明"
                    width="500"
                    trigger="hover"
                    :content="data.press.mark">
                    <span slot="reference">{{data.press.mark.substring(0,50)}}{{data.press.mark.length>50?'......':''}}</span>
                  </el-popover>
                </el-descriptions-item>
              </el-descriptions>
            </cl-col>
          </el-row>
        </el-col>
      </el-row>
      <el-divider content-position="left">
        <el-tag
          style="margin-right: 10px;"
          v-if="undefined !=data.parentDept && null != data.parentDept"
          effect="dark"
          type="info"
          v-for="(tag,index) in data.parentDept">{{tag.deptName}}</el-tag>
        <el-tag
          style="margin-left: 5px;"
          v-if="undefined !=data.dept && null != data.dept"
          effect="dark"
          type="success">{{data.dept.deptName}}</el-tag>
      </el-divider>
      <el-row>
        <el-col>
          <el-descriptions class="margin-top" title="库位详情信息" :column="2" border>
            <template slot="extra">
              <el-button size="small" type="warning" @click="BorrowBook">立即借阅</el-button>
            </template>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-user"></i>
                库位编号
              </template>
              <el-tag>{{data.stock.stockCode}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                详细位置
              </template>
              {{data.stock.localtion}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                书架层号
              </template>
              {{data.storey.name}}<el-tag style="margin-left: 10px;" size="small" type="info" effect="dark">{{data.storey.code}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                层次标题
              </template>
              {{data.storey.title}}
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                书架编号
              </template>
              {{data.shelf.name}}<el-tag style="margin-left: 10px;" size="small" type="info" effect="dark">{{data.shelf.code}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-tickets"></i>
                书架标题
              </template>
              {{data.shelf.title}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
            <data-group :data="data.file"/>
        </el-col>
      </el-row>
    </div>
    <el-empty description="查無此書" v-if="!showData"></el-empty>

    <el-dialog v-if="false" :visible.sync="scanVisible" >
      <QRCode v-on:qrCodeValue="sacnResult"/>
    </el-dialog>
  </div>
</template>

<script>
    import QRCode from "../../base/QRCode";
    import {appendSrcPiffect} from "@/api/userApplication/file"
    import {browBook,brow} from '@/api/userApplication/bookStock';
    import Picture from "../../base/Picture";
    import DataGroup from "../../base/dataGroup";
    import SexLabel from "../../base/SexLabel";
    export default {
        name: "loan",
      components: {SexLabel, DataGroup, Picture, QRCode},
      data(){
          return{
            scanVisible:false,
            code:'',
            showData:false,
            loading:false,
            data:{
              book:null,
              press:null,
              auth:null,
              claz:null,
              bookStock:null,
              stock:null,
              shelf:null,
              storey:null,
              dept:null,
              parentDept:[],
              file:[],
              imgSrc:[],
            },
            browCode:'',
          }
      },
      methods:{
        sacnResult(scanRes){
          this.$modal.msgSuccess(scanRes);
        },
        getStockBook(){
          if (undefined == this.code || null == this.code || ''==this.code.trim()){
            this.$modal.msgWarning("没有输入借阅码...");
            return;
          }
          browBook(this.code).then(res=>{
            this.loading =true;
            this.showData = false;
            if (undefined != res.data && null != res.data) {
              this.data = res.data;
              if (undefined != this.data.imgSrc && null != this.data.imgSrc){
                for (let i=0;i<this.data.imgSrc.length;i++){
                  this.data.imgSrc[i] = appendSrcPiffect(this.data.imgSrc[i]);
                }
              }
              this.browCode = this.code;
              this.showData = true;
              this.loading = false;
            }
          }).catch(_=>{
            this.data = {};
            this.showData = false;
            // this.$modal.msgWarning("获取失败");
          });
        },

        BorrowBook(){
          brow(this.browCode).then(res=>{
            let result = res.data===false?'失败':'成功';
            this.$notify({
              title: '操作提醒',
              message: '图书'+this.data.book.name+'借出'+result+'！'+res.msg,
              type: res.data===false?'error':'success',
              duration:0,
            });
          }).catch(err=>{

          });
        }

      }
    }
</script>

<style scoped>

</style>
