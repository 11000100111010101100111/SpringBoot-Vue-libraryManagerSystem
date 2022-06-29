<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane name="first">
        <span slot="label"><i class="el-icon-date"></i> 闲置库位信息</span>
      </el-tab-pane>
      <el-tab-pane name="second">
        <span slot="label"><i class="el-icon-date"></i> 待入库图书信息</span>
      </el-tab-pane>
      <el-tab-pane name="third">
        <span slot="label"><i class="el-icon-date"></i> 手动入库</span>
      </el-tab-pane>
      <el-tab-pane name="fourth">
        <span slot="label"><i class="el-icon-date"></i> 自动入库</span>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import { listLog, getLog, delLog, addLog, updateLog } from "@/api/userApplication/log";

  export default {
    name: "outStock",
    data() {
      return {
        activeName: 'second',
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
        // 图书库存出入信息表格数据
        logList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          stockId: null,
          isLoan: null,
          operateType: null,
          createPersonal: null,
          otherPersonal: null,
          updatePersonal: null
        },
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
      this.getList();
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      },
      /** 查询图书库存出入信息列表 */
      getList() {
        this.loading = true;
        listLog(this.queryParams).then(response => {
          this.logList = response.rows;
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
          stockId: null,
          isLoan: null,
          operateType: null,
          createTime: null,
          createPersonal: null,
          otherPersonal: null,
          updateTime: null,
          updatePersonal: null
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
        this.title = "添加图书库存出入信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getLog(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改图书库存出入信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateLog(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addLog(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除图书库存出入信息编号为"' + ids + '"的数据项？').then(function() {
          return delLog(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('userApplication/log/export', {
          ...this.queryParams
        }, `log_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
