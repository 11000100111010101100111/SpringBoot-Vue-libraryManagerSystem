<template>
  <div v-if="isLoan == 0">
    <el-tag effect="dark" type="success">在库</el-tag>
  </div>
  <div v-else-if="isLoan == 1">
    <el-tag effect="dark" type="danger">已出库</el-tag>
  </div>
  <div v-else>
    <el-tag effect="dark" type="warning">未知</el-tag>
  </div>
</template>

<script>
    export default {
      props:{
        isLoan:{
          type:Object,
        }
      },
        name: "BookIsLoan"
    }
</script>

<style scoped>

</style>
