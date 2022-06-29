
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="机构名称" prop="deptId">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入图书管理机构名称"
          clearable
          size="small"
          prefix-icon="book-feed"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位地址" prop="addr">
        <el-input
          v-model="queryParams.addr"
          placeholder="请输入单位地址"
          clearable
          size="small"
          prefix-icon="el-icon-map-location"
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
          v-hasPermi="['userApplication:mechanism:add']"
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
          v-hasPermi="['userApplication:mechanism:edit']"
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
          v-hasPermi="['userApplication:mechanism:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['userApplication:mechanism:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mechanismList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="机构编号" align="center" prop="deptId" v-if="false"/>
      <el-table-column label="机构名称" align="center" prop="dept.deptName" />
      <el-table-column label="邮箱" align="center" prop="email" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="单位地址" align="center" prop="addr" >
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.addr.replaceAll(':','')" placement="top-start">
            <span>{{scope.row.addr.replaceAll(':','').substring(0,5)}}...</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="简介" align="center" prop="synopsis" v-if="false">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.mark" placement="top-start">
            <span>{{scope.row.mark.substring(0,5)}}...</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createUser.userName" />
      <el-table-column label="更新人" align="center" prop="updateUser.userName" >
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
            @change="handleMechanismStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
            v-hasPermi="['userApplication:mechanism:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['userApplication:mechanism:remove']"
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
      layout="total, sizes, prev, pager, next, jumper"
      class="pagination-bar"
    />

    <!-- 添加或修改图书管理机构信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="机构名称" prop="deptId">
          <el-input v-model="form.deptName" placeholder="请输入图书管理机构名称" />
        </el-form-item>
        <el-form-item label="联系人" prop="addr">
          <el-input v-model="form.contacts" placeholder="请输入单位联系人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入单位联系电话" />
        </el-form-item>
        <el-form-item label="单位邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入单位邮箱" />
        </el-form-item>
        <el-form-item label="单位地址" prop="addr">
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
        <el-form-item label="机构简介" prop="mark">
          <el-input
            type="textarea"
            :rows="5"
            placeholder="请输入机构简介"
            maxlength="2000"
            v-model="form.mark">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--机构具体信息-->
    <el-dialog :title="detailTitle" :visible.sync="detailOpen" width="70%" append-to-body>
      <el-row >
        <el-col :span="30">
          <el-image
            v-for="srcItem in srcList"
            :key="srcItem"
            :src="srcItem"
            fit="scale-down"
            style="width:200px;height:200px;margin: 5px;border-radius: 5px;box-shadow: 0 0 2px #04A78C;"/>
          <el-image
            :key="addUrl"
            :src="addUrl"
            fit="scale-down"
            style="
            width:200px;
            height:200px;
            background-repeat:no-repeat;
            background-position:100% 100%;
            background-size:100% 100%;
            margin: 5px;
            border-radius: 50%;
            cursor: pointer;" @click="appPicture"/>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-divider content-position="right"><i class="el-icon-location-information"></i>{{detailDept.simpleAddr}}</el-divider>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="30">
          <div class="grid-content bg-purple">
            <el-descriptions title="详细信息"
              :column="2">
              <el-descriptions-item label="联系电话">{{detailMsg.phone}}</el-descriptions-item>
              <el-descriptions-item label="邮箱地址">{{detailMsg.email}}</el-descriptions-item>
              <el-descriptions-item label="负责人">{{detailDept.leader}}</el-descriptions-item>
              <el-descriptions-item label="联系地址">{{detailDept.otherAddr}}</el-descriptions-item>
              <el-descriptions-item label="关键信息" >
                <div class="tag-group">
                  <el-tag type="info" effect="success" icon="el-icon-loading" v-if="keyListLoadding" style="margin-left: 10px">加载中</el-tag>
                  <el-tag
                    v-for="k in keyList"
                    :key="k"
                    type="info"
                    effect="success"
                    style="margin-left: 10px">
                    {{ k }}
                  </el-tag>
                </div>
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-descriptions title="" direction="vertical" :column="1" border>
            <el-descriptions-item label="机构摘要">{{detailDept.mark}}</el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="5" :offset="18">
          <el-button type="danger" plain style="float: right" @click="closeDetial">关闭</el-button>
        </el-col>
      </el-row>
    </el-dialog>

    <el-dialog
      :title="addPic.title"
      :visible.sync="addPic.open"
      width="60%"
      :close="closePictUpload"
      append-to-body
       style="border-radius: 10px;box-shadow: 0 0 5px #aaa;">
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="fileUploadAct"
            :on-preview="handlePreviewPic"
            :on-remove="handleRemovePic"
            :file-list="addPic.picList"
            :on-exceed="handelExceed"
            multiple="true"
            :limit="addPic.maxNum"
            list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
          <el-divider content-position="right"><i class="el-icon-picture-outline"></i></el-divider>
          <span slot="footer" class="dialog-footer">
            <el-button @click="addPic.open = false">取 消</el-button>
            <el-button type="primary" @click="sureToUpload">确 定</el-button>
          </span>

    </el-dialog>
  </div>
