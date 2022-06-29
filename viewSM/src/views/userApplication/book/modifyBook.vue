<template>
    <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label-width="150px" label="图书名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入图书名称" />
          </el-form-item>
          <el-form-item label-width="150px" label="图书编码" prop="code">
            <el-input v-model="form.code" placeholder="请输入图书编码" />
          </el-form-item>
          <el-form-item label-width="150px" label="ISBN" prop="isbn">
            <el-input v-model="form.isbn" placeholder="请输入ISBN" />
          </el-form-item>
          <el-form-item label-width="150px" label="保存公开级别" prop="leavel">
            <el-select v-model="form.leavel" placeholder="请选择保存\公开级别">
              <el-option
                v-for="dict in dict.type.library_secrecy_class"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="150px" label="图书类别" prop="claz" >
            <el-row :gutter="20">
              <!--图书类别-->
              <el-col :span="4" :xs="24">
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
                    style="width: 400px;max-height: 200px;overflow-y: auto;"
                  />
                </div>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item label-width="150px" label="出版日期" prop="publicationDate">
            <el-date-picker clearable size="small"
                            v-model="form.publicationDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择出版日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label-width="150px" label="图书版本号" prop="version">
            <el-input v-model="form.version" placeholder="请输入图书版本号" />
          </el-form-item>
          <el-form-item label-width="150px" label="发行量" prop="circulation">
            <el-input v-model="form.circulation" placeholder="请输入发行量" />
          </el-form-item>
          <el-form-item label-width="150px" label="系列丛书" prop="series">
            <el-input v-model="form.series" placeholder="请输入系列丛书名称" />
          </el-form-item>
          <el-form-item label-width="150px" label="关键字" prop="keyWords">
            <el-input v-model="form.keyWords" placeholder="请输入关键字" />
          </el-form-item>
          <el-form-item label-width="150px" label="单价" prop="price">
            <el-input v-model="form.price" placeholder="请输入单价" />
          </el-form-item>
          <el-form-item label-width="150px" label="说明" prop="mark">
            <el-input v-model="form.mark" placeholder="请输入说明" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
    </div>
</template>

<script>
  import { bookclasstreeselect } from "@/api/main/bookClass";
    export default {
        name: "modifyBook",
      props:{

      },
      comments:{},
      dicts: ['library_secrecy_class'],
      data(){
          return{
            bookClassOptions:{},
            defaultProps:{},

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
                { min: 2, max: 20, message: '图书编码长度必须介于 2 和 20 之间的字母数字组成', trigger: 'blur' }
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
          }
      },
      methods:{
        /** 提交按钮 */
        submitForm() {
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
        cancel(){

        },

        /** 查询图书类别下拉树结构 */
        getTreeselect() {
          bookclasstreeselect().then(response => {
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
      }
    }
</script>

<style scoped>

</style>
