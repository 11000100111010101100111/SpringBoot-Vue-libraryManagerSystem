<template>
  <!--海哥出品<>必属精品-->
    <div class="box-tag-add">
      <el-row :gutter="20">
        <el-col style="display: inline-block">
          <el-tooltip
            :disabled="tags.item.length<maxTags"
            :content="'最多只能添加'+maxTags+'个'">
            <el-button
              :disabled="tags.item.length>maxTags-1"
              icon="el-icon-plus"
              type="primary"
              style="margin-bottom: 5px;"
              @click="addCurInput"/>
          </el-tooltip>
        </el-col>
        <el-col :span="16">
          <el-input
            type="text"
            style="margin-bottom: 10px;"
            :size="sizeOfOne"
            v-model="curInput"
            placeholder="请输入..."/>
        </el-col>
      </el-row>
      <el-row
        :gutter="20"
        v-if="tags.item.length>0">
        <el-col >
            <el-tag
              closable
              @close="handleClose(tag)"
              v-for="(tag,index) in tags.item"
              :key="index"
              class="tags-item"
            >{{tag}}</el-tag>
        </el-col>
      </el-row>
    </div>
</template>
<!--//标签输入框-->
<script>
    export default {
        name: "tagInput",
        props:{
            maxTags:{
              //允许输入多少个标签，默认5个
              type:Number,
              default: 5
            },
          sizeOfOne:{
              //每个标签最多5个字（默认）
              type:Number,
            default:5,
          },
          value:{
              type:String,
              default:''
          }
      },
      data(){
          return{
            inputVisible:false,
            curInput:'',
            tags:{
              item:[],//标签数组
              tagsStr:'',//逗号凭借的结果
            }
          }
      },
      methods:{
        addCurInput(){
          if (undefined == this.curInput || null == this.curInput || ''==this.curInput.trim()){
            this.$modal.msgError("输入内容为空");
            return;
          }
          if (this.tags.item.length>this.maxTags){
            this.$modal.msgError("最多只能添加"+this.maxTags+'个');
            return;
          }
          let val = this.curInput.replaceAll(' ','');
          if (val.length>this.sizeOfOne){
            this.$modal.msgError("每个标签最长不能超过"+this.sizeOfOne+'个字。');
            return;
          }
          this.tags.item.push(val);
          this.curInput = '';
          this.tags.tagsStr = this.tags.item.join(',');
          this.$emit("addTags",this.tags);
        },
        handleClose(tag) {
          this.tags.item.splice(this.tags.item.indexOf(tag), 1);
          this.tags.tagsStr = this.tags.item.join(',');
          this.$emit("addTags",this.tags);
        },
      },
      mounted() {
          this.tags.item = this.value.split(',');
          this.tags.tagsStr = this.value;
      },
      created() {
        this.tags.item = this.value.split(',');
        this.tags.tagsStr = this.value;
      }
    }
</script>

<style scoped>
      .box-tag-add{
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 3px;
        margin: 5px;
      }
      .tags-item{
        margin-right: 5px;
      }
</style>
