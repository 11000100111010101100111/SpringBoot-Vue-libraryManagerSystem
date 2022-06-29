<template>
  <div id="app">
    <div style="margin: auto; width: 100%; height: 100vh">
      <FlowDesignTCD
        :edgeModel.sync="edgeModel"
        :nodeModel.sync="nodeModel"
        @save="save"
      >
        <template v-slot:node>
          <div class="form-wrap">
            <el-form :model="nodeModel" label-position="left" size="mini" label-width="20%">
              <el-form-item label="节点标签">
                <el-input v-model="nodeModel.label"></el-input>
              </el-form-item>
              <el-form-item label="其它数据">
                <el-input
                  type="textarea"
                  :rows="3"
                  v-model="nodeModel.handleNode"
                />
              </el-form-item>
            </el-form>
          </div>
        </template>
        <template v-slot:edge>
          <div class="form-wrap">
            <el-form :model="edgeModel" label-position="left" size="mini" label-width="20%">
              <el-form-item label="连线标签">
                <el-input v-model="edgeModel.label"></el-input>
              </el-form-item>
              <el-form-item label="其它数据">
                <el-input
                  type="textarea"
                  :rows="3"
                  v-model="edgeModel.props"
                />
              </el-form-item>
            </el-form>
          </div>
        </template>
      </FlowDesignTCD>
    </div>
  </div>
</template>
<script>
  export default {
    data(){
      return{
        nodeModel: {
          label: "",
          handleNode: "",
        },
        edgeModel: {
          label: "",
          props: ""
        },
      }
    },

    methods:{
      save(data){
        console.log(data)
      }
    },
  }
</script>

<style>
  .el-form-item{
    white-space: nowrap;
  }
  .form-wrap{
    width: 100%;
    height: 65%;
    box-sizing:border-box;
    padding: 15px;
  }
</style>
