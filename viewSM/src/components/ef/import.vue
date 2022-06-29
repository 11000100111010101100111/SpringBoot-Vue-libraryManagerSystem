<template>
    <div>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".json, .txt"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入json、txt格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </div>
</template>

<script>

  import { getDataA } from './data_A';
  import { getToken } from "@/utils/auth";
  import {importJSON} from '@/api/userApplication/template'
    export default {
        name: "import",
      data(){
          return{
            // 用户导入参数
            upload: {
              // 是否显示弹出层（用户导入）
              open: false,
              // 弹出层标题（用户导入）
              title: "",
              // 是否禁用上传
              isUploading: false,
              // 设置上传的请求头部
              headers: { Authorization: "Bearer " + getToken() },
              // 上传的地址
              url: process.env.VUE_APP_BASE_API + "/userApplication/template/import"
            },
          }
      },
      methods:{
        // 文件上传中处理
        handleFileUploadProgress(event, file, fileList) {
          this.upload.isUploading = true;
        },
        // 文件上传成功处理
        handleFileSuccess(response, file, fileList) {
          this.upload.open = false;
          this.upload.isUploading = false;
          this.$refs.upload.clearFiles();
          if (response.code != 200){
            this.$alert(
              "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>"+response.msg+"</div>",
              "操作失败",
              { dangerouslyUseHTMLString: true });
            return;
          }
          this.$alert(
            "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>模板导入成功</div>",
            "导入结果",
            { dangerouslyUseHTMLString: true });
          this.$emit('import',response.data);
           // this.getList();
        },
        // 提交上传文件
        submitFileForm() {
          this.$refs.upload.submit();
        },

        //方法
        fileUpload(event){
          // 上传文件
          let file = event.target.files;
          let formData = new FormData();
          formData.append('category', 'settingPic');
          formData.append('file', file[0]);
          console.log(formData);
          // 文件上传
          importJSON(formData).then((res) => {
            let data = res.data;
            if(data.success) {
              this.$emit('import',data);
            } else {
              this.$message.error(data.message || '操作失败')
            }
            this.uploadLoading = false
          }).catch((e)=>{
            this.uploadLoading = false;
          })

        },
        /** 下载模板操作 */
        importTemplate() {
            this.$confirm('确定要下载流程模板吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
              closeOnClickModal: false
            }).then(() => {
              let data = getDataA();
              let datastr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(data, null, '\t'));
              let downloadAnchorNode = document.createElement('a');
              downloadAnchorNode.setAttribute("href", datastr);
              downloadAnchorNode.setAttribute("download", 'data.json');
              downloadAnchorNode.click();
              downloadAnchorNode.remove();
              this.$message.success("正在下载中,请稍后...")
            }).catch(() => {
            })

        },
      }
    }
</script>

<style scoped>

</style>
