<template>
    <div>
      <el-tag v-if="status=='-1'" effect="dark" type="info">
        未读
      </el-tag>
      <el-tag v-if="status=='1'" effect="dark" type="primary">
        已处理(同意)
      </el-tag>
      <el-tag v-if="status=='2'" effect="dark" type="warning">
        已处理(拒绝)
      </el-tag>
      <el-tag v-if="status=='0'" effect="dark" type="danger">
        待处理
      </el-tag>
    </div>
</template>

<script>
    export default {
        name: "matterStatus",
      props:{
          status:{
            type:String,
            default:"0"
          }
      }
    }
</script>

<style scoped>

</style>
