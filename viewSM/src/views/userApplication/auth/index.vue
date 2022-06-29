<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作者名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入作者名称"
          clearable
          size="small"
          prefix-icon="el-icon-s-custom"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国籍" prop="country">
        <el-select
          placeholder="请选择国家"
          filterable style="width: 77.9%"
          v-model="queryParams.country">
          <el-option-group
            v-for="group in country"
            :key="group.label"
            :label="group.label">
            <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.label">
              <span style="float: left">{{ item.label }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-option-group>
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
          v-hasPermi="['userApplication:auth:add']"
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
          v-hasPermi="['userApplication:auth:edit']"
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
          v-hasPermi="['userApplication:auth:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:auth:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authList" @selection-change="handleSelectionChange" max-height="600">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="作者名称" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="sex" >
       <template slot-scope="scope">
         <SexLabel :sex="scope.row.sex"/>
<!--         <div v-if="scope.row.sex === 1">-->
<!--           <el-tag type="success" >男</el-tag>-->
<!--         </div>-->
<!--         <div v-else-if="scope.row.sex === 2">-->
<!--           <el-tag type="danger">女</el-tag>-->
<!--         </div>-->
<!--         <div v-else>-->
<!--           <el-tag type="warning">未知</el-tag>-->
<!--         </div>-->
       </template>
      </el-table-column>
      <el-table-column label="国籍" align="center" prop="country" />
      <el-table-column label="作者别名" align="center" prop="nickName" />
      <el-table-column label="英文名称" align="center" prop="englishName" />
      <el-table-column label="简介" align="center" prop="synopsis" v-if="false">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.synopsis" placement="top-start">
            <span>{{scope.row.synopsis.substring(0,5)}}...</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="社会身份" align="center" prop="identity"  v-if="false"/>
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value= "1"
            inactive-value= "0"
            inactive-color="#ff9292"
            @change="handleAuthStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createUser.userName" />
      <el-table-column label="更新人" align="center" prop="updateUser.userName" >
        <template slot-scope="solt">
                    <FomatLabel label="-" v-if="undefined == solt.row.updateUser"/>
          <FomatLabel :label="solt.row.updateUser.userName" v-if="undefined != solt.row.updateUser"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="info"
            icon="el-icon-read"
            @click="detailMessage(scope.row)"
            v-hasPermi="['userApplication:auth:read']"
            style="padding: 3px 5px"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['userApplication:auth:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:auth:remove']"
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

    <!-- 添加或修改作信息者对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body :before-close="handleClose">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作者名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入作者名称" />
        </el-form-item>
        <el-form-item label="作者别名" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入作者别名" />
        </el-form-item>
        <el-form-item label="英文名称" prop="englishName">
          <el-input v-model="form.englishName" placeholder="请输入英文名称" />
        </el-form-item>
        <el-form-item label="性别" prop="englishName">
          <template >
            <el-select v-model="form.sex" :disabled="disabled" style="width: 100%">
              <el-option label="男" :value="1"></el-option>
              <el-option label="女" :value="2"></el-option>
              <el-option label="未知" :value="0"></el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="国籍" prop="country">
          <CountrySelect :cur-country="form.country"/>
        </el-form-item>
        <el-form-item label="社会身份" prop="identity">
          <el-input v-model="form.identity" placeholder="请输入社会身份，多个身份逗号分隔" />
        </el-form-item>
        <el-form-item label="简介" prop="synopsis">
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入简介"
            maxlength="500"
            v-model="form.synopsis">
          </el-input>
        </el-form-item>
        <el-form-item label="图片上传" prop="synopsis">
          <el-upload
            ref="upload"
            class="upload-demo"
            action="#"
            :http-request="fileUploadAct"
            :data="uploadData"
            :on-preview="handlePicPreview"
            :on-remove="handlePicRemove"
            :file-list="formPicList"
            :limit="1"
            :destroy-on-close="true"
            :auto-upload="true"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传[<font style="color: #ff9292;">1</font>]个<font style="color: #ff9292;">jpg</font>/<font style="color: #ff9292;">png</font>文件，且不超过<font style="color: #ff9292;">500kb</font></div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--作者具体信息-->
    <el-dialog :title="detailTitle" :visible.sync="detailOpen" width="50%" append-to-body>
      <el-row :gutter="20">
        <el-col :span="10">
          <Picture :srcList="srcList" />
        </el-col>
        <el-col :span="14">
          <div class="grid-content bg-purple">
            <el-form ref="form" label-width="80px" >
              <el-form-item>
                <label style="font-size: 22px;color:#333;">{{detailName}}</label>
              </el-form-item>
              <el-form-item label="关键信息">
                <div class="tag-group">
                  <el-tag
                    v-for="item in detailItems"
                    :key="item.val"
                    :type="item.effect"
                    effect="success"
                    style="margin-left: 10px">
                    {{ item.val }}
                  </el-tag>
                </div>
              </el-form-item>
              <el-form-item>
                <el-descriptions class="margin-top" direction="vertical" :column="4" :size="size" border>
                  <el-descriptions-item label="简介">{{detailMsg}}</el-descriptions-item>
                </el-descriptions>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { listAuth, getAuth, delAuth, addAuth, updateAuth, changeAuthStatus, getFile } from "@/api/userApplication/auth";
import Template from "../template/index";
import { addFileList} from "@/api/userApplication/file";
import Picture from "../base/Picture";
import CountrySelect from "../base/CountrySelect";
import SexLabel from "../base/SexLabel";
import FomatLabel from "../base/FomatLabel";
export default {
  name: "Auth",
  components: {Template,Picture,CountrySelect,SexLabel,FomatLabel},
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
      // 作信息者表格数据
      authList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        sex: null,
        country: null,
        nickName: null,
        englishName: null,
        synopsis: null,
        identity: null,
        status: null,
        createPersonal: null,
        updatePersonal: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },

      //作者头像信息列表
      fileUploadPath: process.env.VUE_APP_BASE_API + "/sysFile/upload",
      uploadData:{},
      formPicList: [],//[{name:'',url:''}],
      fileMsg:[],

      /**作者具体信息面板 */
      // 标题
      detailTitle : '详细',
      detailName:'',
      // 是否开关
      detailOpen : false,
      fileSrcList:[],
      srcList: [
        '/dev-api/profile/2022/system/no-picture.png',
        '/dev-api/profile/2022/system/no-picture.png'
      ],
      detailItems: [],
      detailMsg:'',
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询作信息者列表 */
    getList() {
      this.loading = true;
      listAuth(this.queryParams).then(response => {
        this.authList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.formPicList = [];
      this.fileMsg = [];
      this.uploadData = {};
      this.reset();
    },
    handleClose(done){
      this.formPicList = [];
      this.fileMsg = [];
      this.uploadData = {};
      this.$modal.msgSuccess("已取消");
      done();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        sex: null,
        country: null,
        nickName: null,
        englishName: null,
        synopsis: null,
        identity: null,
        status: 0,
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length!==1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作信息者";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getAuth(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改作信息者";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.picList = this.fileMsg;
          if (this.form.id != null) {
            updateAuth(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).catch(reason => {
              this.$modal.msgError("修改失败");
            });

          } else {
            addAuth(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }

        }
      });
      this.formPicList = [];
      this.fileMsg = [];
      this.uploadData = {};
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除作信息者编号为"' + ids + '"的数据项？').then(function() {
        return delAuth(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/auth/export', {
        ...this.queryParams
      }, `auth_${new Date().getTime()}.xlsx`)
    },
    /** 头像上传*/
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") === -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.fileMsg.url = reader.result;
        };
      }
    },
    fileUploadAct(files){
      let upData = new FormData(); // 首先创建FormData对象
      upData.append("files", files.file); //不要直接使用我们的文件数组进行上传，你会发现传给后台的是两个Object

      addFileList(upData).then(res => {
        if (res.code === 200) {
          this.$message.success("添加成功");
          this.fileMsg.push(res.data[0]);
        } else {
          // 请求失败后的一些操作
          this.$message.warning("添加失败");
          this.file = [] // 阻止上传的文件被重复添加，所以清空掉
        }
      });
    },
    handlePicRemove(file, fileList) {
      let index = 0;
      for (index;index < fileList.length; index++) {
        if (file.url === fileList[index].url){
          this.fileMsg.splice(index,1);
          break;
        }
      }
      console.log(file, fileList);
    },
    handlePicPreview(file) {
      console.log(file);
    },
    handleUploadSucceed(param){
      // this.formPicList.push(param.files);
      this.$modal.msgSuccess(file);
      this.$modal.msgSuccess(param);
    },
    /** 状态修改*/
    handleAuthStatusChange(row) {
      let text = row.status === "1" ? "启用" : "停用";

      this.$modal.confirm('确认要"' + text + '""' + row.name + '"这名作者吗？').then(function() {
        return changeAuthStatus( row.id , row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },

    /** 关闭细节信息 */
    closeDetail(){
      this.detailOpen = false;
    },
    /** 打开细节信息 */
    detailMessage(row){
      //获取图片信息
      getFile(row.id).then(response => {
        this.srcList=[];
        this.srcList=response.data;
      });

      this.detailOpen = true;
      let sexLabel = row.sex===1?"男":(row.sex===2?"女":"性别保密");
      this.detailName = row.name;
      this.detailItems = [
        {val:row.nickName,effect:"info"},
        {val:row.englishName,effect:"warning"},
        {val:row.country,effect:"danger"},
        {val:sexLabel,effect:"success"}
        ];
      let identifyList = row.identity.split(",");
      identifyList.forEach(iden =>{
        this.detailItems.push({"val":iden,"effect":"success"});
      });
      this.detailMsg=row.synopsis;
    }
  }
};

</script>
