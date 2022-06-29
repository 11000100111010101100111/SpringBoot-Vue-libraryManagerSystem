<template>
    <div>
      <span slot="label"><i class="el-icon-menu"></i> 库位存书信息</span>
      <el-row :gutter="40" v-if="undefined != detail.entity.book">
        <el-col :span="6" :push="1">
          <Picture :srcList="detail.entity.bookFileSrc" v-if="undefined != detail.entity.bookFileSrc"/>
          <el-descriptions column="1" style="margin-bottom: 3px; text-align: center;" :colon="false">
            <el-descriptions-item v-if="undefined != detail.entity.book && undefined != detail.entity.book.name">
              {{detail.entity.book.name}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-col :push="1" :span="16">
          <el-divider content-position="left">
            <i class="el-icon-s-management">图书信息</i>
          </el-divider>
          <el-descriptions column="3" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="图书名称" v-if="undefined != detail.entity.book.name">
              {{detail.entity.book.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="图书编码" v-if="undefined != detail.entity.book.code">
              {{detail.entity.book.code}}
            </el-descriptions-item>
            <el-descriptions-item  label="图书类型">
              <el-tag size="small" effect="dark" type="success" v-if="undefined != detail.entity.bookType.name " style="margin-right: 5px;">
                {{detail.entity.bookType.name}}
              </el-tag>
              <el-tag size="small" effect="dark" type="warning" v-if="undefined != detail.entity.bookType.code ">
                {{detail.entity.bookType.code}}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item  label="ISBN" v-if="undefined != detail.entity.book.isbn">
              {{detail.entity.book.isbn}}
            </el-descriptions-item>
            <el-descriptions-item  label="发行量" v-if="undefined != detail.entity.book.circulation">
              {{detail.entity.book.circulation}}
            </el-descriptions-item>
            <el-descriptions-item  label="版本号" v-if="undefined != detail.entity.book.version">
              {{detail.entity.book.version}}
            </el-descriptions-item>
            <el-descriptions-item  label="丛书系列" v-if="undefined != detail.entity.book.series">
              {{detail.entity.book.series}}
            </el-descriptions-item>
            <el-descriptions-item  label="单价" v-if="undefined != detail.entity.book.price">
              {{detail.entity.book.price}}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions column="1">
            <el-descriptions-item  label="关键字" v-if="undefined != detail.entity.book.keyWords">
              <el-tag
                v-for="lab in detail.entity.book.keyWords.split(',')"
                :key="lab"
                type="success"
                effect="dark"
                v-if="lab != undefined || '' !== lab"
                style="margin-left: 3px;">{{lab}}</el-tag>
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions column="1">
            <el-descriptions-item  label="其他信息" v-if="undefined != detail.entity.book.other">
              {{detail.entity.book.other}}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions column="1">
            <el-descriptions-item content-style="margin:5px;" >
              <el-button-group>
                <el-button
                  v-if="undefined != detail.entity.bookFile.picFile"
                  v-for="(file,index) in detail.entity.bookFile.picFile"
                  :key="file"
                  type="primary"
                  size="small"
                  icon="el-icon-download"
                >图片类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>

                <el-button
                  v-if="undefined != detail.entity.bookFile.pdfFile"
                  v-for="(file,index) in detail.entity.bookFile.pdfFile"
                  :key="file"
                  type="primary"
                  size="small"
                  icon="el-icon-download"
                >pdf类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>
                <el-button
                  v-if="undefined != detail.entity.bookFile.docFile"
                  v-for="(file,index) in detail.entity.bookFile.docFile"
                  :key="file"
                  type="primary"
                  size="small"
                  icon="el-icon-download"
                >文档类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>
                <el-button
                  v-if="undefined != detail.entity.bookFile.videoFile"
                  v-for="(file,index) in detail.entity.bookFile.videoFile"
                  :key="file"
                  type="primary"
                  size="small"
                  icon="el-icon-download"
                >视频类资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>
                <el-button
                  v-if="undefined != detail.entity.bookFile.otherFile"
                  v-for="(file,index) in detail.entity.bookFile.otherFile"
                  :key="file"
                  type="primary"
                  size="small"
                  icon="el-icon-download"
                >其他相关资源-{{index+1}}<a href="http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png"/></el-button>
              </el-button-group>
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row :gutter="40">
        <el-col v-if="undefined != detail.entity.auth" :push="1" :span="15">
          <el-divider content-position="left">
            <i class="el-icon-s-custom">作者信息</i>
          </el-divider>
          <el-descriptions column="3"  contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="原名" v-if="undefined != detail.entity.auth.name">
              <i class="el-icon-user"></i>
              {{detail.entity.auth.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="性别" v-if="undefined != detail.entity.auth.sex">
              <SexLabel :sex="detail.entity.auth.sex"/>
            </el-descriptions-item>
            <el-descriptions-item  label="国籍" v-if="undefined != detail.entity.auth.country">
              <i class="el-icon-s-check"></i>
              {{detail.entity.auth.country}}
            </el-descriptions-item>
            <el-descriptions-item  label="别名">
              <i class="el-icon-help"></i>
              {{detail.entity.auth.nickName}}
            </el-descriptions-item>
            <el-descriptions-item  label="英文名" v-if="undefined != detail.entity.auth.englishName">
              <i class="el-icon-user"></i>
              {{detail.entity.auth.englishName}}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions  column="1" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="社会身份" v-if="undefined != detail.entity.auth.identity">
              <el-tag
                v-for="lab in detail.entity.auth.identity.split(',')"
                :key="lab"
                type="success"
                effect="dark"
                v-if="lab != undefined || '' !== lab"
                style="margin-left: 3px;">{{lab}}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions  column="1" contentStyle="font-size: 12px;font-wight:bold;">
            <el-descriptions-item  label="简介" v-if="undefined != detail.entity.auth.synopsis">
              {{detail.entity.auth.synopsis}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-col v-if="undefined != detail.entity.press" :span="8" :push="1">
          <el-divider content-position="left">
            <i class="el-icon-school">图书出版社信息</i>
          </el-divider>
          <el-descriptions column="1" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="出版社名称" v-if="undefined != detail.entity.press.name">
              {{detail.entity.press.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="地址" v-if="undefined != detail.entity.press.addr">
              {{detail.entity.press.addr}}
            </el-descriptions-item>
            <el-descriptions-item  label="邮箱" v-if="undefined != detail.entity.press.email">
              {{detail.entity.press.email}}
            </el-descriptions-item>
            <el-descriptions-item  label="联系电话" v-if="undefined != detail.entity.press.phone">
              {{detail.entity.press.phone}}
            </el-descriptions-item>
            <el-descriptions-item  label="简介" v-if="undefined != detail.entity.press.mark">
              {{detail.entity.press.mark}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row
        :gutter="40"
        v-if="undefined == detail.entity.book || undefined == detail.entity.auth ||undefined == detail.entity.press">
        <el-col :span="18">
          <el-empty description="暂无此图书信息"></el-empty>
        </el-col>
      </el-row>
    </div>
</template>

<script>
  import { getBookDetail } from "@/api/userApplication/book";
  export default {
        name: "detila",
      props:{
        detail:{
            type:Object,
          },
      },
      data(){
          return{
          }
      },
      mounted() {
          this.detail = JSON.parse(this.detail);
        //@TODO 获取图书pdf文件
        // this.detail.title = this.bookEntity.name;
        // getBookDetail(this.bookEntity.id).then((res)=>{
        //   this.detail.entity = res.data;
        //   if (undefined != this.detail.entity.bookFileSrc) {
        //     for (let index = 0; index < this.detail.entity.bookFileSrc.length; index++) {
        //       this.detail.entity.bookFileSrc[index] =process.env.VUE_APP_BASE_API +this.detail.entity.bookFileSrc[index];
        //     }
        //   }
        //
        //   this.$emit("loaded",true);
        // });
      }
    }
</script>

<style scoped>

</style>