</template>

<script>
import { listMechanism, getMechanism, delMechanism, addMechanism, updateMechanism, changeMechanismStatus, getFile, getFileList, analysisStr, reflushFile } from "@/api/userApplication/mechanism";
import { addFileList} from "@/api/userApplication/file";
import {isEmpty} from "element-ui/src/utils/util";
import FomatLabel from "../base/FomatLabel";

export default {
  name: "Mechanism",
  components: {FomatLabel},
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
      // 图书管理机构信息表格数据
      mechanismList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        deptName: null,
        email: null,
        phone: null,
        addr: null,
        mark:null,
        status: null,
        createPerson: null,
        updatePerson: null,
        delTime: null,
        delPerson: null
      },
      addr:{province:"",city:"",area:"",other:"",all:""},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },


      /**作者具体信息面板 */
      // 标题
      detailTitle : '详细',
      detailName:'',
      // 是否开关
      detailOpen : false,
      url:   '/dev-api/profile/2022/system/no-picture.png',
      addUrl:'http://localhost:81/dev-api/profile/avatar/2022/system/add.png',
      fileSrcList:[],
      detailItems: [],
      detailMsg:[],
      detailDept:{},
      keyList:[],
      keyListLoadding:false,
      srcList:[
        'http://localhost:81/dev-api/profile/avatar/2022/02/16/5f3e109f-356e-402a-8afe-29b904065e1a.jpeg',
        'http://localhost:81/dev-api/profile/avatar/2022/02/16/5f3e109f-356e-402a-8afe-29b904065e1a.jpeg',
        'http://localhost:81/dev-api/profile/avatar/2022/02/16/a9cd668e-05b0-41fa-bea6-a36819bbf6ff.png',
      ],
      //新增图片
      addPic:{
        open:false,
        maxNum:5,
        title:'新增图片',
        picList:[
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''},
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''},
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''},
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''},
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''},
          {name:'xjh.jpg',url:'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',fileId:'',modelId:'',idDel:false,isNew:false,mark:''}],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图书管理机构信息列表 */
    getList() {
      this.loading = true;
      listMechanism(this.queryParams).then(response => {
        this.mechanismList = response.rows;
        this.total = response.total;
        this.loading = false;
        //this.$modal.msgSuccess(response.toString());
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
        deptId: null,
        email: null,
        phone: null,
        addr: null,
        mark:null,
        status: 0,
        createTime: null,
        createPerson: null,
        updateTime: null,
        updatePerson: null,
        delTime: null,
        delPerson: null
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
    handleAdd(row) {
      this.reset();
      this.open = true;
      this.title = "添加图书管理机构信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMechanism(id).then(response => {
        this.form = response.data;
        this.form.deptName = this.form.dept.deptName;
        this.form.contacts = this.form.dept.leader;
        let areaList = this.form.addr.split(":");
        this.addr.province = areaList[0];
        this.addr.city = areaList[1];
        this.addr.area = areaList[2];
        this.addr.other = areaList[3];
        this.open = true;
        this.title = "修改图书管理机构信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMechanism(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMechanism(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除图书管理机构信息编号为"' + ids + '"的数据项？').then(function() {
        return delMechanism(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('userApplication/mechanism/export', {
        ...this.queryParams
      }, `mechanism_${new Date().getTime()}.xlsx`)
    },
    /** 状态修改*/
    handleMechanismStatusChange(row) {
      let text = row.status === "1" ? "启用" : "停用";

      this.$modal.confirm('确认要"' + text + '""' + row.name + '"这名作者吗？').then(function() {
        return changeMechanismStatus( row.id , row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
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

    /** 打开细节信息 */
    detailMessage(row){
      this.keyListLoadding =true;
      this.detailTitle = row.dept.deptName;

      getMechanism(row.id).then(res=>{
        this.detailMsg = res.data;
        //this.detailMsg.addr.replaceAll(':','');
        this.detailDept = this.detailMsg.dept;
        this.detailDept.mark = this.detailMsg.mark;

        let index = this.detailMsg.addr.lastIndexOf(":");
        this.detailDept.simpleAddr = this.detailMsg.addr.substring(0,index).replaceAll(':','-');
        this.detailDept.otherAddr = this.detailMsg.addr.substring(index).replaceAll(':','');


        this.detailOpen = true;
      }).catch(error=>{
        this.detailOpen = false;
        this.$modal.msgError("获取详情信息失败");
      });

      //获取图片信息
      getFile(row.id).then(response => {
        this.srcList=response.data;
        this.url = this.srcList[0];
      });

      let keyWords = [];
       analysisStr(row.id).then(r=>{
        //keyWords = res.data;
        // if (isEmpty(res.data)){
        //   this.$modal.msgSuccess("连接超时");
        //   return;
        // }
        for (let i = 0;i<r.data.length;i++){
          keyWords.push(r.data[i].replaceAll("\\n",""));
        }

        this.keyList = keyWords;
      }).catch(err=>{
        keyWords.push("无")
      });

      this.keyListLoadding=false;
    },
    closeDetial(){
      // 是否开关
      this.detailOpen = false;

      this.detailTitle ='详细';
      this.detailName='';
      this.url ='';
      this.fileSrcList.length=0;
      this.detailItems.length=0;
      this.detailMsg.length=0;
      this.detailDept={};
      this. keyList.length=0;
      this.keyListLoadding=false;
      this.srcList.length=0;

      this.$modal.msgSuccess("关闭成功");
    },
    //添加图片
    appPicture(){

      let query={
        modelId:this.detailMsg.id
      };
      this.addPic.picList=[];
      getFileList(query).then(res=>{
       let list = res.data;
       list.forEach(l=>{
         let fileOb = {};
         fileOb.name = l.name;
         fileOb.url = l.url;
         fileOb.modelId = l.modelId;
         fileOb.fileId = l.fileId;
         fileOb.isDel = false;//文件是否在本次操作被删除
         fileOb.isNew = false;//是否新增文件
         fileOb.mark = l.mark;
         this.addPic.picList.push(fileOb);
       });
        this.addPic.open=true;
      }).catch(err=>{
        this.$modal.msgError("图片列表获取失败");
      });
    },
    closePictUpload(){
      this.addPic.picList=[];
    },
    handlePreviewPic(){

    },
    handleRemovePic(file, fileList){
      this.addPic.picList = fileList;
      for(let index = 0;index<fileList.length;index++){
        if (fileList[index].fileId === file.fileId){
          fileList.splice(index);
          this.$message.success("第"+index+"个文件移除成功!");
          return;
        }
      }
    },
    handelExceed(files, fileList){
      this.$message.error("添加失败：最多上传文件个数为"+this.addPic.maxNum);
    },
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
          let list = res.data;
          list.forEach(l=>{
            let fileOb = {};
            fileOb.name = l.name;
            fileOb.url = "/dev-api"+l.url;
            fileOb.modelId = this.detailMsg.id;
            fileOb.fileId = l.id;
            fileOb.isDel = false;//文件是否在本次操作被删除
            fileOb.isNew = true;//是否新增文件
            fileOb.mark = l.mark;
            this.addPic.picList.push(fileOb);
          });
          this.$message.success("添加成功");
        } else {
          // 请求失败后的一些操作
          this.$message.warning("添加失败");
          this.addPic.picList = [] ;// 阻止上传的文件被重复添加，所以清空掉
          this.appPicture();//重新加载图片
        }
      });
    },
    sureToUpload(){
      let files = this.addPic.picList;

      reflushFile(files,this.detailMsg.id).then(res=>{
        let result = res.data;

        let filesSrcList = [];
        for (let index = 0;index<this.addPic.picList.length;index++){
          filesSrcList.push(this.addPic.picList[index].url);
        }
        this.srcList = filesSrcList;

        this.$message.success(
          "添加成功:本次提交"+result.submitNum
          +"条,删除"+result.delNum
          +"条,新增"+result.insertNum
          +"条,原始记录"+result.agoNum
          +"条,现有记录"+result.nowNum+"条。");
      }).catch(err=>{
        this.$message.warning("添加失败");
      });

      this.addPic.open = false;
    }
  }
};
</script>
