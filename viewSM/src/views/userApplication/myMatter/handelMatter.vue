<template>
  <div>
    <el-row>
      <el-col :span="18">
        <el-form  ref="purchaseForm" label-width="100px"  class="demo-ruleForm" style="padding-right:15px;">
          <el-form-item label="申请标题" prop="purchase.title">
            <span >{{purchaseForm.purchase.title}}</span>
          </el-form-item>
          <el-form-item  label="申请部门"  prop="purchase.approveDeptName">
            <span>{{purchaseForm.purchase.approveDeptName}}</span>
          </el-form-item>
<!--          <el-form-item  label="审批流程"  prop="purchase.purchaseTemplateName">-->
<!--            <span>{{purchaseForm.purchase.purchaseTemplateName}}</span>-->
<!--          </el-form-item>-->
          <el-form-item  label="截止日期"  prop="purchase.termValidity">
            <span>{{purchaseForm.purchase.termValidity}}</span> <el-divider direction="vertical"/> <el-tag v-if="'-2'==purchaseForm.purchase.purchaseStatus" type="danger" effect="dark">已过期</el-tag>
          </el-form-item>
          <el-form-item label="备注信息" prop="purchase.mark">
            <el-input type="textarea" style="cursor: not-allowed !important;" :readonly="true" v-model="purchaseForm.purchase.mark"></el-input>
          </el-form-item>
          <el-form-item label="申请项目">
            <el-table
              :data="purchaseForm.subPurchase"
              height="280"
              v-loading="purchaseForm.loading"
              border>
              <el-table-column type="selection" width="55" align="center" v-if="false"/>
              <el-table-column label="序号" align="center" width="50" prop="index" />
              <el-table-column label="采购申请组" align="center" prop="groupId" v-if="false" />
              <el-table-column label="待采购图书" align="center" prop="entityName" >
                <template slot-scope="slot">
                  <span>{{slot.row.entityName}}</span>
                </template>
              </el-table-column>
              <el-table-column label="采购数" align="center" prop="purchaseNumber" width="250">
                <template slot-scope="slot">
                  <span>{{slot.row.purchaseNumber}}</span>
                </template>
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="utilName" width="200">
                <template slot-scope="slot">
                 <span>{{slot.row.utilEntity.name}}</span>
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" prop="remark" >
                <template slot-scope="slot">
                  <span>{{slot.row.remark}}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="审批意见" required>
            <el-input
              type="textarea"
              v-model="approve.mark"
              :disabled="queue.result=='1' || queue.result=='2' || '-2'==purchaseForm.purchase.purchaseStatus"
              :readonly="queue.result=='1' || queue.result=='2' || '-2'==purchaseForm.purchase.purchaseStatus"/>
          </el-form-item>
          <el-form-item label="审批结果" required>
            <el-radio
              :disabled="queue.result=='1' || queue.result=='2' || '-2'==purchaseForm.purchase.purchaseStatus"
              v-model="approve.result" label="1">同意</el-radio>
            <el-radio
              :disabled="queue.result=='1' || queue.result=='2' || '-2'==purchaseForm.purchase.purchaseStatus"
              v-model="approve.result" label="2">拒绝</el-radio>
          </el-form-item>
          <el-form-item>
            <el-button
              :disabled="queue.result=='1' || queue.result=='2' || '-2'==purchaseForm.purchase.purchaseStatus"
              type="primary" icon="el-icon-finished"
              size="mini"
              @click="handleQuery"
              v-has-role="['libraryAdmin']">提交</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="6" style="border-left:1px #666;height: 88vh;overflow-y: auto;">
        <process-preview v-if="showLink" :title="purchaseForm.purchase.title+'-工作流'" :purchase-link="purchaseLink"  />
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import ProcessPreview from "../purchase/ProcessPreview";
  import {getQueue,approve} from "@/api/userApplication/queue";
  import {getPurchase} from "@/api/userApplication/purchase";
  import { treeselect, getDept } from "@/api/system/dept";
  import { listPurchaseSub } from "@/api/userApplication/purchaseSub";
  import { listTemplate,getTemplate } from "@/api/userApplication/template";
  export default {
    name: "handelMatter",
    components: {ProcessPreview},
    props:{
       onlyRead:{
         type:Boolean,
         default:true
       },
      matterId:{
         type:String,
      },
      title:{
         type:String,
        default:""
      }
    },
    data(){
        return{
          purchaseLink:0,
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
              purchaseTemplateName:'',
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
          queue:{},
          approve:{
            id: null,
            groupId: null,
            queueType: null,
            handelPersonal: null,
            purchaseId: null,
            hasRead: null,
            result: null,
            status: 0,
            mark: null,
            createTime: null,
            createPersonal: null,
            updateTime: null,
            updatePersonal: null,
          },

          showLink:false,
        }
    },
    methods:{
      getMatter(){
        getQueue(this.matterId).then(res=>{
          this.queue = res.data;
          this.purchaseLink = this.queue.purchaseId;
          this.getPurchase();
          this.showLink = true;
        });
      },
      getPurchase(){
        this.purchaseForm.loading = true;

        // this.reset();
        // const id = row.id || this.ids;
        getPurchase(this.purchaseLink).then(response => {
          this.purchaseForm.purchase = response.data;
          this.title = "修改采购申请信息";
        }).then(()=>{
          // @TODO 获取部门信息
          this.getPurchaseDeptInfo();//
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

      handleQuery(){
          this.approve.id = this.queue.id;
          this.approve.groupId = this.queue.groupId;
          this.approve.queueType = this.queue.queueType;
          this.approve.handelPersonal = this.queue.handelPersonal;
          this.approve.purchaseId = this.queue.purchaseId;
          this.approve.hasRead = this.queue.hasRead;
          this.approve.handelNode = this.queue.handelNode;
          // this.approve.result = this.queue.result;
          this.approve.status = this.queue.status;
          // this.approve.mark = this.queue.mark;
          this.approve.createTime = this.queue.createTime;
          this.approve.createPersonal = this.queue.createPersonal;
          this.approve.updateTime = this.queue.updateTime;
          this.approve.updatePersonal = this.queue.updatePersonal;

          let msg = this.approve.result == '1'?"同意":"拒绝";
        this.$modal.confirm('确认'+msg+'这个采购申请吗？').then(()=> {
          approve(this.approve).then(_=>{
            this.$modal.msgSuccess("操作成功！");
            this.$emit("submitOperator");
          });
        });

      },
      resetQuery(){

      }

    },
    mounted() {
      this.getMatter();

    },


  }
</script>

<style scoped>

</style>
