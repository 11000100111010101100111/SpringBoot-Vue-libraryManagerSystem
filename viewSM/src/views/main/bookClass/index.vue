<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="类别编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入图书类别编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入图书类别名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别级别" prop="clasz">
        <el-select
          v-model="queryParams.clasz"
          placeholder="选择图书类别级别"
          clearable
          size="small"
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        >
          <el-option
            :key="0"
            :label="'一级类别'"
            :value="0"
          />
          <el-option
            :key="1"
            :label="'二级类别'"
            :value="1"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="别名" prop="aliName">
        <el-input
          v-model="queryParams.aliName"
          placeholder="请输入图书类别别名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['main:bookClass:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['main:bookClass:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['main:bookClass:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['main:bookClass:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookClassList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="类别编号" align="center" prop="code" width="150"/>
      <el-table-column label="级别" align="center" prop="claszName" width="200"/>
      <el-table-column label="名称" align="center" prop="name" width="500"/>
      <el-table-column label="别名" align="center" prop="aliName" width="500"/>
      <el-table-column label="状态" align="center" prop="status" key="status" width="100">
        <template slot-scope="scope">
        <el-switch
          v-model="scope.row.status"
          active-value='1'
          inactive-value='0'
          @change="handleStatusChange(scope.row)"
        ></el-switch>
      </template>
      </el-table-column>
<!--      <el-table-column label="创建人id" align="center" prop="createPersonal" />-->
<!--      <el-table-column label="删除时间" align="center" prop="delTime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.delTime, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['main:bookClass:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['main:bookClass:remove']"
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

    <!-- 添加或修改中国标准图书分类规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="图书类别名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入图书类别名称" />
        </el-form-item>
        <el-form-item label="图书类别级别" prop="class">
          <el-select
            v-model="form.clasz"
            placeholder="选择图书类别级别"
            clearable
            size="small"
            style="width: 180px"
            @keyup.enter.native="handleQuery"
          >
            <el-option
              :key="0"
              :label="'一级类别'"
              :value="0"
            />
            <el-option
              :key="1"
              :label="'二级类别'"
              :value="1"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图书类别編码" prop="code">
          <el-input v-model="form.code" placeholder="请输入图书类别編码" />
        </el-form-item>
        <el-form-item label="图书类别别名" prop="aliName">
          <el-input v-model="form.aliName" placeholder="请输入图书类别别名" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBookClass, getBookClass, delBookClass, addBookClass, updateBookClass,changeStatus } from "@/api/main/bookClass";

export default {
  name: "BookClass",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 中国标准图书分类规则表格数据
      bookClassList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        class: null,
        aliName: null,
        status: null,
        createPersonal: null,
        updatePersonal: null,
        delTime: null,
        delPersonal: null
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
    /** 查询中国标准图书分类规则列表 */
    getList() {
      this.loading = true;
      listBookClass(this.queryParams).then(response => {
        this.bookClassList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        code: null,
        name: null,
        clasz: null,
        claszName: null,
        aliName: null,
        status: 0,
        createTime: null,
        createPersonal: null,
        updateTime: null,
        updatePersonal: null,
        delTime: null,
        delPersonal: null
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加中国标准图书分类规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBookClass(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改中国标准图书分类规则";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBookClass(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBookClass(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除中国标准图书分类规则编号为"' + ids + '"的数据项？').then(function() {
        return delBookClass(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('main/bookClass/export', {
        ...this.queryParams
      }, `bookClass_${new Date().getTime()}.xlsx`)
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === '1' ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '图书类别《' + row.name + '》吗？').then(function() {
        return changeStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === 0 ? 1 :0;
      });
    },
  }
};
</script>
