<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入采购申请标题文字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物品名称" prop="entityName">
        <el-input
          v-model="queryParams.entityName"
          placeholder="请输入采购物品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="approvePersonal">
        <el-input
          v-model="queryParams.approvePersonal"
          placeholder="请输入采购申请人id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请部门" prop="approveDept">
        <el-input
          v-model="queryParams.approveDept"
          placeholder="请输入采购申请部门"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程模板" prop="purchaseTemplate">
        <el-input
          v-model="queryParams.purchaseTemplate"
          placeholder="请输入采购流程模板id"
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
          v-hasPermi="['userApplication:requisition:add']"
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
          v-hasPermi="['userApplication:requisition:edit']"
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
          v-hasPermi="['userApplication:requisition:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:requisition:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="requisitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="申请标题" align="center" prop="title" />
      <el-table-column label="采购物品名称" align="center" prop="entityName" />
      <el-table-column label="采购数量" align="center" prop="number" />
      <el-table-column label="计量单位" align="center" prop="entityUtil" />
      <el-table-column label="申请人" align="center" prop="approvePersonal" />
      <el-table-column label="申请部门" align="center" prop="approveDept" />
      <el-table-column label="采购流程模板id" align="center" prop="purchaseTemplate" />
      <el-table-column label="采购链状态" align="center" prop="purchaseStatus" />
      <el-table-column label="采购说明" align="center" prop="mark" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="创建人" align="center" prop="createPersonal" />
      <el-table-column label="更新人" align="center" prop="updatePersonal" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:requisition:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:requisition:remove']"
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

    <!-- 添加或修改采购申请信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入采购申请标题文字" />
        </el-form-item>
        <el-form-item label="物品名称" prop="entityName">
          <el-input v-model="form.entityName" placeholder="请输入采购物品名称" />
        </el-form-item>
        <el-form-item label="采购数量" prop="number">
          <el-input v-model="form.number" placeholder="请输入采购数量" />
        </el-form-item>
        <el-form-item label="计量单位" prop="entityUtil">
          <el-input v-model="form.entityUtil" placeholder="请输入采购物品计量单位" />
        </el-form-item>
        <el-form-item label="申请人" prop="approvePersonal">
          <el-input v-model="form.approvePersonal" placeholder="请输入采购申请人id" />
        </el-form-item>
        <el-form-item label="申请部门" prop="approveDept">
          <el-input v-model="form.approveDept" placeholder="请输入采购申请部门" />
        </el-form-item>
        <el-form-item label="流程模板" prop="purchaseTemplate">
          <el-input v-model="form.purchaseTemplate" placeholder="请输入采购流程模板id" />
        </el-form-item>
        <el-form-item label="采购说明" prop="mark">
          <el-input v-model="form.mark" placeholder="请输入采购说明" />
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
import { listRequisition, getRequisition, delRequisition, addRequisition, updateRequisition } from "@/api/userApplication/requisition";

export default {
  name: "Requisition",
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
      // 采购申请信息表格数据
      requisitionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        entityName: null,
        number: null,
        entityUtil: null,
        approvePersonal: null,
        approveDept: null,
        purchaseTemplate: null,
        purchaseStatus: null,
        mark: null,
        status: null,
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
    /** 查询采购申请信息列表 */
    getList() {
      this.loading = true;
      listRequisition(this.queryParams).then(response => {
        this.requisitionList = response.rows;
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
        title: null,
        entityName: null,
        number: null,
        entityUtil: null,
        approvePersonal: null,
        approveDept: null,
        purchaseTemplate: null,
        purchaseStatus: "0",
        mark: null,
        status: 0,
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
      this.title = "添加采购申请信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRequisition(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购申请信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRequisition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRequisition(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购申请信息编号为"' + ids + '"的数据项？').then(function() {
        return delRequisition(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/requisition/export', {
        ...this.queryParams
      }, `requisition_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
