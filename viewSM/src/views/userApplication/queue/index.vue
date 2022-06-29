<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="批次ID" prop="groupId">
        <el-input
          v-model="queryParams.groupId"
          placeholder="请输入当前处理批次ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="待处理人" prop="handelPersonal">
        <el-input
          v-model="queryParams.handelPersonal"
          placeholder="请输入待处理人ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请ID" prop="purchaseId">
        <el-input
          v-model="queryParams.purchaseId"
          placeholder="请输入待处理审批申请ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已读" prop="hasRead">
        <el-input
          v-model="queryParams.hasRead"
          placeholder="请输入是否已读，默认未读0，已读使用1表示"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理结果" prop="result">
        <el-input
          v-model="queryParams.result"
          placeholder="请输入处理结果，未处理0表示，已处理1表示"
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
          v-hasPermi="['userApplication:queue:add']"
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
          v-hasPermi="['userApplication:queue:edit']"
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
          v-hasPermi="['userApplication:queue:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:queue:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="queueList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="处理批次ID" align="center" prop="groupId" />
      <el-table-column label="处理类型" align="center" prop="queueType" />
      <el-table-column label="待处理人" align="center" prop="handelPersonal" />
      <el-table-column label="待处理审批申请" align="center" prop="purchaseId" />
      <el-table-column label="是否已读" align="center" prop="hasRead" />
      <el-table-column label="处理结果" align="center" prop="result" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="说明" align="center" prop="mark" />
      <el-table-column label="创建人" align="center" prop="createPersonal" />
      <el-table-column label="更新人" align="center" prop="updatePersonal" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:queue:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:queue:remove']"
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

    <!-- 添加或修改申请队列信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="当前处理批次ID" prop="groupId">
          <el-input v-model="form.groupId" placeholder="请输入当前处理批次ID" />
        </el-form-item>
        <el-form-item label="待处理人ID" prop="handelPersonal">
          <el-input v-model="form.handelPersonal" placeholder="请输入待处理人ID" />
        </el-form-item>
        <el-form-item label="待处理审批申请ID" prop="purchaseId">
          <el-input v-model="form.purchaseId" placeholder="请输入待处理审批申请ID" />
        </el-form-item>
        <el-form-item label="是否已读，默认未读0，已读使用1表示" prop="hasRead">
          <el-input v-model="form.hasRead" placeholder="请输入是否已读，默认未读0，已读使用1表示" />
        </el-form-item>
        <el-form-item label="处理结果，未处理0表示，已处理1表示" prop="result">
          <el-input v-model="form.result" placeholder="请输入处理结果，未处理0表示，已处理1表示" />
        </el-form-item>
        <el-form-item label="说明" prop="mark">
          <el-input v-model="form.mark" placeholder="请输入说明" />
        </el-form-item>
        <el-form-item label="创建人ID" prop="createPersonal">
          <el-input v-model="form.createPersonal" placeholder="请输入创建人ID" />
        </el-form-item>
        <el-form-item label="更新人ID" prop="updatePersonal">
          <el-input v-model="form.updatePersonal" placeholder="请输入更新人ID" />
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
import { listQueue, getQueue, delQueue, addQueue, updateQueue } from "@/api/userApplication/queue";

export default {
  name: "Queue",
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
      // 申请队列信息表格数据
      queueList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: null,
        queueType: null,
        handelPersonal: null,
        purchaseId: null,
        hasRead: null,
        result: null,
        status: null,
        mark: null,
        createPersonal: null,
        updatePersonal: null
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
    /** 查询申请队列信息列表 */
    getList() {
      this.loading = true;
      listQueue(this.queryParams).then(response => {
        this.queueList = response.rows;
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
        queueType: null,
        handelPersonal: null,
        purchaseId: null,
        hasRead: null,
        result: null,
        status: 0,
        mark: null,
        createTime: null,
        createPersonal: null,
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
      this.title = "添加申请队列信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getQueue(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改申请队列信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateQueue(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQueue(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除申请队列信息编号为"' + ids + '"的数据项？').then(function() {
        return delQueue(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/queue/export', {
        ...this.queryParams
      }, `queue_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
