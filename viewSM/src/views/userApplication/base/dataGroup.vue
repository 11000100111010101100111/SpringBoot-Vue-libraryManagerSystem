<style>

</style>
<template>
    <div>
      <el-descriptions column="1" :colon="true" direction="vertical">
        <el-descriptions-item label="资源列表" content-style="margin:5px;" >
          <el-button-group :style="groupStyle">
          <el-button
            v-if="undefined != data.pdfFile"
            v-for="(file,index) in data.pdfFile"
            :key="file"
            type="danger"
            size="small"
            icon="el-icon-download"
            @click="downloadBackup(file.url)"
          >pdf类资源-{{index+1}}</el-button>
        </el-button-group>
          <br v-if="undefined != data.pdfFile && data.pdfFile.length>0"/>

          <el-button-group :style="groupStyle">
        <el-button
          v-if="undefined != data.picFile"
          v-for="(file,index) in data.picFile"
          :key="file"
          type="primary"
          size="small"
          icon="el-icon-picture-outline-round"
          @click="downloadBackup(file.url)"
        >图片类资源-{{index+1}}</el-button>
          </el-button-group>
          <br v-if="undefined != data.picFile && data.picFile.length>0"/>

          <el-button-group :style="groupStyle">
        <el-button
          v-if="undefined != data.docFile"
          v-for="(file,index) in data.docFile"
          :key="file"
          type="info"
          size="small"
          icon="el-icon-s-order"
          @click="downloadBackup(file.url)"
        >文档类资源-{{index+1}}</el-button>
          </el-button-group>
          <br v-if="undefined != data.docFile && data.docFile.length>0"/>

          <el-button-group :style="groupStyle">
        <el-button
          v-if="undefined != data.videoFile"
          v-for="(file,index) in data.videoFile"
          :key="file"
          type="warning"
          size="small"
          icon="el-icon-video-camera"
          @click="downloadBackup(file.url)"
        >视频类资源-{{index+1}}</el-button>
          </el-button-group>
          <br v-if="undefined != data.videoFile && data.videoFile.length>0"/>

          <el-button-group :style="groupStyle">
        <el-button
          v-if="undefined != data.otherFile"
          v-for="(file,index) in data.otherFile"
          :key="file"
          type="success"
          size="small"
          icon="el-icon-more"
          @click="downloadBackup(file.url)"
        >其他类资源-{{index+1}}</el-button>
      </el-button-group>
        </el-descriptions-item>
      </el-descriptions>
    </div>
</template>

<script>
import {getFilePath,downloadFile} from '@/api/userApplication/file';
    export default {
        props:{
          groupStyle:{
            type:String,
            default:'float: right;margin-bottom:5px;'
          },
          data:{
            type:Array
          }
        },
        name: "dataGroup",
      data(){
          return{
            defaultUrl:'http://localhost:81/dev-api/profile/avatar/2022/system/no-picture.png'
          }
      },
      methods:{
        downloads(url){
          // this.$modal.msgSuccess(url);/dev-api/profile/avatar/2022/02/25/3f991728-12b4-4bb6-91a9-7f08d8029e55.png D:\nhXJH\uploadPath
          let src = url.split('/');
          getFilePath().then(res=>{
            url = res.data +'/' + src.slice(2).join('/');
            this.$modal.msgSuccess(url);
            try {
              let elemIF = document.createElement("iframe");
              elemIF.src = url;
              elemIF.style.display = "none";
              document.body.appendChild(elemIF);
            } catch (e) {
              alert("下载异常！");
            }
          });
        },

        downloadBackup(url) {
          let src = url.split('/');
          let query = {
            url:url
          };
          this.download(
            '/userApplication/file/downloadFile',
            {...query},
            `${src[src.length-1]}`);
        }
      }
    }
</script>

<style scoped>

</style>
