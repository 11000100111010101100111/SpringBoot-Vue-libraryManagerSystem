<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-cpu"></i> 自动入库</span>
        <stock-list/>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-box"></i> 闲置库位信息</span>
        <Stock/>
      </el-tab-pane>
      <el-tab-pane name="third" v-if="false">
        <span slot="label"><i class="el-icon-document-copy"></i> 待入库图书信息</span>
      </el-tab-pane>
      <el-tab-pane name="fourth" v-if="false">
        <span slot="label"><i class="el-icon-thumb"></i> 手动入库</span>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import StockList from "./in/stockList";
  import Stock from "../stock/index";

  export default {
    name: "inStock",
    components: {Stock, StockList},
    data() {
      return {
        activeName: 'first',
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          stockId: [ { required: true, message: "图书库存ID不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      // this.getList();
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
    }
  };
</script>
