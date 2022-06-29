<template>
  <div>
    <el-tooltip v-if="status=='1'" content="已提交，待处理" placement="top">
      <el-tag v-if="status=='1'" effect="dark" type="info">
        已提交
      </el-tag>
    </el-tooltip>
    <el-tooltip v-if="status=='2'" content="处理中" placement="top">
      <el-tag v-if="status=='2'" effect="dark" type="success">
        处理中
      </el-tag>
    </el-tooltip>
    <el-tooltip v-if="status=='3'" content="已通过" placement="top">
      <el-tag v-if="status=='3'" effect="dark" type="primary" style="border: 1px solid #e6b422;">
        已通过
      </el-tag>
    </el-tooltip>
    <el-tooltip v-if="status=='0'" content="暂未提交" placement="top">
      <el-tag v-if="status=='0'" effect="plain" type="info">
        暂未提交
      </el-tag>
    </el-tooltip>
    <el-tooltip v-if="status=='-1'" content="已拒绝" placement="top">
      <el-tag v-if="status=='-1'" effect="dark" type="danger">
        已拒绝
      </el-tag>
    </el-tooltip>
    <el-tooltip v-if="status=='-2'" content="已过期" placement="top">
      <el-tag v-if="status=='-2'" effect="plain" type="warning">
        已过期
      </el-tag>
    </el-tooltip>
  </div>
</template>

<script>
    export default {
      props:{
        status:{
          type:String,
        }
      },
        name: "ApproveStatusTag"
    }
</script>

<style scoped>

</style>
