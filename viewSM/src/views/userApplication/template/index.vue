<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24" >
        <SelectTree v-on:selectNode="selectDept" :treeType="dept"/>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="模板编码" prop="code">
            <el-input
              v-model="queryParams.code"
              placeholder="请输入模板编码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="模板标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入模板标题"
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
              v-hasPermi="['userApplication:template:add']"
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
              v-hasPermi="['userApplication:template:edit']"
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
              v-hasPermi="['userApplication:template:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['userApplication:template:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="模板ID" align="center" prop="id" v-if="false"/>
          <el-table-column label="模板编码" align="center" prop="code" width="200"/>
          <el-table-column label="模板标题" align="center" prop="title" />
          <el-table-column label="模板" align="center" prop="template" v-if="false"/>
          <el-table-column label="备注" align="center" prop="mark"  v-if="false"/>
          <el-table-column label="状态" align="center" prop="status" width="100">
            <template slot-scope="scope">
              <show-status :status="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="center" prop="createPersonal"  v-if="false"/>
          <el-table-column label="更新人" align="center" prop="updatePersonal"  v-if="false"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleBrowseLink(scope.row,false)"
                v-if="scope.row.createPersonal != userId"
                v-hasPermi="['userApplication:template:edit']"
              >查看模板</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-s-operation"
                @click="handleBrowseLink(scope.row,true)"
                v-if="scope.row.createPersonal == userId"
                v-hasPermi="['userApplication:template:edit']"
              >编辑模板</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-if="scope.row.createPersonal == userId"
                v-hasPermi="['userApplication:template:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-if="scope.row.createPersonal == userId"
                v-hasPermi="['userApplication:template:remove']"
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
      </el-col>
    </el-row>
    <!-- 添加或修改审批流程模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模板编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入模板编码" />
        </el-form-item>
        <el-form-item label="模板标题" prop="title">
          <el-input  type="textarea" style="font-size: 14px;color:#333;font-weight: bold;" :rows="3" v-model="form.title" placeholder="请输入模板标题" />
        </el-form-item>
        <el-form-item label="归属部门" prop="dept">
          <Treeselect
            v-model="form.dept"
            :options="deptOptions"
            :show-count="true"
            placeholder="请选择归属部门"/>
        </el-form-item>
        <el-form-item label="备注" prop="mark">
          <el-input type="textarea" style="font-size: 14px;color:#666;" :rows="8" v-model="form.mark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <template>
            <el-switch
              v-model="form.status"
              active-value= "1"
              inactive-value= "0"
              active-text="启用"
              inactive-text="停用"
              inactive-color="#ff9292"
            ></el-switch>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-drawer
      :title="purchaseLink.title"
      :visible.sync="purchaseLink.open"
      direction="ttb"
      size="100%"
      destroy-on-close>
      <flow-panel
        :purchase="purchaseLink" :isEdit="isEdit"/>
      <!--        <PurchaseLinkBox :purchase="purchaseLink"/>-->
    </el-drawer>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import { treeselect } from "@/api/system/dept";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import FlowPanel from '@/components/ef/panel'
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/userApplication/template";
import { getUserProfile } from "@/api/system/user";
import ShowStatus from "../base/ShowStatus";
import purchaseSub from "../purchaseSub/index";
import SelectTree from "../base/libraryElement";
export default {
  name: "Template",
  components: {ShowStatus,FlowPanel,SelectTree,Treeselect},
  data() {
    return {
      purchaseLink:{
        open:false,
        title:'',
        purchase:{},
        template:{},
        showPurchaseId:undefined,//待展示的流程id
      },

      // 部门树选项
      deptOptions: undefined,
      // 部门名称
      deptName: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },


      isEdit:true,
      userId:-1,
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
      // 审批流程模板表格数据
      templateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        title: null,
        dept:null,
        template: null,
        mark: null,
        status: null,
        createPersonal: null,
        updatePersonal: null,
      },
      curDept:{ },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    // this.getList();
  },
  mounted() {
    this.getTreeselect();
    getUserProfile().then(res=>{
      this.userId =  res.data.userId ;
    });
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询审批流程模板列表 */
    getList() {
      if(undefined == this.queryParams.dept || null == this.queryParams.dept){
        this.$modal.msgError("部门信息未选择");
        this.templateList = [];
        this.total = 0;
        return;
      }
      this.loading = true;
      this.queryParams.status = '1';
      listTemplate(this.queryParams).then(response => {
        this.templateList = response.rows;
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
        title: null,
        template: null,
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
      this.single = selection.length!==1;
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加审批流程模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTemplate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改审批流程模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplate(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除审批流程模板编号为"' + ids + '"的数据项？').then(function() {
        return delTemplate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/template/export', {
        ...this.queryParams
      }, `template_${new Date().getTime()}.xlsx`)
    },

    /** 查看处理流程 */
    handleBrowseLink(row,isEdit) {
      this.purchaseLink.template = {};
      this.isEdit=isEdit;
      this.purchaseLink.showPurchaseId = row.id;
      getTemplate(this.purchaseLink.showPurchaseId).then(res => {
        // this.$modal.msgSuccess(this.purchaseLink.showPurchaseId);
        this.purchaseLink.template = res.data;

        this.purchaseLink.title = '关于《' + row.title + '》流程模板信息如下所示：';
        this.purchaseLink.open = true;
        // this.$modal.msgSuccess(this.purchaseLink.template);
      });
    },

    //选择部门后执行查询
    selectDept(selectData){
      this.queryParams.dept = selectData.curData.id;
      this.handleQuery();
    },
  }
};
</script>
