<template>
    <div style="padding: 0 10px 0 10px;">
      <el-row>
        <el-col>
          <el-divider content-position="left"><i class="el-icon-picture-outline-round" style="color: #30B08F"/><font style="color: #30B08F">图片资源</font></el-divider>
          <el-upload
            class="upload-demo"
            action="#"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="pictureList"
            :http-request="uploadPic"
            ref="uploadPic"
            name="file"
            :multiple="false"
            :auto-upload="true"
            accept=".jpg,.jpeg,.png,.gif"
            :before-remove="removeFile"
            list-type="picture-card">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png/gif/jpeg文件，单文件不超过1MB</div>
          </el-upload>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-divider content-position="left"><i class="el-icon-video-camera" style="color: #FEC171"/><font style="color: #FEC171">视频类资源</font></el-divider>
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="uploadVideo"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="videoList"
            ref="uploadVideo"
            name="file"
            :auto-upload="true"
            :multiple="false"
            :before-remove="removeFile"
            accept=".MP4,.mp4,.avi,.AVI"
            list-type="text">
            <el-button size="small" type="warning">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传mp4/avi视频文件，单文件不超过10MB</div>
          </el-upload>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-divider content-position="left"><i class="el-icon-download" style="color: #C03639"/><font style="color: #C03639">文件资源（pdf文档）</font></el-divider>
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="uploadPdf"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            ref="uploadPdf"
            name="file"
            :auto-upload="true"
            :multiple="false"
            :before-remove="removeFile"
            :file-list="pdfList"
            accept=".pdf,.PDF"
            list-type="text">
            <el-button size="small" type="danger">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传pdf文档文件，单文件不超过20MB</div>
          </el-upload>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-divider content-position="left"><i class="el-icon-s-order" style="color: #909399"/><font style="color: #909399">文件资源（doc等其他文档）</font></el-divider>
          <el-upload
            class="upload-demo"
            action="#"
            :on-preview="handlePreview"
            :http-request="uploadDoc"
            :on-remove="handleRemove"
            :auto-upload="true"
            ref="uploadDoc"
            name="file"
            :multiple="false"
            :before-remove="removeFile"
            :file-list="docList"
            accept=".txt,.TXT,.doc,.docx"
            list-type="text">
            <el-button size="small" type="info">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传doc.docx/txt等文档文件，单文件不超过5MB</div>
          </el-upload>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-divider content-position="left"><i class="el-icon-more" style="color: #37e2c0"/><font style="color: #37e2c0">其他文档</font></el-divider>
          <el-upload
            class="upload-demo"
            action="#"
            :on-preview="handlePreview"
            :http-request="uploadOther"
            :on-remove="handleRemove"
            :auto-upload="true"
            ref="uploadOther"
            name="file"
            :multiple="false"
            :before-remove="removeFile"
            :file-list="otherList"
            accept=".txt,.TXT,.doc,.docx"
            list-type="text">
            <el-button size="small" type="success">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传doc.docx/txt等文档文件，单文件不超过5MB</div>
          </el-upload>
        </el-col>
      </el-row>
    </div>
</template>

<script>
  import {updatePicture,updateDoc,updatePdf,getBookDetail,uploadFile} from '@/api/userApplication/book';
  import {delFile,delOneFile} from '@/api/userApplication/file';
  import { addTypeFile,addFileList} from "@/api/userApplication/file";
  import { getToken } from "@/utils/auth";
    export default {
        name: "editResources",
      props:{
        book:{
          type:Object,
        },
      },
      data(){
          return{
            open:false,
            pictureList:[],
            docList:[],
            pdfList:[],
            videoList:[],
            otherList:[],
          }
      },
      methods:{
        handleRemove(file, fileList) {
          console.log(file, fileList);
        },
        handlePreview(file) {
          console.log(file);
        },
        upload(file,type,list){
            let formData = new FormData();
            formData.append("file", file.file);
            addTypeFile(formData,type).then(res=>{
              if(undefined !=res.data && null!=res.data && undefined!=res.data[0] && null != res.data[0]) {
                  let list = res.data[0];
                  let bookFile = {
                    bookId: this.book.id,
                    bookTitle: this.book.name,
                    type: list.type,
                    fileId: list.id,
                    modelId: this.book.id,
                    dbId: '',
                    url: list.url,
                    mark: list.mark,
                    status: 1,
                    isDel: 0,
                  };
                  uploadFile(bookFile).then(response => {
                    list.push(response.data);
                    this.$modal.msgSuccess("上传成功");
                    return list;
                  }).catch(_ => {
                    return list;
                  });
              }else {
                this.$modal.msgSuccess("保存成功");
                return list;
              }
            }).catch(_=>{
              return list;
            });
        },
        uploadPic(file){
          this.upload(file,'picture',this.pictureList);
        },
        uploadDoc(file){
          this.upload(file,'doc',this.docList);
        },
        uploadPdf(file){
          this.upload(file,'pdf',this.pdfList);
        },
        uploadVideo(file){
          this.upload(file,'video',this.videoList);
        },
        uploadOther(file){
          this.upload(file,'other',this.otherList);
        },

        getFileList(){
          this.open = true;
          getBookDetail(this.book.id).then((res)=>{
            let fileEntity = res.data.bookFile;
            let head = fileEntity.urlHead;
            // this.$modal.msgSuccess(head);
            if (undefined == fileEntity || null == fileEntity) {
              this.pictureList = [];
              this.pdfList = [];
              this.docList= [];
              this.videoList= [];
              this.otherList= [];
              this.open = false;
              return;
            }
            this.pictureList = this.setUrlHead(fileEntity.picFile,head,'图片');
            this.pdfList = this.setUrlHead(fileEntity.pdfFile,head,'pdf');
            this.docList= this.setUrlHead(fileEntity.docFile,head,'文档');
            this.videoList= this.setUrlHead(fileEntity.videoFile,head,'视频');
            this.otherList= this.setUrlHead(fileEntity.otherFile,head,'其他文件');
            this.open = false;
          }).catch(_=>{
            this.open = false;
            this.$modal.msgError('资源加载失败，请刷新后打开!');
          });
        },
        setUrlHead(list,head,type){
          if (undefined == list || null == list){
            return [];
          }
          for (let i=0;i<list.length;i++){
            list[i].url = process.env.VUE_APP_BASE_API + list[i].url;
            list[i].name = type+'_'+ list[i].mark+"_"+(i+1);
          }
          return list;
        },

        removeFile(file, fileList){
           this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then( () => {
            delOneFile(file.fileId).then(()=>{
                this.$modal.msgSuccess('删除成功!');
              }
            ).catch(_=>{
              fileList.push(file);
              // this.$modal.msgSuccess('删除失败!');
              return false;
            });
          }).catch(() => {
             fileList.push(file);
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },

        // uploadSucess(data,fileList){
        //   fileList.push(data.data);
        // },
        // uploadSucessOther(response, file, fileList){
        //   this.uploadSucess(response.data,fileList);
        // },
        // uploadSucessPic(response, file, fileList){},
        // uploadSucessPdf(response, file, fileList){},
        // uploadSucessDoc(response, file, fileList){},
        // uploadSucessVideo(response, file, fileList){}
      },
      mounted() {
          this.getFileList();
      }
    }
</script>

<style scoped>

</style>
