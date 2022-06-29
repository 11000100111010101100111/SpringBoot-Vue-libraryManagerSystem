<style>
  .role_span{
    width: 100%;
    font-size: 17px;
    font-weight: 600;
    text-align: center;
    padding: 5px;
    cursor: pointer;
    display: inline-block;
    transition: all 0.4s;
  }
  .role_span:hover{
    color: #1ab394;
    background-color: #ddd;
  }
</style>
<template>
    <!--角色列表-->
  <div>
    <div class="head-container">
      <el-input
        v-model="roleName"
        placeholder="输入角色名称开始查询"
        clearable
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 20px;"
      />
    </div>
    <div class="head-container">
      <el-row v-for="role in roleList" v-if="role.status == '0'">
          <el-col>
              <span class="role_span" @click="chioseRole(role)" ref="roleList">{{role.roleName}}</span>
          </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
    import {listRole} from "@/api/system/role"
    export default {
        name: "RoleList",
      props:{
        roleList:{
          type:Array,
          default:function () {
            this.getRole({});
          }
        }
      },
        data(){
          return{
            roleName:'',
          }
        },
        methods:{
            getRole(query){
              listRole(query).then(res=>{
                this.roleList = res.rows;
              });
            },
            chioseRole(role){
              this.$emit('clickRole',role);
            }
        },
        mounted() {
          // this.getRole({});
        },
      watch: {
          // 根据名称筛选部门树
          roleName(val) {
            let query = {
              roleName:val
            };
            this.getRole(query);
          }
        },
    }
</script>

<style scoped>

</style>
