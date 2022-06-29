<template>
    <div>

    </div>
</template>

<script>
  // 数据列表
    export default {
        name: "my-list",
      props:{
          column:{
            type:Array,
            default:[0],//默认显示第一列
          },
        data:{
            type:Array,
            default:[],
        }
      },
    }
</script>

<style scoped>

</style>
