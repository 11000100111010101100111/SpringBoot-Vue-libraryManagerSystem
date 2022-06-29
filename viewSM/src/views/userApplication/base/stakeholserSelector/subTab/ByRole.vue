<template>
  <!--角色列表-->
  <div class="app-container">
    <el-row type="flex">
      <el-col style="width: 25%">
        <!--部门数据-->
        <RoleList v-on:clickRole="clickRole" style="max-height: 55vh;overflow: auto" :role-list="roleList"/>
      </el-col>
      <el-col v-loading="loading" style="width: 75%">
        <el-divider content-position="left"  style="margin: 20px;">
          <el-tag style="margin-right: 3px;" v-for="tag in curRole" :key="tag" :type="tag.type" effect="dark">{{tag.label}}</el-tag>
        </el-divider>
        <selected :checked-list="checkedList" v-on:selected="handleCheckedChange" v-if="chiose"
                  :stakeholser-list="stakeholserList" :loading="loading"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {selectByRole} from "@/api/system/user"
  import Selected from "./select";
  import RoleList from "../../RoleList";
  export default {
    props:{
      checkedList:{
        type:Array,
        default:[]
      },
      roleList:{
        type:Array,
        default:[]
      }
    },
    name: "ByRole",
    components: {RoleList, Selected},
    comments:[],
    data(){
      return{
        loading:false,
        stakeholserList:[],
        curRole:[],
        chiose:false,
      }
    },
    methods:{
      getUserList(query){
        this.loading = true;
        selectByRole(query).then(res=>{
          this.stakeholserList = res.data;
          this.chiose  =true;
          this.loading = false;
        });
      },
      // 部门树节点单击事件
      clickRole(role) {
        this.curRole = [];
        let query={
          'roleId' : role.roleId,
        };
        this.curRole.push({
          'id': role.roleId,
          'label': role.roleName,
          'type': 'warning'
        });
        this.curRole.push({
          'id': role.roleId,
          'label': role.roleKey,
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
