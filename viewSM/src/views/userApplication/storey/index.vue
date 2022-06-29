<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24" >
        <dept-tree v-on:selectNode="selectDept"/>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="书架编码" prop="code">
            <el-input
              v-model="queryParams.code"
              placeholder="请输入书架编码"
              clearable
              size="small"
              @keyup.enter.native="handleStoreyQuery"
            />
          </el-form-item>
          <el-form-item label="书架名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入书架名称"
              clearable
              size="small"
              @keyup.enter.native="handleStoreyQuery"
            />
          </el-form-item>
          <el-form-item label="书架标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入书架标题"
              clearable
              size="small"
              @keyup.enter.native="handleStoreyQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleStoreyQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetStoreyQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-divider content-position="left"><i class="el-icon-location-information"></i>
          <el-tag
            v-if="queryParams.bookShelfNode !== undefined && queryParams.bookShelfNode !== ''"
            type="danger"
            effect="dark" style="margin-right: 5px;">
            {{ queryParams.bookShelfNode.label }}
          </el-tag>
          <el-tag
            v-for="lab in queryParams.deptAllNode"
            :key="lab"
            :type="lab.type"
            effect="dark"
            v-if="lab.label != undefined && '' !== lab.label"
            style="margin-left: 3px;">{{lab.label}}</el-tag>
        </el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleStoreyAdd"
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
              @click="handleStoreyUpdate"
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
              @click="handleStoreyDelete"
              v-hasPermi="['userApplication:bookshelf:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleStoreyExport"
              v-hasPermi="['userApplication:bookshelf:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table
          ref="parentTable"
          class="parent-table"
          v-loading="loading"
          :data="bookshelfList"
          stripe="stripe"
          @expand-change="expandSelect"
          :row-key="getRowKeys"
          :expand-row-keys="expands"
          show-summary
          :summary-method="getSummariesCount"
          @row-click="clickTable"
          @selection-change="handleSelectionChange" style="max-height: 64vh;overflow-y: auto;">
          <el-table-column type="expand" >
            <template slot-scope="slot">
              <el-table :load="loadStorey"
                        class="sub-table"
                        :data="storeyList"
                        @selection-change="handleStoreySelectionChange" style="width: 100%;">
                <el-table-column type="selection" width="100" align="center" />
                <el-table-column label="层ID" align="center" prop="id" v-if="false"/>
                <el-table-column label="层编码" align="center" prop="code" width="100">
                  <template slot-scope="scope">
                    <el-tag  effect="dark">{{scope.row.code}}</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="层名称" align="center" prop="name" />
                <el-table-column label="层标题" align="center" prop="title" >
                  <template slot-scope="scope">
                    <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                      <span>{{scope.row.title.substring(0,15)}}...</span>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column label="图书类别" align="center" prop="bookClass.name" />
                <el-table-column label="备注" align="center" prop="mark" v-if="false">
                  <template slot-scope="scope">
                    <el-tooltip class="item" effect="dark" :content="scope.row.mark" placement="top-start">
                      <span>{{scope.row.mark.substring(0,10)}}...</span>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column label="状态" align="center" prop="status" width="50">
                  <template slot-scope="scope">
                    <el-switch
                      v-model="scope.row.status"
                      active-value= "1"
                      inactive-value= "0"
                      inactive-color="#ff9292"
                      @change="handleStoreyStatusChange(scope.row)"
                    ></el-switch>
                  </template>
                </el-table-column>
                <el-table-column label="书架" align="center" prop="bookShelf.name" v-if="false"/>
                <el-table-column label="创建人" align="center" prop="createUser.userName" width="80"/>
                <el-table-column label="更新人" align="center" prop="updateUser.userName" width="80"/>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleStoreyUpdate(scope.row)"
                      v-hasPermi="['userApplication:storey:edit']"
                    >修改</el-button>
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-delete"
                      @click="handleStoreyDelete(scope.row)"
                      v-hasPermi="['userApplication:storey:remove']"
                    >删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>

          <el-table-column type="index" width="55" align="center" prop="index">

          </el-table-column>
          <el-table-column label="书架编码" align="center" prop="code" >
            <template slot-scope="scope">
              <el-tag  effect="dark" type="danger">{{scope.row.code}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="名称" width="150" align="center" prop="name" >
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" :content="scope.row.name" placement="top-start">
                <span>{{scope.row.name.substring(0,5)}}...</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="书架id" align="center" prop="id" v-if="false"/>
          <el-table-column label="所属部门" width="200" align="center" prop="sysDept.deptName" />
          <el-table-column label="存书类别" width="250" align="center" prop="bookClassCn.name" >
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" :content="scope.row.bookClassCn.name" placement="top-start">
                <span>{{scope.row.bookClassCn.name.substring(0,5)}}...</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="标题" width="250" align="center" prop="title" >
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                <span>{{scope.row.title.substring(0,5)}}...</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="备注" width="300" align="center" prop="mark" >
            <template slot-scope="scope">
              <el-tooltip class="item" effect="dark" :content="scope.row.mark" placement="top-start">
                <span>{{scope.row.mark.substring(0,5)}}...</span>
              </el-tooltip>
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


    <!-- 添加或修改书架层信息对话框 -->
    <el-dialog :title="titleStorey" :visible.sync="openStorey" width="60%" append-to-body>
      <div>
        <el-row>
          <el-col :span="14">
              <el-form ref="storeyForm" :model="storeyForm" :rules="rules" label-width="80px">
                <el-form-item label="书架信息" prop="bookshelf">
                  <!--          <el-input v-model="storeyForm.bookshelf" placeholder="请输入书架ID" />-->
                  <el-tag
                    v-if="undefined != queryParams.bookShelfNode"
                    type="danger"
                    effect="dark" >{{queryParams.bookShelfNode.label}}</el-tag>
                </el-form-item>
    <!--            <el-divider content-position="left"></el-divider>-->
                <el-form-item label="编码" prop="code">
                  <el-input v-model="storeyForm.code" placeholder="请输入层编码" />
                </el-form-item>
                <el-form-item label="名称" prop="name">
                  <el-input v-model="storeyForm.name" placeholder="请输入层名称" />
                </el-form-item>
                <el-form-item label="标题" prop="title">
                  <el-input v-model="storeyForm.title" placeholder="请输入层标题" />
                </el-form-item>
                <el-form-item label="备注" prop="mark">
                  <el-input type="textarea"
                            :rows="10"
                            maxlength="2000"
                            v-model="storeyForm.mark"
                            placeholder="请输入备注" />
                </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="6">
            <div style="margin-left: 10px;">
              <p style="font-size: 12px;color:#666;">请选择图书类别:</p>
<!--              <el-form-item label="图书类别" prop="bookType">-->
                <!--图书类别-->
<!--                <el-col :span="4" :xs="24">-->
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
                      style="width: 400px;max-height: 400px;overflow-y: auto;"
                    />
                  </div>
<!--                </el-col>-->
<!--              </el-form-item>-->
            </div>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitStoreyForm">确 定</el-button>
        <el-button @click="cancelStorey">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listBookshelf, getBookshelf, delBookshelf, addBookshelf, updateBookshelf, changeBookShelfStatus } from "@/api/userApplication/bookshelf";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import { bookclasstreeselect } from "@/api/main/bookClass";
  import { listStorey, getStorey, delStorey, addStorey, updateStorey, changeStoreyStatus } from "@/api/userApplication/storey";
  import DeptTree from "../base/libraryElement";
  export default {
    name: "Storey",
    components: {DeptTree, Treeselect },
    data() {
      return {
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

        // 遮罩层
        loadingStorey: true,
        // 选中数组
        idsStorey: [],
        // 非单个禁用
        singleStorey: true,
        // 非多个禁用
        multipleStorey: true,
        // 显示搜索条件
        showSearchStorey: true,
        // 总条数
        totalStorey: 0,
        // 书架层信息表格数据
        storeyList: [],
        // 弹出层标题
        titleStorey: "",
        // 是否显示弹出层
        openStorey: false,
        // 查询参数
        queryStoreyParams: {
          pageNum: 1,
          pageSize: 1000,
          code: null,
          name: null,
          title: null,
          bookType: null,
          mark: null,
          status: null,
          bookshelf: null,
          createPersonal: null,
          updatePersonal: null
        },
        // 表单参数
        storeyForm: {},

        //主表唯一索引
        expands: [],
        isOpen:{id:undefined,boo:false},
        getRowKeys(row) {
          return row.id;
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
        this.resetForm("queryStoreyForm");
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
        this.getBookClassTreeselect();//获取图书类别下拉树结构
        this.title = "添加书架信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.getBookClassTreeselect();//获取图书类别下拉树结构
        const id = row.id || this.ids
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

      //选择部门后执行查询
      selectDept(selectData){
          this.cleanStoreyShelf();

          this.queryParams.dept = selectData.curData.id;
          this.queryParams.deptName = selectData.curData.label;

          //获取当前结点全体祖先结点信息
          this.queryParams.deptAllNode = [];
          //let curNode = selectData.curNode;
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
        this.storeyForm.bookType = data.id;
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
        this.queryParams.sumDept = sums;
        return sums;
      },




      /** 查询书架层信息列表 */
      getStoreyList() {
        this.loadingStorey = true;
        listStorey(this.queryStoreyParams).then(response => {
          this.storeyList = response.rows;
          this.totalStorey = response.total;
          this.loadingStorey = false;
        });
      },
      // 取消按钮
      cancelStorey() {
        this.openStorey = false;
        this.resetStorey();
      },
      // 表单重置
      resetStorey() {
        let bookShelf = this.storeyForm.bookshelf;
        this.storeyForm = {
          id: null,
          code: null,
          name: null,
          title: null,
          bookType: null,
          mark: null,
          status: 0,
          bookshelf: null,
          createTime: null,
          createPersonal: null,
          updateTime: null,
          updatePersonal: null
        };
        this.resetForm("storeyForm");
        this.storeyForm.bookshelf = bookShelf;
      },
      cleanStoreyShelf(){
        this.queryParams.bookShelfNode = undefined;//清空当前选中的书架
        this.queryStoreyParams.bookshelf = undefined;
        this.queryParams.bookShelfNode = undefined;
      },
      /** 搜索按钮操作 */
      handleStoreyQuery() {
        this.queryStoreyParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetStoreyQuery() {
        this.resetForm("queryStoreyForm");
        this.handleStoreyQuery();
      },
      // 多选框选中数据
      handleStoreySelectionChange(selection) {
        this.idsStorey = selection.map(item => item.id);
        this.singleStorey = selection.length!==1;
        this.multipleStorey = !selection.length
      },
      /** 新增按钮操作 */
      handleStoreyAdd() {
        if(undefined == this.queryStoreyParams.bookshelf || '' == this.queryStoreyParams.bookshelf){
          this.$modal.msgError("对书架增加层信息前，请先选择书架！");
          return;
        }
        let bookShelf = this.storeyForm.bookshelf;
        this.resetStorey();
        this.getBookClassTreeselect();//获取图书类别下拉树结构
        this.openStorey = true;
        this.titleStorey = "添加书架层信息";
        this.storeyForm.bookshelf = bookShelf;
      },
      /** 修改按钮操作 */
      handleStoreyUpdate(row) {
        let bookShelf = this.storeyForm.bookshelf;
        this.resetStorey();
        this.storeyForm.bookshelf = bookShelf;
        const id = row.id || this.ids;
        this.getBookClassTreeselect();//获取图书类别下拉树结构
        getStorey(id).then(response => {
          this.storeyForm = response.data;
          this.openStorey = true;
          this.titleStorey = "修改书架层信息";
        });
      },
      /** 提交按钮 */
      submitStoreyForm() {
        this.$refs["storeyForm"].validate(valid => {
          if (valid) {
            if (this.storeyForm.id != null) {
              updateStorey(this.storeyForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.openStorey = false;
                this.getList();
              });
            } else {
              addStorey(this.storeyForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.openStorey = false;
                this.getList();
              });
            }
            this.refulshList();
          }
        });
      },
      /** 删除按钮操作 */
      handleStoreyDelete(row) {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除书架层信息编号为"' + ids + '"的数据项？').then(function() {
          return delStorey(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleStoreyExport() {
        this.download('userApplication/storey/export', {
          ...this.queryStoreyParams
        }, `storey_${new Date().getTime()}.xlsx`)
      },
      loadStorey(tree, treeNode, resolve) {
        //this.$modal.msgSuccess("");
        this.queryStoreyParams.bookshelf = tree.id;
        this.queryStoreyParams.bookShelfName = tree.name+'('+ tree.code+')';
        let list = this.getStoreyList();

        this.queryParams.bookShelfNode = {};//当前选择的书架
        this.queryParams.bookShelfNode = {'id': tree.id, 'label': tree.name+'('+ tree.code+')','type':'danger'};
        this.loading = true;//重新加载
        this.loading = false;
        resolve(list);
      },
      // 点击行展开事件
      clickTable(row, index, e) {
        this.$refs.refTable.toggleRowExpansion(row);
      },
      // 折叠面板每次只能展开一行
      expandSelect(row, expandedRows) {
        if( undefined != this.queryParams.bookShelfNode && row.id == this.queryParams.bookShelfNode.id) {
          this.queryParams.bookShelfNode = undefined;//当前选择的书架
          this.queryStoreyParams.bookshelf = undefined;
          this.loading = true;//重新加载
          this.loading = false;
          return;
        }

        let that = this;
        if (expandedRows.length) {
          that.expands = [];
          if (row) {
            that.expands.push(row.id); // 每次push进去的是每行的ID
          }
        } else {
          that.expands = []; // 默认不展开
        }

        this.queryStoreyParams.bookshelf = row.id;
        this.queryStoreyParams.bookShelfName = row.name+'('+ row.code+')';
        this.storeyForm.bookshelf = row.id;

        this.queryParams.bookShelfNode = {};//当前选择的书架
        this.queryParams.bookShelfNode = {'id': row.id, 'label': row.name + '(' + row.code + ')', 'type': 'danger'};
        this.loading = true;//重新加载
        this.loading = false;

        this.refulshList();
      },
      /** 状态修改*/
      handleStoreyStatusChange(row) {
        let text = row.status === "1" ? "启用" : "停用";

        this.$modal.confirm('确认要"' + text + '""' + row.name + '"这一层吗？').then(function() {
          return changeStoreyStatus( row.id , row.status);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      refulshList(){
        let list = this.getStoreyList();
        resolve(list);
      }
    }
  };
</script>
