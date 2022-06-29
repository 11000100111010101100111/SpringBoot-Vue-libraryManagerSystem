<template>
  <div class="app-container">
    <el-form v-if="false" :model="queryParams" ref="querySubForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主键" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入主键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购图书名称" prop="entityName">
        <el-input
          v-model="queryParams.entityName"
          placeholder="请输入采购图书名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购数" prop="purchaseNumber">
        <el-input
          v-model="queryParams.purchaseNumber"
          placeholder="请输入采购数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理数" prop="purchaseOverNumber">
        <el-input
          v-model="queryParams.purchaseOverNumber"
          placeholder="请输入处理数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计量单位ID" prop="util">
        <el-input
          v-model="queryParams.util"
          placeholder="请输入计量单位ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计量单位名称" prop="utilName">
        <el-input
          v-model="queryParams.utilName"
          placeholder="请输入计量单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="isDel">
        <el-input
          v-model="queryParams.isDel"
          placeholder="请输入是否删除"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.base_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['userApplication:purchaseSub:add']"
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
          v-hasPermi="['userApplication:purchaseSub:edit']"
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
          v-hasPermi="['userApplication:purchaseSub:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:purchaseSub:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseSubList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="采购申请组id" align="center" prop="groupId" />
      <el-table-column label="采购图书id" align="center" prop="entityId" />
      <el-table-column label="采购图书名称" align="center" prop="entityName" />
      <el-table-column label="采购数" align="center" prop="purchaseNumber" />
      <el-table-column label="处理数" align="center" prop="purchaseOverNumber" />
      <el-table-column label="计量单位ID" align="center" prop="util" />
      <el-table-column label="计量单位名称" align="center" prop="utilName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="是否删除" align="center" prop="isDel" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.base_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createPersonal" />
      <el-table-column label="更新人" align="center" prop="updatePersonal" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:purchaseSub:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:purchaseSub:remove']"
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

    <!-- 添加或修改采购申请子对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="subPurchaseForm" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购图书名称" prop="entityName">
          <el-input v-model="form.entityName" placeholder="请输入采购图书名称" />
        </el-form-item>
        <el-form-item label="采购数" prop="purchaseNumber">
          <el-input v-model="form.purchaseNumber" placeholder="请输入采购数" />
        </el-form-item>
        <el-form-item label="处理数" prop="purchaseOverNumber">
          <el-input v-model="form.purchaseOverNumber" placeholder="请输入处理数" />
        </el-form-item>
        <el-form-item label="计量单位ID" prop="util">
          <el-input v-model="form.util" placeholder="请输入计量单位ID" />
        </el-form-item>
        <el-form-item label="计量单位名称" prop="utilName">
          <el-input v-model="form.utilName" placeholder="请输入计量单位名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="是否删除" prop="isDel">
          <el-input v-model="form.isDel" placeholder="请输入是否删除" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.base_status"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="purchaseSubmitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPurchaseSub, getPurchaseSub, delPurchaseSub, addPurchaseSub, updatePurchaseSub } from "@/api/userApplication/purchaseSub";

export default {
  name: "purchaseSub",
  dicts: ['base_status'],
  props:{
    key:{
      type:Number,
    }
  },
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
      // 采购申请子表格数据
      purchaseSubList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        groupId: null,
        entityId: null,
        entityName: null,
        purchaseNumber: null,
        purchaseOverNumber: null,
        util: null,
        utilName: null,
        isDel: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        groupId: [ { required: true, message: "采购申请组id不能为空", trigger: "change" }
        ],
        entityId: [ { required: true, message: "采购图书id不能为空", trigger: "change" }
        ],
        purchaseNumber: [ { required: true, message: "采购数不能为空", trigger: "blur" }
        ],
        util: [ { required: true, message: "计量单位ID不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    // this.$modal.msgSuccess(this.queryParams.groupId);
    this.getList();
  },
  methods: {
    /** 查询采购申请子列表 */
    getList() {
      this.loading = true;
      this.queryParams.groupId = this.key;
      listPurchaseSub(this.queryParams).then(response => {
        this.purchaseSubList = response.rows;
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
        groupId: null,
        entityId: null,
        entityName: null,
        purchaseNumber: null,
        purchaseOverNumber: null,
        util: null,
        utilName: null,
        remark: null,
        isDel: null,
        status: null,
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
      this.resetForm("querySubForm");
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
      this.title = "添加采购申请子";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPurchaseSub(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购申请子";
      });
    },
    /** 提交按钮 */
    purchaseSubmitForm() {
      this.$refs["subPurchaseForm"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchaseSub(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchaseSub(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购申请子编号为"' + ids + '"的数据项？').then(function() {
        return delPurchaseSub(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/purchaseSub/export', {
        ...this.queryParams
      }, `purchaseSub_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
