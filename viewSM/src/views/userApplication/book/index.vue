<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键字" prop="keyWords" style="width: 90%;">
        <el-col :span="21">
            <el-input
            v-model="queryParams.keyWords"
            placeholder="请输入关键字"
            clearable
            size="small"
            @keyup.enter.native="handleQuery" style="width: 800px"
           />
        </el-col>
      </el-form-item>
      <el-form-item label="图书名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入图书名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入图书编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保存公开级别" prop="leavel" label-width="120px">
        <el-select v-model="queryParams.leavel" placeholder="请选择保存\公开级别" clearable size="small">
          <el-option
            v-for="dict in dict.type.library_secrecy_class"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="ISBN" prop="isbn">
        <el-input
          v-model="queryParams.isbn"
          placeholder="请输入ISBN"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系列丛书" prop="series">
        <el-input
          v-model="queryParams.series"
          placeholder="请输入系列丛书名称"
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
          v-hasPermi="['userApplication:book:add']"
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
          v-hasPermi="['userApplication:book:edit']"
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
          v-hasPermi="['userApplication:book:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:book:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id"  v-if="columns[0].visible"/>
      <el-table-column label="图书编码" align="center" prop="code" v-if="columns[1].visible" />
      <el-table-column label="图书名称" align="center" prop="name"  v-if="columns[2].visible"/>
      <el-table-column label="类型" align="center" prop="type"  v-if="columns[3].visible">
        <template slot-scope="scope">
          <el-tag type="warning">{{scope.row.type}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="保存公开级别" align="center" prop="leavel" v-if="columns[4].visible" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.library_secrecy_class" :value="scope.row.leavel"/>
        </template>
      </el-table-column>
      <el-table-column label="图书类别" align="center" prop="claz"  v-if="columns[5].visible"/>
      <el-table-column label="作者" align="center" prop="author" v-if="columns[6].visible" />
      <el-table-column label="出版社" align="center" prop="mechanism"  v-if="columns[7].visible"/>
      <el-table-column label="出版日期" align="center" prop="publicationDate" v-if="columns[8].visible" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publicationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图书版本号" align="center" prop="version"  v-if="columns[9].visible"/>
      <el-table-column label="发行量" align="center" prop="circulation"  v-if="columns[10].visible"/>
      <el-table-column label="ISBN" align="center" prop="isbn"  v-if="columns[11].visible"/>
      <el-table-column label="系列丛书" align="center" prop="series" v-if="columns[12].visible" />
      <el-table-column label="关键字" align="center" prop="keyWords"  v-if="columns[13].visible"/>
      <el-table-column label="单价" align="center" prop="price" v-if="columns[14].visible" />
      <el-table-column label="说明" align="center" prop="mark" v-if="columns[15].visible" />
      <el-table-column label="状态" align="center" prop="status" v-if="columns[16].visible" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value= "1"
            inactive-value= "0"
            inactive-color="#ff9292"
            @change="handleBookStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="button"
            icon="el-icon-read"
            @click="handleRead(scope.row)"
            v-hasPermi="['userApplication:book:read']"
            style="padding: 3px 5px"
          >查阅</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:book:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-upload"
            @click="resources(scope.row)"
            v-hasPermi="['userApplication:book:edit']"
          >资源管理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:book:remove']"
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

    <!-- 添加或修改图书实体信息对话框 -->
    <el-drawer :title="title" :visible.sync="open" direction="rtl" destroy-on-close size="80%"  append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="0">
        <el-form-item label-width="80px" prop="name">
          <el-descriptions :column="3">
            <el-descriptions-item label="图书名称">
              <el-input v-model="form.name" placeholder="请输入图书名称"  style="width: 300px;"/>
            </el-descriptions-item>
            <el-descriptions-item label="图书编码">
              <el-input v-model="form.code" placeholder="请输入图书编码"  style="width: 300px;"/>
            </el-descriptions-item>
            <el-descriptions-item label="ISBN">
              <el-input v-model="form.isbn" placeholder="请输入ISBN"  style="width: 300px;"/>
            </el-descriptions-item>
            <el-descriptions-item label="公开级别">
              <el-select v-model="form.leavel" placeholder="请选择保存\公开级别" style="width: 300px;">
                <el-option
                  v-for="dict in dict.type.library_secrecy_class"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item label="版本编号">
              <el-input v-model="form.version" placeholder="请输入图书版本号" style="width: 300px;" />
            </el-descriptions-item>
            <el-descriptions-item label="发行数量">
              <el-input v-model="form.circulation" placeholder="请输入发行量" style="width: 300px;" />
            </el-descriptions-item>
            <el-descriptions-item label="系列丛书">
              <el-input v-model="form.series" placeholder="请输入系列丛书名称" style="width: 300px;" />
            </el-descriptions-item>
            <el-descriptions-item label="图书单价">
              <el-input v-model="form.price" placeholder="请输入单价"  style="width: 300px;"/>
            </el-descriptions-item>
            <el-descriptions-item label="出版日期">
              <el-date-picker clearable size="small"
                              v-model="form.publicationDate"
                              type="date"
                              style="width: 300px;"
                              value-format="yyyy-MM-dd"
                              placeholder="选择出版日期">
              </el-date-picker>
            </el-descriptions-item>
            <el-descriptions-item label="选择作者">
              <el-select v-model="auth.name"
                         :multiple="false"
                         placeholder="选择作者"
                         @change="authChange"
                         style="width: 300px;">
                <el-option
                  v-for="item in authList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  <span style="float: left">{{ item.name }}({{item.country}})</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.englishName }}</span>
                </el-option>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item :span="4" :xs="24" label="选出版社">
              <el-select
                :multiple="false"
                v-model="press.name"
                placeholder="选出版社"
                @change="meachaseChange"
                style="width: 300px;">
                <el-option
                  v-for="item in pressList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                  <span style="float: left"><i class="el-icon-office-building"/>{{ item.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px"><i class="el-icon-message"/>{{ item.email }}</span>
                </el-option>
              </el-select>
            </el-descriptions-item>
          </el-descriptions>
        </el-form-item>
        <el-divider/>
        <el-form-item label-width="80px" prop="claz" >
          <el-row :gutter="20" column="3">
            <!--图书类别-->
            <el-col :span="8" label="图书类别">
              <label>选择图书类别</label>
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
                  :props="defaultProps"
                  :expand-on-click-node="false"
                  :filter-node-method="filterNode"
                  ref="tree"
                  @node-click="handleNodeClick"
                  style="width: 400px;max-height: 450px;overflow-y: auto;"
                />
              </div>
            </el-col>

            <el-col :span="12">
              <el-row >
                <label>图书标签</label>
                  <tag-input :max-tags="5" :size-of-one="10" :value.sync="form.keyWords" v-on:addTags="addTags"/>
              </el-row>
              <el-row>
                <el-col>
                  <label style="margin-top: 20px;">详细信息</label>
                    <el-input
                      type="textarea"
                      :rows="5"
                      :max-size="1500"
                      v-model="form.mark"
                      placeholder="请输入说明" />
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <el-button-group style="float: right;margin-right: 20px;">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-button-group>
    </el-drawer>

    <el-dialog :title="detail.title" :visible.sync="detail.open" destroy-on-close width="80%" append-to-body>
      <span slot="label"><i class="el-icon-menu"></i> 库位存书信息</span>
      <el-row :gutter="40" v-if="undefined != detail.entity.book">
        <el-col :span="6" :push="1">
          <Picture :srcList="detail.entity.bookFileSrc" v-if="undefined != detail.entity.bookFileSrc"/>
          <el-descriptions column="1" style="margin-bottom: 3px; text-align: center;" :colon="false">
            <el-descriptions-item v-if="undefined != detail.entity.book && undefined != detail.entity.book.name">
              {{detail.entity.book.name}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-col :push="1" :span="16">
          <el-divider content-position="left">
            <i class="el-icon-s-management">图书信息</i>
          </el-divider>
          <el-descriptions column="3" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="图书名称" v-if="undefined != detail.entity.book.name">
              {{detail.entity.book.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="图书编码" v-if="undefined != detail.entity.book.code">
              {{detail.entity.book.code}}
            </el-descriptions-item>
            <el-descriptions-item  label="图书类型">
              <el-tag size="small" effect="dark" type="success" v-if="undefined != detail.entity.bookType.name " style="margin-right: 5px;">
                {{detail.entity.bookType.name}}
              </el-tag>
              <el-tag size="small" effect="dark" type="warning" v-if="undefined != detail.entity.bookType.code ">
                {{detail.entity.bookType.code}}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item  label="ISBN" v-if="undefined != detail.entity.book.isbn">
              {{detail.entity.book.isbn}}
            </el-descriptions-item>
            <el-descriptions-item  label="发行量" v-if="undefined != detail.entity.book.circulation">
              {{detail.entity.book.circulation}}
            </el-descriptions-item>
            <el-descriptions-item  label="版本号" v-if="undefined != detail.entity.book.version">
              {{detail.entity.book.version}}
            </el-descriptions-item>
            <el-descriptions-item  label="丛书系列" v-if="undefined != detail.entity.book.series">
              {{detail.entity.book.series}}
            </el-descriptions-item>
            <el-descriptions-item  label="单价" v-if="undefined != detail.entity.book.price">
              {{detail.entity.book.price}}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions column="1">
            <el-descriptions-item  label="关键字" v-if="undefined != detail.entity.book.keyWords">
              <el-tag
                v-for="lab in detail.entity.book.keyWords.split(',')"
                :key="lab"
                type="success"
                effect="dark"
                v-if="lab != undefined || '' !== lab"
                style="margin-left: 3px;">{{lab}}</el-tag>
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions column="1">
            <el-descriptions-item  label="其他信息" v-if="undefined != detail.entity.book.other">
              {{detail.entity.book.other}}
            </el-descriptions-item>
          </el-descriptions>

          <data-group :data="detail.entity.bookFile" :group-style="''"/>
        </el-col>
      </el-row>
      <el-row :gutter="40">
        <el-col v-if="undefined != detail.entity.auth" :push="1" :span="15">
          <el-divider content-position="left">
            <i class="el-icon-s-custom">作者信息</i>
          </el-divider>
          <el-descriptions column="3"  contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="原名" v-if="undefined != detail.entity.auth.name">
              <i class="el-icon-user"></i>
              {{detail.entity.auth.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="性别" v-if="undefined != detail.entity.auth.sex">
              <SexLabel :sex="detail.entity.auth.sex"/>
            </el-descriptions-item>
            <el-descriptions-item  label="国籍" v-if="undefined != detail.entity.auth.country">
              <i class="el-icon-s-check"></i>
              {{detail.entity.auth.country}}
            </el-descriptions-item>
            <el-descriptions-item  label="别名">
              <i class="el-icon-help"></i>
              {{detail.entity.auth.nickName}}
            </el-descriptions-item>
            <el-descriptions-item  label="英文名" v-if="undefined != detail.entity.auth.englishName">
              <i class="el-icon-user"></i>
              {{detail.entity.auth.englishName}}
            </el-descriptions-item>
          </el-descriptions>

          <el-descriptions  column="1" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="社会身份" v-if="undefined != detail.entity.auth.identity">
              <el-tag
                v-for="lab in detail.entity.auth.identity.split(',')"
                :key="lab"
                type="success"
                effect="dark"
                v-if="lab != undefined || '' !== lab"
                style="margin-left: 3px;">{{lab}}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
          <el-descriptions  column="1" contentStyle="font-size: 12px;font-wight:bold;">
            <el-descriptions-item  label="简介" v-if="undefined != detail.entity.auth.synopsis">
              {{detail.entity.auth.synopsis}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
        <el-col v-if="undefined != detail.entity.press" :span="8" :push="1">
          <el-divider content-position="left">
            <i class="el-icon-school">图书出版社信息</i>
          </el-divider>
          <el-descriptions column="1" contentStyle="font-size: 16px;font-wight:bold;">
            <el-descriptions-item  label="出版社名称" v-if="undefined != detail.entity.press.name">
              {{detail.entity.press.name}}
            </el-descriptions-item>
            <el-descriptions-item  label="地址" v-if="undefined != detail.entity.press.addr">
              {{detail.entity.press.addr}}
            </el-descriptions-item>
            <el-descriptions-item  label="邮箱" v-if="undefined != detail.entity.press.email">
              {{detail.entity.press.email}}
            </el-descriptions-item>
            <el-descriptions-item  label="联系电话" v-if="undefined != detail.entity.press.phone">
              {{detail.entity.press.phone}}
            </el-descriptions-item>
            <el-descriptions-item  label="简介" v-if="undefined != detail.entity.press.mark">
              {{detail.entity.press.mark}}
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row
        :gutter="40"
        v-if="undefined == detail.entity.book || undefined == detail.entity.auth ||undefined == detail.entity.press">
        <el-col :span="18">
          <el-empty description="暂无此图书信息"></el-empty>
        </el-col>
      </el-row>
    </el-dialog>

    <el-drawer title="资源管理" :visible.sync="res.open" destroy-on-close direction="rtl" append-to-body>
      <edit-resources :book.sync="res.book"/>
    </el-drawer>
  </div>
</template>

<script>
import { listBook, getBook, delBook, addBook, updateBook, changeBookStatus, getBookDetail } from "@/api/userApplication/book";
import { bookclasstreeselectBook } from "@/api/main/bookClass";
import { allAuth } from "@/api/userApplication/auth";
import { allPress } from "@/api/userApplication/press";
import SexLabel from "../base/SexLabel";
import Picture from "../base/Picture";
import TagInput from "../base/tagInput";
import EditResources from "./editResources";
import DataGroup from "../base/dataGroup";
export default {
  name: "Book",
  components: {DataGroup, EditResources, TagInput, SexLabel, Picture},
  dicts: ['library_secrecy_class'],
  comments:{SexLabel,Picture},
  data() {
    return{
        auth:{},
        press:{},
        res: {
          open: false,
          book:{},
        },
        pressList:[],
        authList:[],

        detail:{
        title:'',
        open:false,
        entity:{
          //图书详情
          book:{},
          auth:{},
          press:{},
          bookType:{},
          bookFile:[],
          bookFileSrc:[],
          authFile:[],
          authFileSrc:[],
        }
      },

      /**阅读书籍面板 */
      // 标题
      readTitle : '书籍查阅',
      // 是否开关
      readOpen : false,

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
      // 图书实体信息表格数据
      bookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //图书类别名称
      bookClassName:'',
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 图书类别树选项
      bookClassOptions: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        type: '1',//图书
        leavel: null,
        claz: null,
        author: null,
        mechanism: null,
        publicationDate: null,
        version: null,
        circulation: null,
        isbn: null,
        series: null,
        keyWords: null,
        price: null,
        mark: null,
        status: null,
        createPersonal: null,
        updatePersonal: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name:[
          { required: true, message: "图书名称不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '图书名称长度必须介于 2 和 20 之间的字符串组成', trigger: 'blur' }
        ],
        code:[
          { required: true, message: "图书编码不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '图书编码长度必须介于 2 和 10 之间的字母数字组成', trigger: 'blur' }
        ],
        leavel: [
          { required: true, message: "请选择保存公开级别", trigger: "blur" }
        ],
        bookClassName: [
          { required: true, message: "请选择图书类别", trigger: "blur" }
        ],
        publicationDate: [
          { required: true, message: "出版日期不能为空", trigger: "blur" }
        ],
        isbn: [
          { required: true, message: "图书ISBN不能为空", trigger: "blur" }
        ]
      },
      // 列信息
      columns: [
        { key: 0, label: `ID`, visible: false },
        { key: 1, label: `图书编码`, visible: true },
        { key: 2, label: `图书名称`, visible: true },
        { key: 3, label: `图书类型`, visible: true },
        { key: 4, label: `保存公开级别`, visible: true },
        { key: 5, label: `图书类别`, visible: true },
        { key: 6, label: `作者`, visible: false },
        { key: 7, label: `出版社`, visible: false },
        { key: 8, label: `出版日期`, visible: true },
        { key: 9, label: `图书版本号`, visible: false },
        { key: 10, label: `发行量`, visible: false },
        { key: 11, label: `ISBN`, visible: false },
        { key: 12, label: `系列丛书`, visible: false },
        { key: 13, label: `关键字`, visible: false },
        { key: 14, label: `单价`, visible: false },
        { key: 15, label: `说明`, visible: false },
        { key: 16, label: `状态`, visible: true }
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {


    //@TODO TEST
    onSubmit() {
      console.log('submit!');
    },


    /** 查询图书实体信息列表 */
    getList() {
      this.loading = true;
      this.reset();
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getAuthList(){
      allAuth().then(res=>{
        this.authList = res.data;
      });
    },
    getPressList(){
      allPress().then(res=>{
        this.pressList = res.data;
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
        name: null,
        code: null,
        type: "1",//图书
        leavel: null,
        claz: null,
        author: null,
        authName:'',
        mechanism: null,
        pressName:'',
        publicationDate: null,
        version: null,
        circulation: null,
        isbn: null,
        series: null,
        keyWords: null,
        price: null,
        mark: null,
        other: null,
        status: 0,
        createPersonal: null,
        createTime: null,
        updateTime: null,
        updatePersonal: null
      };
      this.auth = {};
      this.press = {};
      this.bookClassName = '';
      this.resetForm("form");
    },
    /** 查询图书类别下拉树结构 */
    getTreeselect() {
      bookclasstreeselectBook().then(response => {
        this.bookClassOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.bookClassName = data.label;
      this.form.claz = data.id;
      //this.queryParams.deptId = data.id;
      // this.handleQuery();
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
      this.getTreeselect();
      this.getPressList();
      this.getAuthList();
      this.open = true;
      this.title = "新增图书信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();

      const id = row.id || this.ids;
      getBook(id).then(response => {
        this.form = response.data;
        this.getPressList();
        this.getAuthList();

        this.form.claz = row.clazId;
        this.bookClassName = row.claz;
        this.auth.id = row.authorId;
        this.auth.name = row.author;
        this.press.id =  row.mechanismId;
        this.press.name = row.mechanism;
        this.open = true;
        this.title = "修改图书信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // this.form.author = this.auth.id;
      // this.form.mechanism = this.press.id;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除图书实体信息编号为"' + ids + '"的数据项？').then(function() {
        return delBook(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    },
    // 状态修改
    handleBookStatusChange(row) {
      let text = row.status === "1" ? "启用" : "停用";

      this.$modal.confirm('确认要"' + text + '""' + row.name + '"吗？').then(function() {
        return changeBookStatus( row.id , row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查阅图书*/
    handleRead(row){
      // //@TODO 获取图书pdf文件
      this.detail.title = row.name;
      getBookDetail(row.id).then((res)=>{
        this.detail.entity = res.data;
        if (undefined != this.detail.entity.bookFileSrc) {
          for (let index = 0; index < this.detail.entity.bookFileSrc.length; index++) {
            this.detail.entity.bookFileSrc[index] =process.env.VUE_APP_BASE_API +this.detail.entity.bookFileSrc[index];
          }
        }
        this.detail.open = true;
      });
    },
    /**文件资源管理**/
    resources(row){
      this.res.book = row;
      this.res.open = true;
    },
    authChange(auth){
      this.form.author = auth;
      // this.auth = auth;
    },
    meachaseChange(press){
      // this.press = item;
      this.form.mechanism = press;
    },
    //添加标签
    addTags(tags){
      this.form.keyWords = tags.tagsStr;
    },

  }
};
</script>
