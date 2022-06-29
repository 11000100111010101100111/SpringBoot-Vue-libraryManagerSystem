<style>
  .share-list{
    display: flex;
    list-style: none;
    min-height: 5vh;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 5px;
    max-height: 25vh;
    overflow-y: auto;
    flex-wrap: wrap;
    cursor: pointer;
    transition: all 0.5s;
  }
  .share-list:hover{
    border: 1px solid #1ab394;
  }
  .share-list:before,
  .share-list:after{
    content: "";
  }
  .share-item{
    margin: 3px;
  }
</style>

<template>
  <div>
    <el-row v-if="undefined!=userList && null !=userList ">
      <el-col>
        <div class="share-list" @click="isEdit?toAdd():function() {}">
            <el-tag
              class="share-item"
              :key="index"
              v-for="(user,index) in userList"
              :disable-transitions="false"
              :closable="false"
              @close="handleClose(index)">
              {{user.nickName}}
            </el-tag>
        </div>
      </el-col>
    </el-row>
    <el-row v-if="isEdit">
      <el-button
        style="margin: 5px;"
        icon="el-icon-plus"
        class="button-new-tag"
        size="small"
        @click="toAdd">{{undefined==userList || null ==userList || userList.length<=0?'新增干系人':'修改干系人'}}</el-button>
      <span
        style="color:#aaa;font-size: 13px;"
        v-if="undefined!=userList && null !=userList ">共选择{{userList.length}}人</span>
    </el-row>
  </div>
</template>

<script>
  import {users} from "@/api/system/user"
    export default {
      props:{
        userList:{
          type:[Object,Array],
          default:()=>{}
        },
        ids:{
          type:Array,
          default:()=>{}
        },
        isEdit:{
          type:Boolean,
          default:true
        }
      },
        name: "StakeholderInput",
      data(){
        return{
        }
      },
      methods:{
        handleClose(index) {
          this.userList.splice(index,1);
          // this.stakeholderList = list;
        },
        toAdd() {
          this.$emit('addStakeholder',this.userList);
        },
        getUserByIds(ids){//ids  Array
          users(ids).then(res=>{
            this.userList = res.data;
          });
        }
      },
      mounted() {
        // this.getUserByIds(this.ids);
      },
      watch:{
        userList(){
        }
      }
    }
</script>

<style scoped>

</style>
