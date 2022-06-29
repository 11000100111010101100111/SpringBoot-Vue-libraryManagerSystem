<template>
  <div class="app-container">
    <el-row :gutter="20" >
      <!--部门数据-->
      <el-col :span="4" :xs="24" >
        <dept-tree v-on:selectNode="selectDept"/>
      </el-col>
      <el-col :span="20" :xs="24" >
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="书架编码" prop="code">
            <el-input
              v-model="queryParams.code"
              placeholder="请输入书架编码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="书架名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入书架名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="书架标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入书架标题"
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
        <el-divider content-position="left"><i class="el-icon-location-information"></i>
<!--          <el-tag-->
<!--            v-if="queryParams.deptName !== undefined && queryParams.deptName !== ''"-->
<!--            type="warning"-->
<!--            effect="dark" style="margin-right: 5px;">-->
<!--            {{ queryParams.deptName }}-->
<!--          </el-tag>-->
          <el-tag
            v-for="lab in queryParams.deptAllNode"
            :key="lab"
            :type="lab.type"
            effect="dark"
            v-if="lab.label != undefined || '' !== lab.label"
          style="margin-left: 3px;">{{lab.label}}</el-tag>
        </el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['userApplication:bookshelf:add']"
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
              v-hasPermi="['userApplication:bookshelf:edit']"
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
              v-hasPermi="['userApplication:bookshelf:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['userApplication:bookshelf:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table
          v-loading="loading"
          :data="bookshelfList"
          stripe="stripe"
          highlight-current-row="highlight-current-row"
          max-height="600"
          show-summary
          :summary-method="getSummariesCount"
          @selection-change="handleSelectionChange"  >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="编码" align="center" prop="code" fixed width="100"/>
          <el-table-column label="名称" width="250" align="center" prop="name" fixed>
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" :content="scope.row.name" placement="top-start">
                <span>{{scope.row.name.substring(0,30)}}...</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="书架id" align="center" prop="id" v-if="false"/>
          <el-table-column label="所属部门" width="200" align="center" prop="sysDept.deptName" />
          <el-table-column label="存书类别" width="300" align="center" prop="bookClassCn.name" />
          <el-table-column label="标题" width="300" align="center" prop="title" />
          <el-table-column label="备注" width="300" align="center" prop="mark" v-if="false"/>
          <el-table-column label="创建人" align="center" prop="createUser.userName" width="80"/>
          <el-table-column label="更新人" align="center" prop="updateUser.userName" width="80">
            <template slot-scope="solt">
                        <FomatLabel label="-" v-if="undefined == solt.row.updateUser"/>
          <FomatLabel :label="solt.row.updateUser.userName" v-if="undefined != solt.row.updateUser"/>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status" fixed="right" width="80">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value= "1"
                inactive-value= "0"
                inactive-color="#ff9292"
                @change="handleBookShelfStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="150"
            class-name="small-padding fixed-width"
            fixed="right">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['userApplication:bookshelf:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['userApplication:bookshelf:remove']"
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


    <!-- 添加或修改书架信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="65%" append-to-body>
      <div>
        <el-row>
          <el-col :span="14">
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
              <el-form-item label-width="120px" label="归属部门" prop="dept">
                <treeselect v-model="form.dept" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
              </el-form-item>
<!--              <el-divider/>-->
              <el-form-item label-width="120px" label="书架编码" prop="code">
                <el-input prefix-icon="book-biaoshuziliao" v-model="form.code" placeholder="请输入书架编码" />
              </el-form-item>
              <el-form-item label-width="120px" label="书架名称" prop="name">
                <el-input prefix-icon="book-ziliaoyichang" v-model="form.name" placeholder="请输入书架名称" />
              </el-form-item>
              <el-form-item label-width="120px" label="书架标题" prop="title">
                <el-input prefix-icon="book-shangchuan4" v-model="form.title" placeholder="请输入书架标题" />
              </el-form-item>
              <el-form-item label-width="120px" label="备注" prop="mark">
                <el-input type="textarea"
                          :rows="10"
                          maxlength="2000"
                          v-model="form.mark"
                          placeholder="请输入备注" />
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="6">
                <div style="margin-left: 10px;">
                  <p style="font-size: 12px;color:#666;">请选择图书类别:</p>
                  <div class="head-container">
                    <el-input
                      v-model="bookClassName"
                      placeholder="请选择图书类别名称"
                      clearable
                      size="small"
                      style="margin-bottom: 10px;width:400px"
                    />
                  </div>
                  <div class="head-container">
                    <el-tree
                      :data="bookClassOptions"
                      :props="defaultbookClassProps"
                      :expand-on-click-node="false"
                      :filter-node-method="filterBookClassNode"
                      ref="tree"
                      @node-click="handleBookClassNodeClick"
                      style="width: 400px;max-height: 600px;overflow-y: auto;"
                    />
                  </div>
                </div>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import { treeselect } from "@/api/system/dept";
import { listBookshelf, getBookshelf, delBookshelf, addBookshelf, updateBookshelf, changeBookShelfStatus } from "@/api/userApplication/bookshelf";
import { bookclasstreeselect } from "@/api/main/bookClass";
import FomatLabel from "../base/FomatLabel";
import DeptTree from "../base/libraryElement";
export default {
  name: "Bookshelf",
  components: { Treeselect, FomatLabel,DeptTree },
  data() {
    return {
      // 部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },


      //图书类别名称
      bookClassName:'',
      // 图书类别树选项
      bookClassOptions: undefined,
      defaultbookClassProps: {
        children: "children",
        label: "label"
      },

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
      // 书架信息表格数据
      bookshelfList: [],
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
        title: null,
        bookType: null,
        mark: null,
        status: null,
        dept: null,
        createPersonal: null,
        updatePersonal: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // dept:[{required: true, message: "书架所属部门不能为空", trigger: "blur"}],
        // bookClassName:[{required: true, message: "书架存书类别不能为空", trigger: "blur"}],
        code:[
          { required: true, message: "书架编码不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '书架编码长度必须介于 2 和 20 之间的字母数字组成', trigger: 'blur' }
        ],
        name:[
          { required: true, message: "书架名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '书架名称长度必须介于 2 和 20 之间的字符串组成', trigger: 'blur' }
          ],
        title:[
          { required: true, message: "书架名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '书架名称长度必须介于 2 和 20 之间的字符串组成', trigger: 'blur' }
        ]
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询书架信息列表 */
    getList() {
      this.loading = true;
      listBookshelf(this.queryParams).then(response => {
        this.bookshelfList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        }).catch(()=>{
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
        title: null,
        bookType: null,
        mark: null,
        status: 0,
        dept: null,
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
      this.getTreeselect();
      this.getBookClassTreeselect();//获取图书类别下拉树结构
      this.title = "添加书架信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getBookClassTreeselect();//获取图书类别下拉树结构
      const id = row.id || this.ids;
      getBookshelf(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改书架信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBookshelf(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBookshelf(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除书架信息编号为"' + ids + '"的数据项？').then(function() {
        return delBookshelf(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/bookshelf/export', {
        ...this.queryParams
      }, `bookshelf_${new Date().getTime()}.xlsx`)
    },

    // 状态修改
    handleBookShelfStatusChange(row) {
      let text = row.status === "1" ? "启用" : "停用";

      this.$modal.confirm('确认要"' + text + '""' + row.name + '"吗？').then(function() {
        return changeBookShelfStatus( row.id , row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },

    // 部门树节点单击事件
    selectDept(selectData) {
      this.queryParams.dept = selectData.curData.id;
      this.queryParams.deptName = selectData.curData.label;

      //获取当前结点全体祖先结点信息
      this.queryParams.deptAllNode = [];
      let isStart = true;

      if(undefined != selectData.nodes ) {
        for (let i = 0; i < selectData.nodes.length; i++) {
          let node = selectData.nodes[i];
          if (undefined != node.id) {
            this.queryParams.deptAllNode.push({
              'id': node.id,
              'label': node.label,
              'type': isStart ? 'warning' : 'success'
            });
          }
          if (isStart) {
            isStart = false;
          }
        }
      }
      this.handleQuery();
    },


    /** 查询图书类别下拉树结构 */
    getBookClassTreeselect() {
      bookclasstreeselect().then(response => {
        this.bookClassOptions = response.data;
      });
    },
    // 筛选图书类别树节点
    filterBookClassNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 图书类别树节点单击事件
    handleBookClassNodeClick(data) {
      this.bookClassName = data.label;
      this.form.bookType = data.id;
      //this.queryParams.deptId = data.id;
      // this.handleQuery();
    },
    /**书架总计*/
    getSummariesCount(param){
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 1) {
          sums[index] = '合计';
          return;
        }
        else if(index === 2){
          let dept = this.queryParams.deptName;
          dept = undefined===dept || dept===''?'在册机构':dept;
          sums[index] = dept+'-共'+ this.total+'个书架';
          return;
        }else{
          sums[index] = '';
          return;
        }
      });

      return sums;
    }
  }
};
</script>
