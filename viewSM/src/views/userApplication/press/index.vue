<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入出版社名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="addr">
        <el-input
          v-model="queryParams.addr"
          placeholder="请输入出版社地址"
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
          v-hasPermi="['userApplication:press:add']"
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
          v-hasPermi="['userApplication:press:edit']"
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
          v-hasPermi="['userApplication:press:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:press:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="地址" align="center" prop="addr" >
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.addr.replaceAll(':','')" placement="top-start">
            <span>{{scope.row.addr.replaceAll(':','').substring(0,5)}}...</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="单位邮箱" align="center" prop="email" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="简介" align="center" prop="mark" >
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.mark" placement="top-start">
            <span>{{scope.row.mark.substring(0,5)}}...</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createUser.userName" />
      <el-table-column label="修改人" align="center" prop="updateUser.userName" >
        <template slot-scope="solt">
          <FomatLabel label="-" v-if="undefined == solt.row.updateUser"/>
          <FomatLabel :label="solt.row.updateUser.userName" v-if="undefined != solt.row.updateUser"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value= "1"
            inactive-value= "0"
            inactive-color="#ff9292"
            @change="handlePressStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:press:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:press:remove']"
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

    <!-- 添加或修改出版社信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入出版社名称" />
        </el-form-item>
        <el-form-item label="地址" prop="addr">
          <v-distpicker
            :province="addr.province"
            :city="addr.city"
            :area="addr.area"
            @province="onChangeProvince"
            @city="onChangeCity"
            @area="onChangeArea"
            style="margin: 2px 5px;padding: 5px 2px;">
          </v-distpicker>
          <el-input style="margin: 2px 5px" v-model="addr.other" placeholder="详细地址" prefix-icon="el-icon-edit" @blur="onChangeOther">
          </el-input>
          <span style="color: #999;font-size: 12px;"><font style="color: #E6A23C;">{{addr.province}}</font><font style="color: #04A78C;">{{addr.city}}</font><font style="color: #F56C6C;">{{addr.area}}</font>{{addr.other}}</span>
          <el-divider style="margin-top: 2px !important;"/>
        </el-form-item>
        <el-form-item label="单位邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入单位邮箱" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="简介" prop="mark">
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入出版社简介"
            maxlength="500"
            v-model="form.mark">
          </el-input>
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
import { listPress, getPress, delPress, addPress, updatePress, changePressStatus } from "@/api/userApplication/press";
// 地址选择组件
import VDistpicker from 'v-distpicker'
import FomatLabel from "../base/FomatLabel";
export default {
  name: "Press",
  comments:{VDistpicker,FomatLabel},
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
      // 出版社信息表格数据
      pressList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        addr: null,
        mark: null,
        email: null,
        phone: null,
        status: null,
        createPersonal: null,
        updatePersonal: null,
        delTime: null,
        delPersonal: null
      },
      addr:{province:"",city:"",area:"",other:"",all:""},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name:[
          { required: true, message: "出版社名称不能为空", trigger: "blur" },
          { min: 3, max: 50, message: '图书名称长度必须介于 3 和 50 之间的字符串组成', trigger: 'blur' },
          { type: 'string',message: '备注内容包含非法字符',trigger: 'blur',
            transform (value) {
              if (/[`~!@#$^&*()=|{}':;',\\[\\].<>《》?~！@#￥……&*（）——|{}【】‘；：”“。，、？ ]$/.test(value) ){
                return true;
              }
            }
          }
        ],
        other:[
          { required: false, message: "详细地址不能包含特殊符号，如：:&|=.等", trigger: "blur" },
          { type: 'string',message: '备注内容包含非法字符',trigger: 'blur',
            transform (value) {
              if (/[`~!@#$^&*()=|{}':;',\\[\\].<>《》?~！@#￥……&*（）——|{}【】‘；：”“。，、？ ]$/.test(value)){
                return true;
              }
            }
          }
        ],
        phone:[
          { required: false, message: "请输入合法手机号码", trigger: "blur" },
          { type: 'string',message: '手机号码格式不正确',trigger: 'blur',
            transform (value) {
               if(value!=="" && !/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value)){
                 return true;
               }
            }
          }
        ],
        email:[
          { required: false, message: "请输入合法邮箱号", trigger: "blur" },
          { type: 'string',message: '邮箱格式不正确',trigger: 'blur',
            transform (value) {
              if (value!=="" && !/^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value)){
                return true;
              }
            }
          }
        ],
        mark:[
          { required: false, message: "备注内容不能包含特殊符号，如：:&|=.等", trigger: "blur" },
          { type: 'string',message: '备注内容包含非法字符',trigger: 'blur',
            transform (value) {
              if ( /[`~!@#$^&*()=|{}':;',\\[\\].<>《》?~！@#￥……&*（）——|{}【】‘；：”“。，、？ ]$/.test(value)){
                return true;
              }
            }
          }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询出版社信息列表 */
    getList() {
      this.loading = true;
      listPress(this.queryParams).then(response => {
        this.pressList = response.rows;
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
        name: null,
        addr: null,
        mark: null,
        email: null,
        phone: null,
        status: 0,
        createTime: null,
        createPersonal: null,
        updateTime: null,
        updatePersonal: null,
        delTime: null,
        delPersonal: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length!==1;
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出版社信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      let areaList = row.addr.split(":");
      this.addr.province = areaList[0];
      this.addr.city = areaList[1];
      this.addr.area = areaList[2];
      this.addr.other = areaList[3];
      getPress(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出版社信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePress(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPress(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除出版社信息编号为"' + ids + '"的数据项？').then(function() {
        return delPress(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/press/export', {
        ...this.queryParams
      }, `press_${new Date().getTime()}.xlsx`)
    },
    /** 地址选择*/
    onChangeProvince(data) {
      this.addr.province = data.value;
      this.setAddr();
    },
    onChangeCity(data) {
      this.addr.city = data.value;
      this.setAddr();
    },
    onChangeArea(data){
      this.addr.area = data.value;
      this.setAddr();
    },
    onChangeOther(){
      this.setAddr();
    },
    setAddr(){
      this.form.addr =
        ( (this.addr.province!=="省") ? this.addr.province: "") +
        ( (this.addr.city    !=="市") ? ":"+this.addr.city: "") +
        ( (this.addr.area    !=="区") ? ":"+this.addr.area: "") +
        ( (this.addr.other.trim()   !=="") ? ":"+ this.addr.other.trim():"");
      this.addr.all = this.form.addr.replaceAll(':','');
    },

    /** 状态修改*/
    handlePressStatusChange(row) {
      let text = row.status === "1" ? "启用" : "停用";

      this.$modal.confirm('确认要"' + text + '""' + row.name + '"这名作者吗？').then(function() {
        return changePressStatus( row.id , row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>
