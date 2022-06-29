<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录id" prop="id" v-if="false" label-width="120px">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入记录id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购申请标题" prop="title" label-width="120px">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入采购申请标题文字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购申请人id" prop="approvePersonal" v-if="false" label-width="120px">
        <el-input
          v-model="queryParams.approvePersonal"
          placeholder="请输入采购申请人id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购开始时间" prop="startTime" label-width="120px">
        <el-date-picker clearable size="small"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择采购开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="采购结束时间" prop="endTime" label-width="120px">
        <el-date-picker clearable size="small"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择采购结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="有效期至" prop="termValidity" label-width="120px">
        <el-date-picker clearable size="small"
          v-model="queryParams.termValidity"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
          placeholder="选择有效期至">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录状态" prop="status" v-if="false" label-width="120px">
        <el-select v-model="queryParams.status" placeholder="请选择记录状态" clearable size="small">
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
          v-hasPermi="['userApplication:purchase:add']"
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
          v-hasPermi="['userApplication:purchase:edit']"
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
          v-hasPermi="['userApplication:purchase:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:purchase:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      id="tab"
      class="parent-table"
      v-loading="loading"
      :data="purchaseList"
      @selection-change="handleSelectionChange"
      @row-click="clickTable"
      :row-key="getRowKeys"
      :expand-row-keys="expands"
      @expand-change="expandSelect">
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column type="expand">
        <template slot-scope="slot">
          <el-table
            class="sub-table"
            :data="purchaseSub.purchaseSubList"
            v-loading="purchaseSub.loading">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="主键" align="center" prop="id" v-if="false"/>
            <el-table-column label="采购申请组" align="center" prop="groupId" v-if="false"/>
            <el-table-column label="采购图书" align="center" prop="entityId" v-if="false"/>
            <el-table-column label="采购图书名称" align="center" prop="entityName" />
            <el-table-column label="采购数" align="center" prop="purchaseNumber" >
             <template slot-scope="scope">
               <span>{{scope.row.purchaseNumber}}({{scope.row.utilName}})</span>
             </template>
            </el-table-column>
            <el-table-column label="计量单位" align="center" prop="util" v-if="false"/>
            <el-table-column label="已处理" align="center" prop="purchaseOverNumber" >
              <template slot-scope="scope">
                <span>{{scope.row.purchaseOverNumber}}({{scope.row.utilName}})</span>
              </template>
            </el-table-column>
            <el-table-column label="待处理" align="center" prop="purchaseOverNumber" >
              <template slot-scope="scope">
                <span>{{ scope.row.purchaseNumber - scope.row.purchaseOverNumber}}({{scope.row.utilName}})</span>
              </template>
            </el-table-column>
            <el-table-column label="计量单位" align="center" prop="utilName" />
            <el-table-column label="备注" align="center" prop="remark" v-if="false"/>
            <el-table-column label="是否删除" align="center" prop="isDel" v-if="false"/>
            <el-table-column label="状态" align="center" prop="status">
              <template slot-scope="slot">
                <show-status :status="slot.row.status"/>
              </template>
            </el-table-column>
            <el-table-column label="创建人" align="center" prop="createPersonal" v-if="false"/>
            <el-table-column label="更新人" align="center" prop="updatePersonal" v-if="false"/>
          </el-table>
          <pagination
            v-show="purchaseSub.page.total>0"
            :total="purchaseSub.page.total"
            :page.sync="purchaseSub.page.pageNum"
            :limit.sync="purchaseSub.page.pageSize"
            @pagination="getPurchaseSubList(slot.row.id)"
          />
        </template>
      </el-table-column>
      <el-table-column label="记录" align="center" prop="id" v-if="false"/>
      <el-table-column label="申请标题" align="center" prop="title" >
        <template slot-scope="scope">
          <el-tooltip v-if="undefined != scope.row.title" :content="scope.row.title" placement="top">
            <span>{{scope.row.title.substring(0,5)}}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="申请部门" align="center" prop="approveDept" v-if="false" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <el-tooltip v-if="undefined != scope.row.startTime" :content="scope.row.startTime" placement="top">
            <span v-if="undefined != scope.row.startTime">{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
          </el-tooltip>
          <span v-if="undefined == scope.row.startTime"><el-tag effect="plain" type="warning">未开始</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <el-tooltip v-if="undefined != scope.row.endTime" :content="scope.row.endTime" placement="top">
            <span v-if="undefined != scope.row.endTime">{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
          </el-tooltip>
          <span v-if="undefined == scope.row.endTime">-</span>
        </template>
      </el-table-column>
      <el-table-column label="有效期至" align="center" prop="termValidity" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.termValidity, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请结果" align="center" prop="purchaseStatus" >
        <template slot-scope="scope">
          <approve-status-tag :status="scope.row.purchaseStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="mark" v-if="false" />
      <el-table-column label="状态" align="center" prop="status" v-if="false">
        <template slot-scope="scope">
          <ShowStatus  :status="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createPersonal" v-if="false" />
      <el-table-column label="更新人" align="center" prop="updatePersonal"  v-if="false" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-share"
            @click="handleBrowseLink(scope.row)"
            v-if="scope.row.purchaseStatus!='0'"
            style="padding:5px;"
            v-hasPermi="['userApplication:purchase:edit']"
          >流程查看</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.purchaseStatus=='0' && userId == scope.row.createPersonal?'el-icon-edit':'el-icon-view'"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:purchase:edit']"
          >
            {{scope.row.purchaseStatus=='0' && userId == scope.row.createPersonal?'修改':'详情'}}
          </el-button>
          <el-button
            v-if="scope.row.purchaseStatus=='0' && userId == scope.row.createPersonal"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:purchase:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body v-loading="purchaseForm.loading">
      <el-form  ref="purchaseForm" label-width="100px"  class="demo-ruleForm" >
        <el-form-item label="申请标题" prop="purchase.title">
          <el-input
            v-if="isEdit"
            v-model="purchaseForm.purchase.title"
            placeholder="请输入申请标题......"/>
          <span v-if="!isEdit">{{purchaseForm.purchase.title}}</span>
        </el-form-item>
        <el-form-item  label="申请部门" required>
          <el-input
            v-if="isEdit"
            v-model="purchaseForm.purchase.approveDeptName"
            placeholder="单击选择部门..."
            @focus="openDeptDraw($event)" style="width: 50%;"/>
          <span v-if="!isEdit">{{purchaseForm.purchase.approveDeptName}}</span>
        </el-form-item>
        <el-form-item  label="审批流程" required>
          <el-input
            v-if="isEdit"
            v-model="purchaseForm.purchase.purchaseTemplateName"
            placeholder="单击选择审批流程..."
            @focus="openPurchaseTemplateDraw($event)" style="width: 50%;" />
          <span v-if="!isEdit">{{purchaseForm.purchase.purchaseTemplateName}}</span>
        </el-form-item>
        <el-form-item  label="截止日期" required>
          <el-date-picker
            v-if="isEdit"
            v-model="purchaseForm.purchase.termValidity"
            type="datetime"
            placeholder="选择日期时间"
            align="right"
            :picker-options="pickerOptions"
            style="margin-right: 20px;">
          </el-date-picker>
          <span v-if="!isEdit">{{purchaseForm.purchase.termValidity}}</span>
        </el-form-item>
        <el-form-item label="备注信息" prop="desc">
          <el-input type="textarea" :readonly="!isEdit" v-model="purchaseForm.purchase.mark"></el-input>
        </el-form-item>
        <el-form-item label="申请项目">
          <el-row :gutter="10" class="mb8" v-if="isEdit">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleSubPurchaseAdd"
                v-hasPermi="['userApplication:purchaseSub:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="appOrModify.multiple"
                @click="handleSubPurchaseDeletes"
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
          </el-row>
          <el-table
            :data="purchaseForm.subPurchase"
            height="280"
            @selection-change="handleAddOrEditSubPurchaseSelectionChange"
            v-loading="appOrModify.loading"
            border>
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序号" align="center" width="50" prop="index" />
            <el-table-column label="采购申请组" align="center" prop="groupId" v-if="false" />
            <el-table-column label="待采购图书" align="center" prop="entityName" >
              <template slot-scope="slot">
                <el-input v-if="isEdit" v-model="slot.row.entityName" placeholder="单击前往选择图书..." @focus="openDraw(slot.row,$event)"/>
                <span v-if="!isEdit">{{slot.row.entityName}}</span>
              </template>
            </el-table-column>
            <el-table-column label="采购数" align="center" prop="purchaseNumber" width="250">
              <template slot-scope="slot">
                <el-input-number v-if="isEdit" v-model="slot.row.purchaseNumber" controls-position="right" :min="1" :max="10000"/>
                <span v-if="!isEdit">{{slot.row.purchaseNumber}}</span>
              </template>
            </el-table-column>
            <el-table-column label="计量单位" align="center" prop="utilName" width="200">
              <template slot-scope="slot">
                <el-select v-if="isEdit" v-model="slot.row.utilEntity" value-key="id" clearable placeholder="选择计量单位">
                  <el-option
                    v-for="option in utilList"
                    :key="option.id"
                    :label="option.name + '('+ option.code +')' "
                    :value="option"
                  />
                </el-select>
                <span v-if="!isEdit">{{slot.row.utilEntity.name}}</span>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="remark" >
              <template slot-scope="slot">
                <el-input v-if="isEdit" type="textarea" v-model="slot.row.remark" rows="1"></el-input>
                <span v-if="!isEdit">{{slot.row.remark}}</span>
              </template>
            </el-table-column>
<!--            <el-table-column label="状态" align="center" prop="status" v-if="false">-->
<!--              <template slot-scope="scope">-->
<!--                <dict-tag :options="dict.type.base_status" :value="scope.row.status"/>-->
<!--              </template>-->
<!--            </el-table-column>-->
            <el-table-column v-if="isEdit" label="操作" align="center" width="80" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleSubPurchaseDelete(scope.row)"
                  v-hasPermi="['userApplication:purchaseSub:remove']"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-tooltip v-if="isEdit" content="确认保存但不提交，下次可修改" placement="top">
          <el-button v-if="isEdit" type="primary" @click="submitSaveForm">保 存</el-button>
        </el-tooltip>
        <el-tooltip v-if="isEdit" content="提交申请后等待审批，无法修改" placement="top">
          <el-button v-if="isEdit" type="primary" @click="submitApproveForm">保存并提交</el-button>
        </el-tooltip>
        <el-tooltip :content="isEdit?'取消后信息不会被保存':'返回'" placement="top">
          <el-button @click="cancel">取 消</el-button>
        </el-tooltip>
      </div>
    </el-dialog>

<!--图书选择-->
    <el-dialog
      :title="appOrModify.bookType.title"
      destroy-on-close
      :visible.sync="appOrModify.bookType.drawer"
      width="75%"
      append-to-body>
      <ChioseBook v-on:selectedBook="handleBookSelectionChange"/>
    </el-dialog>
<!--部门选择-->
    <el-drawer
          size="20%"
          style="z-index: 99;"
          :title="appOrModify.dept.title"
          :visible.sync="appOrModify.dept.open"
          direction="ltr"
          :before-close="drawerClose">
      <el-row  type="flex">
        <el-col style="width: 100%">
          <!--部门数据-->
          <select-tree v-on:selectNode="selectDept"/>
        </el-col>
      </el-row>
    </el-drawer>
<!--审批流程模板选择-->
    <el-drawer
      size="40%"
      style="z-index: 99;"
      :title="appOrModify.template.title"
      :visible.sync="appOrModify.template.open"
      direction="rtl"
      :before-close="drawerClose">
        <ChiosePurchaseTemplate v-on:sure="sureThisTemplate"/>
    </el-drawer>

    <el-drawer
        :visible.sync="purchaseLink.open"
        direction="rtl"
        size="20%"
      destroy-on-close>
      <ProcessPreview :title="purchaseLink.title" :purchaseLink="purchaseLink.link.approveId"/>
<!--        <flow-panel-->
<!--          :purchase="purchaseLink" :isEdit="false"/>-->
      </el-drawer>
  </div>
</template>

<script>

  import FlowPanel from '@/components/ef/panel'
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase,approvePurchase, test } from "@/api/userApplication/purchase";
import { listUtil, allUtil } from "@/api/userApplication/util";
import { listBook } from "@/api/userApplication/book";
import { listTemplate,getTemplate } from "@/api/userApplication/template";
import Template from "../template/index";
import { treeselect, getDept } from "@/api/system/dept";
import purchaseSub from"../purchaseSub/index"
import PurchaseSubTable from "../purchaseSub/purchaseSubTable";
import { listPurchaseSub, getPurchaseSub, delPurchaseSub, addPurchaseSub, updatePurchaseSub } from "@/api/userApplication/purchaseSub";
import { getUserProfile } from "@/api/system/user";
import ShowStatus from "../base/ShowStatus";
import ApproveStatusTag from "../base/ApproveStatusTag";
import { bookclasstreeselect } from "@/api/main/bookClass";
import SelectTree from "../base/libraryElement";
import HasBook from "../base/HasBook";
import PurchaseLinkBox from "../base/PurchaseLinkBox";
  import ChiosePurchaseTemplate from "./ChiosePurchaseTemplate";
  import ChioseBook from "./ChioseBook";
  import ProcessPreview from "./ProcessPreview";
export default {
  name: "purchase",
  components: {
    ProcessPreview,
    ChioseBook,
    ChiosePurchaseTemplate,
    PurchaseLinkBox,
    ApproveStatusTag,
    SelectTree,
    ShowStatus,
    PurchaseSubTable,
    Template,
    purchaseSub,
    FlowPanel},
  dicts: ['base_status','library_secrecy_class'],
  data() {
    return {
      isEdit:false,
      utilList:[{'label':'','value':''}],
      userId:-1,
      groupId:-1,
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
      purchaseList: [],
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

      //主表唯一索引
      expands: [],
      isOpen:{id:undefined,boo:false},
      getRowKeys(row) {
        return row.id;
      },
      purchaseSub:{//子表数据
        loading:false,
        page:{
          total:0,
          pageNum:10,
          pageSize:1
        },
        purchaseSubList:[],
        query:{},
      },
      // 表单参数
      appOrModify:{
        loading:false,
        subIds:[],
        multiple:true,
        single:true,

        //图书选择
        bookType:{
          drawer:false,
          operatorIndex:undefined,
          title:'请选择待采购的图书（单项选择）',
          queryBookTypeId:undefined,
          //图书类别名称
          bookClassName:'',
          // defaultProps: {
          //   children: "children",
          //   label: "label"
          // },
          total:0,
          queryParams:{
            pageNum:1,
            pageSize:10,
          },
          // 图书类别树选项
          // bookClassOptions: undefined,
        },

        //部门选择
        dept:{
          title:'部门选择',
          open:false,
        },

        //审批流程模板选择
        template:{
          open:false,
          title:'选择采购图书审批流程模板',
          templateName:'',
          tempList:[]
        }
      },
      purchaseForm: {
        loading:false,
        purchase:{
          id:null,
          title:null,
          approvePersonal:null,
          approveDept:null,
          approveDeptName:null,
          startTime:null,
          endTime:null,
          termValidity:null,
          purchaseTemplate:null,
          purchaseTemplateName:null,
          purchaseStatus:0,
          mark:null,
          isDel:0,
          status:1,
          createPersonal:null,
          createTime:null,
          updatePersonal:null,
          updateTime:null
        },
        subPurchase:[],
      },
      pickerOptions: {
        shortcuts: [{
          text: '明天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '后天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 2 * 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 7 * 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }]
      },
      purchaseLink:{
        open:false,
        title:'',
        purchase:{},
        template:{},
        link:{
          approveId:null,
          node:[],
        },
        showPurchaseId:undefined,//待展示的流程id
      },
      // 表单校验
      rules: {
        // purchase: {
        //   title: [{required: true, message: "采购申请标题文字不能为空", trigger: "blur"}
        //   ],
        //   approveDeptName: [{required: true, message: "采购申请部门不能为空", trigger: "blur"}
        //   ],
        //   termValidity: [{required: true, message: "有效期至某年某月某日某时某分某秒结束,不能为空", trigger: "blur"}
        //   ],
        //   approveTemplateName: [{required: true, message: "采购流程模板不能为空", trigger: "blur"}
        //   ],
        // },
        // subPurchase:{
        //   entityName:[{required: true, message: "请选择采购图书", trigger: "blur"}],
        //   purchaseNumber:[
        //     {required: true, message: "请输入采购数", trigger: "blur"},
        //     {min:1,max:1000,message: "每个批次的采购数只能介于1~10000之间",trigger:"blur"},
        //     ],
        //   util:[{required: true, message: "请选择计量单位", trigger: "blur"}]
        // }
      }
    };
  },
  created() {
    getUserProfile().then(res=>{
      this.userId =  res.data.userId ;
    });
    this.getUtilList();
    // const userId = this.$route.params && this.$route.params.userId;
    // this.$modal.msgSuccess( userId );
    this.getList();
  },
  methods: {
    /** 查询采购申请信息列表 */
    getList() {
      this.loading = true;
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(()=>{
        this.loading = false;
        this.$modal.msgError("连接超时");
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
        approvePersonal: null,
        approveDept: null,
        startTime: null,
        endTime: null,
        termValidity: null,
        purchaseTemplate: null,
        purchaseTemplateName:null,
        purchaseStatus: null,
        mark: null,
        isDel: null,
        status: 0,
        createPersonal: null,
        createTime: null,
        updatePersonal: null,
        updateTime: null
      };
      this.resetPurchaseForm();
      this.resetForm("purchaseForm");
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length!==1;
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isEdit = true;
      this.open = true;
      this.title = "添加采购申请信息";
    },
    /** 查看处理流程 */
    handleBrowseLink(row){
      // this.purchaseLink.template={};
      // this.purchaseLink.showPurchaseId = row.purchaseTemplate;
      // getTemplate(this.purchaseLink.showPurchaseId).then(res=>{
      //   // this.$modal.msgSuccess(this.purchaseLink.showPurchaseId);
      //   this.purchaseLink.template= res.data;
      //
      //   this.purchaseLink.title = '关于《'+row.title+'》的申请处理流程状态如下所示：';
        this.purchaseLink.open = true;
        this.purchaseLink.title = row.title;
      // });
      //@TODO 查询流程结点
      this.purchaseLink.link.approveId = row.id;
      this.purchaseLink.link.node = [];

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.purchaseForm.loading = true;

      this.isEdit = row.purchaseStatus=='0' && this.userId == row.createPersonal;

      this.reset();
      const id = row.id || this.ids;
      getPurchase(id).then(response => {
        this.purchaseForm.purchase = response.data;
        this.title = "修改采购申请信息";
      }).then(()=>{
        // @TODO 获取部门信息
        this.getPurchaseDeptInfo();//
      }).then(()=>{
        this.getApproveLinkTemplateInfo();
        // @TODO 获取审批流程模板信息
      }).then(()=>{
        this.getSubPurchaseInfo();
        this.open = true;
        // @TODO 获取审批子表信息
        this.purchaseForm.loading = false;
      }).catch(()=>{
        this.open = false;
        this.purchaseForm.loading = false;
          this.$modal.msgSuccess("数据获取异常");
      });
    },

    /*获取部门信息*/
    getPurchaseDeptInfo(){
      if (undefined != this.purchaseForm.purchase && undefined != this.purchaseForm.purchase.approveDept){
        getDept(this.purchaseForm.purchase.approveDept).then(res=>{
          let deptData = res.data;
          this.purchaseForm.purchase.approveDept = deptData.deptId;
          this.purchaseForm.purchase.approveDeptName = deptData.deptName;
          return true;
        }).catch(()=>{
          return false;
        });
      }
    },
    /*获取审批流程模板信息*/
    getApproveLinkTemplateInfo(){
      if (undefined != this.purchaseForm.purchase){
        getTemplate(this.purchaseForm.purchase.purchaseTemplate).then(res=>{
          let templateData = res.data;
          if (undefined == templateData || templateData == null){
            return false;
          }
          this.purchaseForm.purchase.purchaseTemplate = templateData.id;
          this.purchaseForm.purchase.purchaseTemplateName = templateData.title;
          return true;
        }).catch(()=>{
          return false;
        });
      }
    },
    /*获取审批子表信息*/
    getSubPurchaseInfo(){
      if (undefined != this.purchaseForm.purchase && undefined != this.purchaseForm.purchase.id){
        let subQuery = {
          'groupId':this.purchaseForm.purchase.id,
          'status':'1',
          'isDel':'0'
        };
        listPurchaseSub(subQuery).then(response => {
          if(undefined == response.rows){
            return;
          }

          // @TODO this.purchaseForm.purchase.subPurchase = [];
          this.purchaseForm.subPurchase = [];
          for(let i=0;i<response.rows.length;i++){
            let subData = {
              id:response.rows[i].id,
              groupId:response.rows[i].groupId,
              entityId:response.rows[i].entityId,
              entityName:response.rows[i].entityName,
              purchaseNumber:response.rows[i].purchaseNumber,
              purchaseOverNumber:response.rows[i].purchaseOverNumber,
              util: response.rows[i].util,
              utilName:response.rows[i].utilName,
              utilEntity:{id:response.rows[i].util,name:response.rows[i].utilName},
              remark:response.rows[i].remark,
              isDel:response.rows[i].isDel,
              status:response.rows[i].status,
              createPersonal:response.rows[i].createPersonal,
              createTime:response.rows[i].createTime,
              updatePersonal:response.rows[i].updatePersonal,
              updateTime:response.rows[i].updateTime,
              index: i+1
            };
            this.purchaseForm.subPurchase.push(subData);
          }

          return true;
        }).catch(()=>{
          return false;
        });
      }
    },

    getUtilList(){
      let utilQuery = {
        'status':'1',
        'isDel':'0',
      };
      allUtil(utilQuery).then(res=>{
        if (undefined == res.data){
          this.utilList = [];
        }else{
          this.utilList = [];
          for (let i=0;i<res.data.length;i++){
            this.utilList.push(res.data[i]);
          }
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchase(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },


    hasSubList(){
      return undefined == this.purchaseForm.subPurchase
        || null == this.purchaseForm.subPurchase
        || this.purchaseForm.subPurchase.length<=0;
    },
    /** 提交并保存按钮 */
    submitSaveForm() {
      if(!this.ruleSubmit()) {
        return;
      }
      if(this.hasSubList()){
        this.$modal.confirm('未填写采购申请项目列表，确认继续操作？').then(()=> {
          this.save();
        }).catch(()=>{return false;})
      }else {
        this.save();
      }
    },
    save(){
      if (undefined != this.purchaseForm.purchase.id && this.purchaseForm.purchase.id != null) {
        // this.refulshUtil();
        updatePurchase(this.purchaseForm).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.getList();
        }).catch(err=>{
          this.$modal.msgSuccess("系统繁忙，操作失败");
          this.open = false;
        });
      } else {
        // this.refulshUtil();
        addPurchase(this.purchaseForm).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        }).catch(err=>{
          this.$modal.msgSuccess("系统繁忙，操作失败");
          this.open = false;
        });
      }
    },
    refulshUtil(){
      // util保存了util实体信息，提交的信息只要util的计量单位ID，以及将utilName；
      if (undefined != this.purchaseForm.subPurchase && null != this.purchaseForm.subPurchase){
        for (let i=0;i<this.purchaseForm.subPurchase.length;i++){
          if(undefined == this.purchaseForm.subPurchase[i].utilEntity || null == this.purchaseForm.subPurchase[i].utilEntity){
            continue;
          }
          this.purchaseForm.subPurchase[i].utilName = this.purchaseForm.subPurchase[i].utilEntity.name;
          this.purchaseForm.subPurchase[i].util = this.purchaseForm.subPurchase[i].utilEntity.id;
        }
      }
    },

    //对提交信息进行校验
    ruleSubmit(){
      let purchaseEntity = this.purchaseForm.purchase;
      if (undefined == purchaseEntity.title || null == purchaseEntity.title || purchaseEntity.title==''){
        this.$modal.msgError('采购申请标题不能为空');
        return false;
      }
      if(undefined == purchaseEntity.approveDept || null == purchaseEntity.approveDept){
        this.$modal.msgError('采购申请部门不能为空');
        return false;
      }
      if(undefined == purchaseEntity.purchaseTemplate || null == purchaseEntity.purchaseTemplate){
        this.$modal.msgError('审批处理流程不能为空');
        return false;
      }
      if(undefined == purchaseEntity.termValidity || null == purchaseEntity.termValidity){
        this.$modal.msgError('申请截止日期不能为空');
        return false;
      }
      let purchaseSubList = this.purchaseForm.subPurchase;
      if(this.hasSubList()){
        return false;
      }

      for (let i=0;i<purchaseSubList.length;i++){
        if(undefined == purchaseSubList[i] || null == purchaseSubList[i]){
          this.$modal.msgError('第'+(i+1)+'个申请项填写信息不完整');
          return false;
        }
        if (undefined == purchaseSubList[i].entityId || null == purchaseSubList[i].entityId ){
          this.$modal.msgError('填写信息不完整，第'+(i+1)+'个申请项缺少申请物品');
          return false;
        }
        if (undefined == purchaseSubList[i].purchaseNumber || null == purchaseSubList[i].purchaseNumber ){
          this.$modal.msgError('填写信息不完整，第'+(i+1)+'个申请项缺少申请数量');
          return false;
        }
        if (undefined == purchaseSubList[i].utilEntity || null == purchaseSubList[i].utilEntity ){
          this.$modal.msgError('填写信息不完整，第'+(i+1)+'个申请项缺少计量单位');
          return false;
        }
        this.purchaseForm.subPurchase[i].utilName = purchaseSubList[i].utilEntity.name;
        this.purchaseForm.subPurchase[i].util = purchaseSubList[i].utilEntity.id;
      }
      return true;
    },
    /** 提交并申请按钮 */
    submitApproveForm() {
      if(!this.ruleSubmit()) {
        return;
      }
      if(this.hasSubList()){
        this.$modal.confirm('未填写采购申请项目列表，确认继续操作？').then(()=> {
          this.approve();
        }).catch(()=>{return false;})
      }else{
        this.approve();
      }
    },
    approve(){
      if (this.purchaseForm.purchase.id != null) {
        // this.refulshUtil();
        approvePurchase(this.purchaseForm).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.getList();
        });
      }
      // else {
      //   // this.refulshUtil();
      //   approvePurchase(this.purchaseForm).then(response => {
      //     this.$modal.msgSuccess("新增成功");
      //     this.open = false;
      //     this.getList();
      //   });
      // }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除采购申请信息编号为"' + ids + '"的数据项？').then(function() {
        return delPurchase(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    },


// 点击行展开事件
    clickTable(row, index, e) {
      this.$refs.refTable.toggleRowExpansion(row);
    },
    expandSelect(row, expandedRows){
      this.purchaseSub.purchaseSubList = [];//打开或关闭子表时先清空子表数据

      /**打开子表只展示一个子表，其他子表闭合**/
      let that = this;
      if (expandedRows.length) {
        that.expands = [];
        if (row) {
          that.expands.push(row.id); // 每次push进去的是每行的ID
        }
      } else {
        that.expands = []; // 默认不展开
      }

      this.groupId = row.id;
      this.getPurchaseSubList(row.id);
    },


    /**子表数据获取**/
    getPurchaseSubList(group_id) {
      // this.$modal.msgSuccess(group_id);
      this.purchaseSub.loading = true;
      this.purchaseSub.query.groupId = group_id;
      listPurchaseSub(this.purchaseSub.query).then(response => {
        this.purchaseSub.purchaseSubList = response.rows;
        this.purchaseSub.page.total = response.total;
        this.purchaseSub.loading = false;
      });
    },
    /**新增时增加一条子表记录**/
    handleSubPurchaseAdd(){
      let index = this.purchaseForm.subPurchase.length;
      if (index<=0){
        index = 1;
      }else{
        index = this.purchaseForm.subPurchase[index-1].index+1;
      }
      let row = {
        'index':index,
        'entityName':null,
        'entityId':null,
        'purchaseNumber':1,
        'util':null,
        'utilEntity':null,
        'utilName':'',
        'mark':''
      };
      this.purchaseForm.subPurchase.push(row);
    },
    // 新增或修改数据时子表多选框选中数据
    handleAddOrEditSubPurchaseSelectionChange(selection) {
      this.appOrModify.subIds = selection.map(item => item.index);
      this.appOrModify.single = selection.length!==1;
      this.appOrModify.multiple = !selection.length;
    },
    /**删除一条记录**/
    handleSubPurchaseDelete(row){
      this.$modal.confirm('是否确认移除第' + row.index + '条数据项？').then(function() {
        return true;
      }).then(() => {
        // this.getStockList();
        this.removeSubItem([row.index]);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});

    },
    handleSubPurchaseDeletes(){
      let removeLen = this.appOrModify.subIds.length;
      this.$modal.confirm('是否确认移除这' + removeLen + '条数据项？').then(function() {
        return true;
      }).then(() => {
        this.removeSubItem(this.appOrModify.subIds);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    removeSubItem(item_index_arr){
      if (! typeof( item_index_arr) instanceof Array){
        return false;
      }
      for(let index=0;index<item_index_arr.length;index++) {
        for (let i = 0; i < this.purchaseForm.subPurchase.length; i++) {
          if (this.purchaseForm.subPurchase[i].index === item_index_arr[index]) {
            this.purchaseForm.subPurchase.splice(i, 1);
            break;
          }
        }
      }
      return true;
    },


    //重置 提交或修改 的表单
    resetPurchaseForm(){
      this.purchaseForm = {
        purchase:{
          id:null,
          title:null,
          approvePersonal:null,
          approveDept:null,
          approveDeptName:null,
          startTime:null,
          endTime:null,
          termValidity:null,
          purchaseTemplate:null,
          purchaseTemplateName:null,
          purchaseStatus:0,
          mark:null,
          isDel:0,
          status:1,
          createPersonal:null,
          createTime:null,
          updatePersonal:null,
          updateTime:null
        },
        subPurchase:[]
      };
    },

    /*选择图书*/
    openDraw(row,event) {
      event.target.blur();
      // this.getTreeselect();//获取图书类别
      this.appOrModify.bookType.operatorIndex = row.index;
      this.appOrModify.bookType.drawer = true;
    },

    /**关闭抽屉**/
    drawerClose(done){
      done();
    },

    /**/
    utilChange(value){

    },

    // 图书类别节点单击事件
    selectBookType(selectData) {
      this.appOrModify.bookType.bookClassName = selectData.curData.label;
      this.appOrModify.bookType.queryBookTypeId = selectData.curData.id;
      // this.form.claz = data.id;
      this.appOrModify.bookType.queryParams.type=this.appOrModify.bookType.queryBookTypeId;
      this.getBookList();
    },

    /** 查询图书实体信息列表 */
    getBookList() {
      this.appOrModify.bookType.loading = true;
      listBook(this.appOrModify.bookType.queryParams).then(response => {
        this.appOrModify.bookType.bookList = response.rows;
        this.appOrModify.bookType.total = response.total;
        this.appOrModify.bookType.loading = false;
      });
    },
    // 选中图书数据
    handleBookSelectionChange(selection) {
      for (let i = 0; i < this.purchaseForm.subPurchase.length; i++) {
        if (this.purchaseForm.subPurchase[i].index === this.appOrModify.bookType.operatorIndex) {
          let bookName = selection.name;
          this.$modal.confirm('确认选择'+bookName+'吗？').then(function() {
            return true;
          }).then(() => {
            this.appOrModify.loading = true;
            this.purchaseForm.subPurchase[i].entityId = selection.id;
            this.purchaseForm.subPurchase[i].entityName = selection.name;
            this.appOrModify.bookType.drawer = false;
            this.appOrModify.loading = false;
          }).catch(() => {});
          return;
        }
      }
    },

    // 部门树节点单击事件
    selectDept(selectData) {
      this.purchaseForm.purchase.approveDept = selectData.curData.id;
      this.purchaseForm.purchase.approveDeptName = selectData.curData.label;
      this.appOrModify.dept.open = false;
    },
    openDeptDraw(event){
      event.target.blur();
      this.appOrModify.dept.open = true;
    },

    //**流程审批模板选择**//
    openPurchaseTemplateDraw(event){
      event.target.blur();
      // this.getTemplateList();
      this.appOrModify.template.open = true;
    },


    //获取模板信息
    // getTemplateList(){
    //   listTemplate().then(res=>{
    //     this.appOrModify.template.templateList=res.data;
    //   });
    // },

    //确认选择此模板
    sureThisTemplate(row){
      this.$modal.confirm('是否确认选择《'+row.title+'》作为审批流程？').then(function() {
        return true;
      }).then(() => {
        this.purchaseForm.purchase.purchaseTemplateName = row.title;
        this.purchaseForm.purchase.purchaseTemplate = row.id;
        this.appOrModify.template.open = false;
      }).catch(() => {});
    }
  },

};
</script>
