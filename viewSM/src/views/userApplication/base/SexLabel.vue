<template>
  <div v-if="sex == 1">
    <el-tag type="success">男</el-tag>
  </div>
  <div v-else-if="sex == 2">
    <el-tag type="danger">女</el-tag>
  </div>
  <div v-else>
    <el-tag type="warning">未知</el-tag>
  </div>
</template>

<script>
    export default {
      props:{
        sex:{
          type:Object,
        }
      },
        name: "SexLabel"
    }
</script>

<style scoped>

</style>
