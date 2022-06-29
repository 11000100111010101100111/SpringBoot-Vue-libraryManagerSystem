<template>
  <div>
    <label style="font-size: 14px;
                  font-weight: normal;
                  color:#606266;
                  text-rendering: optimizeLegibility;
                  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;">
      {{label}}
    </label>
  </div>
</template>

<script>
    export default {
      props:{
        label:{
          type:Object
        }
      },
        name: "FomatLabel"
    }
</script>

<style scoped>

</style>
