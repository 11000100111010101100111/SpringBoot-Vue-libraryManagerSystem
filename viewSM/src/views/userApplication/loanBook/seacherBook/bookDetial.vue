<template>
  <div>
    <el-descriptions style="width: 30vw;" class="margin-top" :title="detial.name" :column="1" :size="size">
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          作者
        </template>
        {{detial.authName}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          所属机构
        </template>
        {{detial.dept}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          描述
        </template>
        {{detial.mark.substring(0,20)}}......
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
    export default {
        name: "bookDetial",
      props:{
          detial:{
            type:Object,
          }
      },
      data(){
          return{
            size: ''
          }
      }
    }
</script>

<style scoped>

</style>
