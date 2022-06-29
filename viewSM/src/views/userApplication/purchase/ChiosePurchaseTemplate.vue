<template>
    <div style="padding: 20px;">
      <el-form>
        <el-form-item label="归属部门" prop="dept">
          <Treeselect
            v-model="dept"
            :options="deptOptions"
            :show-count="true"
            placeholder="请选择归属部门"/>
        </el-form-item>
        <el-form-item>
          <el-table v-loading="loading" :data="templateList" style="max-height: 70vh;min-height: 70vh;overflow-y: auto;">
            <el-table-column width="150" align="center" >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleBrowseLink(scope.row)"
                  v-hasPermi="['userApplication:template:edit']"
                >查看模板</el-button>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="title" />
            <el-table-column align="center" class-name="small-padding fixed-width" width="150">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  @click="sureThisTemplate(scope.row)"
                  v-hasPermi="['userApplication:template:edit']"
                >立即选择</el-button>
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
        </el-form-item>
      </el-form>

<!--      <el-dialog-->
<!--        :title="purchaseLink.title"-->
<!--        destroy-on-close-->
<!--        :visible.sync="purchaseLink.open"-->
<!--        width="70%" append-to-body>-->
      <el-drawer
        :title="purchaseLink.title"
        :visible.sync="purchaseLink.open"
        direction="rtl"
        size="90%"
        append-to-body
        destroy-on-close>
        <flow-panel
          :purchase="purchaseLink" :isEdit="false"/>
      </el-drawer>
<!--      </el-dialog>-->
    </div>
</template>

<script>

  import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/userApplication/template";
  import Treeselect from "@riophae/vue-treeselect";
  import { treeselect } from "@/api/system/dept";
  import FlowPanel from '@/components/ef/panel'
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    export default {
        name: "ChiosePurchaseTemplate",
      components: { Treeselect,FlowPanel },
      data(){
          return{
            // 部门树选项
            deptOptions: undefined,
            // 部门名称
            deptName: undefined,
            defaultProps: {
              children: "children",
              label: "label"
            },

            dept:undefined,
            total:0,
            loading:false,
            // 审批流程模板表格数据
            templateList: [],
            // 查询参数
            queryParams: {
              pageNum: 1,
              pageSize: 10,
              code: null,
              title: null,
              dept:null,
              template: null,
              mark: null,
              status: null,
              createPersonal: null,
              updatePersonal: null,
            },

            purchaseLink:{
              open:false,
              title:'',
              purchase:{},
              template:{},
              showPurchaseId:undefined,//待展示的流程id
            },
          }
      },
      watch: {
        // 根据名称筛选部门树
        dept(val) {
          this.queryParams.dept = this.dept;
          this.getList();
          // this.$refs.tree.filter(val);
        }
      },
      created() {
        this.getTreeselect();
      },
      methods:{
        /** 查询部门下拉树结构 */
        getTreeselect() {
          treeselect().then(response => {
            this.deptOptions = response.data;
          });
        },
        // 筛选节点
        filterNode(value, data) {
          if (!value) return true;
          return data.label.indexOf(value) !== -1;
        },
        // 节点单击事件
        handleNodeClick(data) {
          this.query.dept = data.id;
          this.handleQuery();
        },

        sureThisTemplate(row){
            this.$emit("sure", row);
        },


        closePurchase(){
          this.purchaseLink.template = null;
          this.purchaseLink.showPurchaseId=null;
        },
        /** 查询审批流程模板列表 */
        getList() {
          // if(undefined == this.queryParams.dept || null == this.queryParams.dept){
          //   this.$modal.msgError("部门信息未选择");
          //   this.templateList = [];
          //   this.total = 0;
          //   return;
          // }

          this.loading = true;
          this.queryParams.status = '1';
          listTemplate(this.queryParams).then(response => {
            this.templateList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },

        /** 查看处理流程 */
        handleBrowseLink(row) {

          this.purchaseLink.open = false;
          this.purchaseLink.template = {};
          this.purchaseLink.showPurchaseId = row.id;
          getTemplate(row.id).then(res => {
            this.purchaseLink.template = res.data;
            this.purchaseLink.title = '关于《' + res.data.title + '》流程模板信息如下所示：';
            this.purchaseLink.open = true;
          }).catch(()=>{
            this.$modal.msgError("系统繁忙");
            this.purchaseLink.open = false;
          });
        },
      }
    }
</script>

<style scoped>

</style>
