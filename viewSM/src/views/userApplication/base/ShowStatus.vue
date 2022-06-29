<template>
    <div>
      <el-tag v-if="status=='1'" effect="dark" type="success">
        正常
      </el-tag>
      <el-tag v-if="status=='0'" effect="dark" type="danger">
        已停用
      </el-tag>
    </div>
</template>

<script>
    export default {
      props:{
        status:{
          type:String,
        }
      },
        name: "ShowStatus"
    }
</script>

<style scoped>

</style>
