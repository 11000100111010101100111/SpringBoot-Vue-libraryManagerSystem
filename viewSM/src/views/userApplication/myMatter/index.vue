<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="false" v-show="showSearch" label-width="68px">
      <el-form-item label="搜索信息" prop="stockId">
        <el-input
          v-model="queryParams.stockId"
          placeholder="请输入关键字查询..."
          clearable
          size="small"
          style="width: 40vw"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务状态" prop="stockId" label-width="68px">
        <el-row>
          <el-col :span="12">
            <el-checkbox-group
              v-model="checkedStatus"
              @change="handleQuery">
              <el-checkbox label="0" :value="0"  :key="0">待处理</el-checkbox>
              <el-checkbox label="1" :value="1"  :key="1">已处理</el-checkbox>
              <el-checkbox label="-1" :value="-1"  :key="-1">未读</el-checkbox>
            </el-checkbox-group>
          </el-col>
          <el-col :span="10">
            <el-divider direction="vertical"/>
            <el-button type="primary"  icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="readAll">一键已读</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>

    <el-table
      :show-header="false"
      v-loading="loading"
      :data="logList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column  align="center" prop="purchaseStatus" width="100">
        <template slot-scope="scope">
          <matter-status :status="scope.row.result"/>
        </template>
      </el-table-column>
      <el-table-column label="任务详情" align="left" prop="title"  width="1000" />
      <el-table-column label="操作" align="right" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-check"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.result!='1' && scope.row.result!='2'"
            v-has-role="['libraryAdmin']"
          >一键办理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-has-role="['common,libraryAdmin']"
          >查看信息</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.result=='1' || scope.row.result=='2'"
            v-has-role="['libraryAdmin']"
          >删除记录</el-button>
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

    <el-drawer :title="title" :visible.sync="open" size="80%"  append-to-body destroy-on-close>
      <handel-matter @submitOperator="submitOperator" :only-read="onlyRead" :matter-id="matterId" :title="title"/>
    </el-drawer>
  </div>
</template>

<script>
  import { mine,getPurchase } from "@/api/userApplication/purchase";
  import ApproveStatusTag from "../base/ApproveStatusTag";
  import MatterStatus from "../base/matterStatus";
  import HandelMatter from "./handelMatter";
  import {updateQueue,readAll} from "@/api/userApplication/queue";
    export default {
        name: "myMatter",
      components: {HandelMatter, MatterStatus, ApproveStatusTag},
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
              id: null,
              title: null,
              approvePersonal: null,
              approveDept: null,
              startTime: null,
              endTime: null,
              termValidity: null,
              purchaseTemplate: null,
              purchaseStatus: null,
              mark: null,
              isDel: null,
              status: null,
            },
            checkedStatus: ['-1','0','1','2'],
            onlyRead:false,
            matterId:0,
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
        /** 查询信息列表 */
        getList() {
          this.loading = true;
          this.queryParams.purchaseStatus=this.checkedStatus.join(',');
          mine(this.queryParams).then(response => {
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

        /** 修改按钮操作 */
        handleUpdate(row) {
          this.reset();

          this.open = true;

          const read = {
            id:row.id,
            hasRead:'1'
          };
          updateQueue(read).then(_=>{});

          this.matterId = row.id;
          this.title = row.title;
        },

        handleDelete(row){

        },
        readAll(){
          let readIds = '';
          this.logList.forEach(log=>{
            if ('-1'==log.result){
              readIds+=log.id+',';
            }
          });
          if (readIds.length>0){
            readAll(readIds).then(_=>{
              this.$modal.msgSuccess("操作成功！");
              this.getList();
            });
          }
        },
        submitOperator(){
          this.getList();
          this.open= false;
        }
      }
    }
</script>

<style scoped>

</style>
