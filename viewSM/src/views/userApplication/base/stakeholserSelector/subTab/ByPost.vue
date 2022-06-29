<template>
  <!--角色列表-->
  <div class="app-container">
    <el-row type="flex">
      <el-col style="width: 25%">
        <!--部门数据-->
        <PostList v-on:clickPost="clickPost" style="max-height: 55vh;overflow: auto" :post-list="postList"/>
      </el-col>
      <el-col v-loading="loading" style="width: 75%">
        <el-divider content-position="left"  style="margin: 20px;">
          <el-tag style="margin-right: 3px;" v-for="tag in curPost" :key="tag" :type="tag.type" effect="dark">{{tag.label}}</el-tag>
        </el-divider>
        <selected :checked-list="checkedList" v-on:selected="handleCheckedChange" v-if="chiose"
                  :stakeholser-list="stakeholserList" :loading="loading"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {selectByPost} from "@/api/system/user"
  import Selected from "./select";
  import PostList from "../../PostList";
  export default {
    props:{
      checkedList:{
        type:Array,
        default:[]
      },
      postList:{
        type:Array,
        default:[]
      }
    },
    name: "ByPost",
    components: {PostList, Selected},
    comments:[],
    data(){
      return{
        loading:false,
        stakeholserList:[],
        curPost:[],
        chiose:false,
      }
    },
    methods:{
      getUserList(query){
        this.loading = true;
        selectByPost(query).then(res=>{
          this.stakeholserList = res.data;
          this.chiose  =true;
          this.loading = false;
        });
      },
      // 部门树节点单击事件
      clickPost(post) {
        this.curpost = [];
        let query={
          'postId' : post.postId,
        };
        this.curpost.push({
          'id': post.postId,
          'label': post.postName,
          'type': 'warning'
        });
        this.curpost.push({
          'id': post.postId,
          'label': post.postCode,
          'type': 'success'
        });
        this.getUserList(query);
      },
      handleCheckedChange(value) {
        this.$emit('selected',value)
      }
    }
  }
</script>

<style scoped>

</style>
