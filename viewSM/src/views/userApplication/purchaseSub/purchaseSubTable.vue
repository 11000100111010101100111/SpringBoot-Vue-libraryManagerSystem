<template>
  <el-table :data="purchaseSubList">
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
  </el-table>
</template>

<script>
  import { listPurchaseSub, getPurchaseSub, delPurchaseSub, addPurchaseSub, updatePurchaseSub } from "@/api/userApplication/purchaseSub";
    export default {
      props:{
        edit:{
          type:Boolean
        },
        purchaseSubId:{
          type:Number
        }
      },
        name: "purchaseSubTable",
  data() {
    return {
      // 遮罩层
      loading: false,
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
      this.loading = false;
      this.queryParams.groupId = this.purchaseSubId;
      listPurchaseSub(this.queryParams).then(response => {
        this.purchaseSubList = response.rows;
        this.total = response.total;
        this.$modal.msgSuccess(this.total);
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

<style scoped>

</style>
