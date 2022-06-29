<template>
  <div>
    <el-tag effect="dark" v-if="undefined != backType && null != backType && backType=='1'">已归还 </el-tag>
    <el-tag effect="dark" type="danger"> 待归还 </el-tag>
  </div>
</template>

<script>
    export default {
        name: "HasBackBookTag",
        props:{
          backType:{
            type:String,
            default:'0'
          }
        }
    }
</script>

<style scoped>

</style>
