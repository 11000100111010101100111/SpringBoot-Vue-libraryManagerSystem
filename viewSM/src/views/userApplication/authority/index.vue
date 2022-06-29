<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
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
          v-hasPermi="['userApplication:authority:add']"
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
          v-if="false"
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
          v-if="false"
          v-hasPermi="['userApplication:authority:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:authority:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authorityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" v-if="false"/>
      <el-table-column label="用户名称" align="center" >
        <template slot-scope="solt" v-if="null != solt.row.user">
          <FomatLabel :label="solt.row.user.userName"/>
        </template>
      </el-table-column>
      <el-table-column label="所在部门" align="center" >
        <template slot-scope="solt" v-if="null != solt.row.dept">
          <FomatLabel :label="solt.row.dept.deptName"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" >
        <template slot-scope="solt" v-if="null != solt.row.createUser">
          <FomatLabel :label="solt.row.createUser.userName"/>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" >
        <template slot-scope="solt">
          <FomatLabel label="-" v-if="undefined == solt.row.updateUser || null == solt.row.updateUser"/>
          <FomatLabel :label="solt.row.updateUser.userName" v-if="undefined != solt.row.updateUser && null != solt.row.updateUser"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.user.userId !== 1">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="changeAuthority(scope.row)"
            v-hasPermi="['userApplication:authority:edit']"
          >管理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:authority:remove']"
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

    <!-- 添加用户可访问部门信息对话框 -->
    <el-dialog :title="addAuthority.title" :visible.sync="addAuthority.open" width="800px" append-to-body>
      <el-form ref="form" label-width="80px" >
        <el-form-item label="归属部门" prop="deptId">
          <el-row :gutter="10">
            <el-col :span="10">
              <treeselect
                v-model="addAuthority.query.deptId"
                :options="addAuthority.deptOptions"
                :show-count="true"
                placeholder="请选择归属部门"/>
            </el-col>
            <el-col :span="5">
              <el-button type="primary" @click="deptSelect">确定</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="选择用户">
          <el-table
            v-loading="addAuthority.loading"
            :data="addAuthority.userList"
            @selection-change="handleUserSelectionChange"
            style="max-height: 50vh;overflow-y: auto;">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="false"/>
            <el-table-column label="头像" align="center" key="avatar" prop="avatar" >
              <template scope="solt">
                <div class="text-center"><el-avatar shape="square" :size="50" fit="scale-down" :src="solt.row.avatar" /></div>
              </template>
            </el-table-column>
            <el-table-column label="用户名称" align="center" key="userName" prop="userName" :show-overflow-tooltip="true" />
            <el-table-column label="用户昵称" align="center" key="nickName" prop="nickName"  :show-overflow-tooltip="true" />
          </el-table>
          <pagination
            v-show="addAuthority.query.total>0"
            :total="addAuthority.query.total"
            :page.sync="addAuthority.query.pageNum"
            :limit.sync="addAuthority.query.pageSize"
            @pagination="getUserList"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleUserAdd">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--设置可访问部门-->
    <el-drawer
      :title="authority.title"
      :visible.sync="authority.open"
      direction="rtl"
      size="30%"
      :before-close="changeAuthorityClose">
      <el-divider content-position="left"><i class="el-icon-s-order" style="font-size: 16px;"></i>用户信息</el-divider>
      <el-row>
        <el-col v-if="undefined != authority.user">
          <el-descriptions class="margin-top" :column="1" :size="10" :colon="false" v-if="undefined != authority.user.avatar">
            <el-descriptions-item >
              <el-avatar shape="square" :size="60" fit="scale-down" :src="authority.user.avatar" ></el-avatar>
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions class="margin-top" :column="2" border>
            <el-descriptions-item v-if="undefined != authority.user.userName">
              <template slot="label">
                <i class="el-icon-user"></i>
                用户名
              </template>
              {{authority.user.userName}}
            </el-descriptions-item>
            <el-descriptions-item v-if="undefined != authority.user.nickName">
              <template slot="label">
                <i class="el-icon-s-flag"></i>
                别名
              </template>
              {{authority.user.nickName}}
            </el-descriptions-item>
            <el-descriptions-item v-if="undefined != authority.user.phonenumber">
              <template slot="label">
                <i class="el-icon-mobile-phone"></i>
                手机号
              </template>
              {{authority.user.phonenumber}}
            </el-descriptions-item>
            <el-descriptions-item v-if="undefined != authority.user.email">
              <template slot="label">
                <i class="el-icon-s-promotion"></i>
                邮箱
              </template>
              {{authority.user.email}}
            </el-descriptions-item>
            <el-descriptions-item v-if="undefined != authority.user.sex">
              <template slot="label">
                <i class="el-icon-male" v-if="authority.user.sex == 1"></i>
                <i class="el-icon-female" v-if="authority.user.sex == 2"></i>
                <i class="el-icon-question" v-if="authority.user.sex != 1 && authority.user.sex != 2"></i>
                性别
              </template>
              <SexLabel :sex="authority.user.sex"/>
            </el-descriptions-item>
            <el-descriptions-item v-if="undefined != authority.dept && undefined != authority.dept.deptName">
              <template slot="label">
                <i class="el-icon-location-outline"></i>
                所属部门
              </template>
              {{authority.dept.deptName}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-divider content-position="left"><i class="el-icon-s-claim" style="font-size: 16px;"></i>选择部门</el-divider>
      <el-row>
        <el-col>
            <el-form ref="authorityForm" label-width="80px">
              <el-form-item label="部门权限">
                <el-checkbox v-model="authority.menuExpand" @change="handleCheckedTreeExpand($event, 'dept')">展开/折叠</el-checkbox>
                <el-checkbox v-model="authority.menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">全选/全不选</el-checkbox>
                <!--            <el-checkbox v-model="true" @change="handleCheckedTreeConnect($event, 'dept')">父子联动</el-checkbox>-->
                <el-tree
                  class="tree-border"
                  :data="addAuthority.deptOptions"
                  show-checkbox
                  ref="deptAuthority"
                  node-key="id"
                  :check-strictly="!authority.form.menuCheckStrictly"
                  empty-text="加载中，请稍候"
                  :props="addAuthority.defaultProps"
                  style="max-height: 45vh;min-height: 45vh;overflow-y: auto;"
                ></el-tree>
              </el-form-item>
            </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="1" :push="3">
          <el-button type="primary" icon="el-icon-switch-button" @click="saveAuthority">确定</el-button>
        </el-col>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
  import { getDept } from "@/api/system/dept";
  import { listUser, getUser } from "@/api/system/user";
import { listAuthority, getAuthority, delAuthority, addAuthority, updateAuthority,addAuthoritys,addDeptAuthority,getAuthorityDept } from "@/api/userApplication/authority";
import { appendSrcPiffect } from "@/api/userApplication/file";
  import { treeselect } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import SexLabel from "../base/SexLabel";
  import Template from "../template/index";
  import FomatLabel from "../base/FomatLabel";
export default {
  name: "Authority",
  components: {Template, SexLabel, Treeselect, FomatLabel},
  dicts: ['base_status', 'base_del'],
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
      // 用户可访问部门信息表格数据
      authorityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        userId: null,
      },
      addAuthority:{
        open:false,
        title:'',
        query:{
          deptId:undefined,//已选择的部门
          total:0,
          pageNum:1,
          pageSize:10,
        },
        // 部门树选项
        deptOptions: undefined,
        defaultProps: {
          children: "children",
          label: "label"
        },
        loading:false,
        userId:[],//已 选择的用户ID列表
        multiple:true,
        single:true,
        userList:[],//用户列表
      },
      authority:{
        title:'设置部门权限',
        open:false,
        menuExpand: false,
        menuNodeAll: false,
        deptExpand: true,
        deptNodeAll: false,
        user:{},
        dept:{},
        form:{
           roleId: undefined,
           roleName: undefined,
           roleKey: undefined,
           roleSort: 0,
           status: "1",
           userId:0,
           menuIds: [],
           deptIds: [],
           menuCheckStrictly: true,
           deptCheckStrictly: true,
           remark: undefined
         }
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
    /** 查询用户列表 */
    getUserList() {
      this.addAuthority.query.loading = true;
      listUser(this.addAuthority.query).then(response => {
          this.addAuthority.userList = response.rows;
          this.addAuthority.query.total = response.total;
          if (undefined != this.addAuthority.userList){
            for (let i = 0;i<this.addAuthority.userList.length;i++){
              this.addAuthority.userList[i].avatar = appendSrcPiffect(this.addAuthority.userList[i].avatar);
            }
          }

          this.addAuthority.query.loading = false;
        }
      );
    },
    /** 查询用户可访问部门信息列表 */
    getList() {
      this.loading = true;
      listAuthority(this.queryParams).then(response => {

        let bound = this.queryParams.pageSize*this.queryParams.pageNum-1;

        let start = (this.queryParams.pageNum-1)*this.queryParams.pageSize;
        let end = Math.min(bound, response.rows.length);
        for (let i = start;i<end;i++){
          this.authorityList.push(response.rows[i]);
        }
        this.total = response.rows.length;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.addAuthority.open = false;
      this.addAuthority.userList = [];
      this.addAuthority.query.deptId = undefined;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        deptId: [],
        userId: null,
        status: "0",
        isDel: "0",
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
      this.ids = selection.map(item => item.user.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.addAuthority.open = true;
      this.addAuthority.title = "添加用户的部门权限";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.changeAuthority(row);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.deptId = this.form.deptId.join(",");
          if (this.form.id != null) {
            updateAuthority(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.addAuthority.open = false;
              this.getList();
            });
          } else {
            addAuthority(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.addAuthority.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.user.userId || this.ids;
      this.$modal.confirm('是否确认移除用户'+row.user.userName+'的全部可访问部门信息？').then(function() {
        return delAuthority(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/authority/export', {
        ...this.queryParams
      }, `authority_${new Date().getTime()}.xlsx`)
    },


    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.addAuthority.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.addAuthority.query.deptId = data.id;
      // this.handleQuery();
      this.getUserList();
    },

    deptSelect(){
      if (undefined == this.addAuthority.query.deptId || '' == this.addAuthority.query.deptId){
        this.addAuthority.userList = [];
        return;
      }
      this.getUserList();
    },
    // 多选框选中数据
    handleUserSelectionChange(selection){
      this.addAuthority.userId = selection.map(item => item.userId)
      this.addAuthority.single = selection.length!==1
      this.addAuthority.multiple = !selection.length
    },
    handleUserAdd(){
      //添加用户，默认添加的用户只具备所处部门权限
      if (undefined == this.addAuthority.userId || this.addAuthority.userId.length<=0){
        this.$modal.confirm('暂未选择用户，请选择后再提交');
        return;
      }
      addAuthoritys(this.addAuthority.userId).then(response => {
        this.getList();
        this.addAuthority.open = false;
        this.$modal.confirm(response.msg);
      });
    },

    /**设置可访问部门**/
    changeAuthority(row){
      this.getTreeselect();
      getUser(row.userId).then(res=>{
        this.authority.user = res.data;
        if (undefined != this.authority.user && undefined != this.authority.user.avatar){
          this.authority.user.avatar = appendSrcPiffect( this.authority.user.avatar);
          getDept(row.deptId).then(res=>{
            this.authority.dept = res.data;
          });
          // this.$modal.msgSuccess(this.authority.user.userId);
          getAuthorityDept(this.authority.user.userId).then(res=>{
            let deptIds = res.data;
            this.setDeptTreeValue(deptIds);
          });
          this.authority.open = true;
        }
      });
    },
    saveAuthority(){
      this.$confirm('确认保存？')
        .then(_ => {
            this.authority.form.userId = this.authority.user.userId;
            this.authority.form.deptIds =  this.getDeptAllCheckedKeys();
          // this.$modal.msgSuccess( this.authority.form.deptIds);
          addDeptAuthority(this.authority.form).then(res=>{
              this.getList();
              this.authority.open = false;

              this.$modal.msgSuccess("设置成功");
            });
        })
        .catch(_ => {});
    },
    changeAuthorityClose(){
      this.$confirm('信息未提交，确认关闭？')
        .then(_ => {
          this.authority.open = false;
        })
        .catch(_ => {});
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.addAuthority.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == 'dept') {
        let treeList = this.addAuthority.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.deptAuthority.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.addAuthority.menuOptions: []);
      } else if (type == 'dept') {
        this.$refs.deptAuthority.setCheckedNodes(value ? this.addAuthority.deptOptions: []);
      }
    },
    setDeptTreeValue(ids){
      this.$refs.deptAuthority.setCheckedNodes(ids);
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.authority.form.menuCheckStrictly = value ? true: false;
      } else if (type == 'dept') {
        this.authority.form.deptCheckStrictly = value ? true: false;
      }
    },
    // 所有部门节点数据
    getDeptAllCheckedKeys() {
      // 目前被选中的部门节点
      let checkedKeys = this.$refs.deptAuthority.getCheckedKeys();
      // 半选中的部门节点
      let halfCheckedKeys = this.$refs.deptAuthority.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    }
  }
};
</script>
