<template>
  <div class="app-container">
    <el-tabs v-model="activeName" v-loading="loading" @tab-click="handleClick">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-search"></i> 图书搜索</span>
        <seacherBook ref="first"/>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-s-platform"></i> 图书借阅</span>
        <loan ref="second"/>
      </el-tab-pane>
      <el-tab-pane name="third">
        <span slot="label"><i class="el-icon-finished"></i> 图书归还</span>
        <history ref="third"/>
      </el-tab-pane>
      <el-tab-pane name="fourth">
        <span slot="label"><i class="el-icon-date"></i> 归还记录</span>
        <back-log-list ref="fourth"/>
      </el-tab-pane>
      <el-tab-pane name="five">
        <span slot="label"><i class="el-icon-s-custom"></i> 与我相关</span>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
    import SeacherBook from "./seacherBook/seacherBook";
    import TimeNow from "./seacherBook/timeNow";
    import Loan from "./loan/loan";
    import History from "./history/history";
    import BackLogList from "./back/BackLogList";
    export default {
        name: "loanBook",
      components: {BackLogList, History, Loan, TimeNow, SeacherBook},
      data() {
          return {
            loading:false,
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
            this.loading = true;
           if (tab.name == 'first'){
             this.$refs.first.getList();
           }else if(tab.name == 'second'){
             this.$refs.second.code='';
             this.$refs.second.showData=false;
           }else if(tab.name == 'third'){
             this.$refs.third.getList();
           }else if(tab.name == 'fourth'){
             this.$refs.fourth.getList();
           }else if(tab.name == 'five'){
             // this.$refs.five.getList();
           }
           this.loading = false;
          },

        }
    }
</script>

<style scoped>

</style>
