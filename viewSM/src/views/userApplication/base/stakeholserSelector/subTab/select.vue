<template>
  <div v-loading="loading" style="padding: 10px;">
    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
    <div style="margin: 15px 0;"></div>
    <el-checkbox-group
      v-model="checkedList" @change="handleCheckedChange">
      <el-checkbox
        size="medium"
        :label="stakeholser.userId"
        v-for="(stakeholser,index) in stakeholserList"
        :key="stakeholser.userId"
        :value="stakeholser.userId"
      >{{stakeholser.nickName}}</el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
    export default {
        props:{
          stakeholserList:{
            type:Array,
          },
          checkedList:{
            type:Array,
            default:[]
          },
          loading:{
            type:Boolean,
            default:false
          }
        },
        name: "selected",
        data(){
          return{
            loading:false,
            checkAll: false,
            isIndeterminate: true,
          }
        },
        // watch:{
        //   stakeholserList() {
        //     // this.checkList = [];
        //     this.loading=false;
        //     this.checkList=[];
        //     this.checkAll=false;
        //     this.isIndeterminate= true;
        //   },
        // }
        // ,
        methods:{
        handleCheckAllChange(val) {
          // this.checkAll = !this.checkAll;
          // this.arr = this.checkAll ? this.stakeholserList : [];
          this.checkedList = [];
          if(this.checkAll){
            for(let i=0;i<this.stakeholserList.length;i++){
              this.checkedList.push( this.stakeholserList[i].userId );
            }
          }
          this.isIndeterminate = false;
          this.$emit('selected',this.checkedList)
        },
        handleCheckedChange(value) {
          let checkedCount = value.length;
          this.checkAll = checkedCount == this.stakeholserList.length;
          this.isIndeterminate = checkedCount > 0 && checkedCount < this.stakeholserList.length;

          // this.arr = [];
          // for(let i = 0;i<value.length;i++){
          //   this.arr.push(this.stakeholserList[value[i]]);
          // }

          this.$emit('selected',this.checkedList)
        }
      },
      mounted() {
          // this.checkedList = [];
      }
    }
</script>

<style scoped>

</style>
