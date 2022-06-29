<template>
    <!--干系人选择器-->
  <div>
    <el-tabs type="border-card" @tab-click="clickTab" active-name="dept">
      <el-tab-pane name="dept" style="min-height: 60vh;max-height: 60vh;overflow: auto;">
        <span slot="label"><i class="el-icon-office-building"></i> 按部门选择</span>
        <by-dept v-on:selected="selectChange" :checked-list="checkedList"/>
      </el-tab-pane>
      <el-tab-pane name="role" label="消息中心" style="min-height: 60vh;max-height: 60vh;overflow: auto;">
        <span slot="label"><i class="el-icon-s-custom"></i> 按角色选择</span>
        <by-role v-on:selected="selectChange" :checked-list="checkedList" :role-list="roleList"/>
      </el-tab-pane>
      <el-tab-pane name="post" label="角色管理" style="min-height: 60vh;max-height: 60vh;overflow: auto;">
        <span slot="label"><i class="el-icon-suitcase"></i> 按岗位选择</span>
        <by-post v-on:selected="selectChange" :checked-list="checkedList" :post-list="postList"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import ByDept from "./subTab/ByDept";
    import ByRole from "./subTab/ByRole";
    import {listRole} from "@/api/system/role"
    import {listPost} from "@/api/system/post"
    import ByPost from "./subTab/ByPost";
    export default {
        name: "stakeholserSelector",
        components: {ByPost, ByRole, ByDept},
      props:{
        checkedList:{
          type:Array,
          default:[]
        }
      },
        data(){
            return{
              roleList:[],
              postList:[],
            }
        },
        methods:{
          getRoleList(){
            listRole({}).then(res=>{
              this.roleList = res.rows;
            });
          },
          getDeptTree(){

          },
          getPostList(){
            listPost({}).then(res=>{
              this.postList = res.rows;
            });
          },
          selectChange(value){
            if (undefined == value || null == value || value.length <1){
              this.$modal.msgError("当前无选择");
            }
            this.$emit('selected',value)
          },
          clickTab(tab, event){
            if (tab.name == 'dept'){
              this.getDeptTree();
            }else if(tab.name == 'role'){
              this.getRoleList();
            }else if(tab.name == 'post'){
              this.getPostList();
            }
          }
        }
    }
</script>

<style scoped>

</style>
