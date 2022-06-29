<style>
  .post_span{
    width: 100%;
    font-size: 17px;
    font-weight: 600;
    text-align: center;
    padding: 5px;
    cursor: pointer;
    display: inline-block;
    transition: all 0.4s;
  }
  .post_span:hover{
    color: #1ab394;
    background-color: #ddd;
  }
</style>
<template>
  <!--角色列表-->
  <div>
    <div class="head-container">
      <el-input
        v-model="postName"
        placeholder="输入岗位名称开始查询"
        clearable
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 20px;"
      />
    </div>
    <div class="head-container">
      <el-row v-for="post in postList" v-if="post.status == '0'">
        <el-col>
          <span class="post_span" @click="chiosePost(post)" ref="roleList">{{post.postName}}</span>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import {listPost} from "@/api/system/post"
  export default {
    name: "PostList",
    props:{
      postList:{
        type:Array,
        default:function () {
          this.getPost({});
        }
      }
    },
    data(){
      return{
        postName:'',
      }
    },
    methods:{
      getPost(query){
        listPost(query).then(res=>{
          this.postList = res.rows;
        });
      },
      chiosePost(post){
        this.$emit('clickPost',post);
      }
    },
    mounted() {
      // this.getPost({});
    },
    watch: {
      // 根据名称筛选部门树
      postName(val) {
        let query = {
          postName:val
        };
        this.getPost(query);
      }
    },
  }
</script>

<style scoped>

</style>
