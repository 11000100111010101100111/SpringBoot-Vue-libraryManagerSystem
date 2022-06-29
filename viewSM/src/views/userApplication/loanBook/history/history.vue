<template>
  <div class="app-container">
    <el-form :model="queryParams"
             @submit.native.prevent
             ref="queryForm"
             :inline="true"
             v-show="showSearch" label-width="68px">
      <el-form-item label="图书名称" prop="searchValue">
        <el-input
          v-model="queryParams.searchValue"
          placeholder="请输入图书名称"
          clearable
          style="width: 20vw;"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="historyList" >
      <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="图书名称" align="center" prop="bookName" />
      <el-table-column label="借书人" align="center" prop="userName" />
      <el-table-column label="归还时间" align="center" prop="backTime" width="180">
        <template slot-scope="scope">
          <span v-if="undefined!=scope.row.backTime && null !=scope.row.backTime">{{ parseTime(scope.row.backTime, '{y}-{m}-{d}') }}</span>
          <el-tag
            v-if="undefined==scope.row.backTime || null ==scope.row.backTime"
          effect="dark"
          type="info">待定</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否归还" align="center" prop="hasBack" >
        <template slot-scope="scope">
          <has-back-book-tag :back-type="scope.row.hasBack"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.hasBack!='1'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="goBack(scope.row)"
            v-hasPermi="['userApplication:history:edit']"
          >一键归还</el-button>
          <el-button
            v-if="scope.row.hasBack=='1'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:history:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listHistory,getMyHistory, getHistory, back, updateHistory } from "@/api/userApplication/history";
import Template from "../../template/index";
import HasBackBookTag from "./HasBackBookTag";

export default {
  name: "History",
  components: {HasBackBookTag, Template},
  data() {
    return {
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 借书信息表格数据
      historyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        bookId: null,
        stockId: null,
        backTime: null,
        hasBack: '0',
        status: '1',
        isDel: '0',
        searchValue:'',
        createPersonal: null,
        updatePersonal: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询借书信息列表 */
    getList() {
      this.historyList = [];
      this.total = 0;
      getMyHistory(this.queryParams).then(response => {
        this.historyList = response.rows;
        this.total = response.total;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        bookId: null,
        stockId: null,
        backTime: null,
        hasBack: '0',
        status: "0",
        isDel: null,
        createPersonal: null,
        createTime: null,
        updatePersonal: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const ids = row.id || this.ids;
      // this.$modal.confirm('是否确认删除借书信息编号为"' + ids + '"的数据项？').then(function() {
      //   return delHistory(ids);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
      this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let query = {id:row.id,isDel:'1'};
        updateHistory(query).then(res=>{
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },


    goBack(row){
      let msg = "确认归还"+row.bookName+"吗？";
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        back(row).then(res=>{
          this.$notify({
            title: '操作提醒',
            message: '图书'+row.bookName+'归还成功！',
            type: 'success'
          });
          this.getList();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '归还操作已取消'
        });
      });
    },

    selectable( row, index) {
      return false;
    }
  }
};
</script>
