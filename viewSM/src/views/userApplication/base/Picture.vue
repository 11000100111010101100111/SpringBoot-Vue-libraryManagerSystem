<template>
  <div class="grid-content bg-purple image__preview preview">
    <el-image
      :style="myStype"
      :src="undefined == srcList || srcList.length <=0?'/dev-api/profile/avatar/2022/system/no-picture.png':srcList[0]"
      :preview-src-list="srcList"
      :fit="fit"
    >
    </el-image>
  </div>
</template>

<script>
    export default {
      props:{
        srcList:{
          type:Array
        },
        myStype:{
          type:String,
          default:'width: 300px; height: 300px;box-shadow: 0 0 5px #666'
        },
        fit:{
          type:String,
          default:'fit'
        }
      },
      name: "Picture",
      data(){
        return{
          url:'',
        }
      },
      methods:{

      }
    }
</script>

<style scoped>

</style>
