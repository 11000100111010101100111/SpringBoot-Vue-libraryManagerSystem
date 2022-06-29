<template>
  <div v-if="hasBook == 1">
    <el-tag effect="dark" type="success">库存在用</el-tag>
  </div>
  <div v-else-if="hasBook == 0">
    <el-tag effect="dark" type="info">库存空闲</el-tag>
  </div>
  <div v-else>
    <el-tag effect="dark" type="warning">未知</el-tag>
  </div>
</template>

<script>
    export default {props:{
        hasBook:{
          type:Object,
        }
      },
        name: "HasBook"
    }
</script>

<style scoped>

</style>
