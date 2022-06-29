<template>
  <div>
        <flow-panel :purchase="purchase"/>
  </div>
</template>

<script>
  import FlowPanel from '@/components/ef/panel'
  // import FlowDesignTCD from 'flow-design-tcd'
  import { listTemplate,getTemplate } from "@/api/userApplication/template";
  import { listPurchaseNode } from "@/api/userApplication/purchaseNode";
  import NodeTag from "./purchaseLinkBoxSub/nodeTag";
  import Purchase from "./purchaseLinkBoxSub/purchase";
    export default {
      components: {Purchase, NodeTag,FlowPanel},
      props:{
        purchase:{
          type:Object
        },
        isEdit:{
          type:Boolean,
          default:false
        }
      },
      name: "PurchaseLinkBox",
      data(){
        return{
          purchaseLink:{
            purchaseId:null,//申请id
            purchaseEntity:{},//申请信息
            purchaseTemplate:{},//申请模板信息
            purchaseNodes:[
              {
                id:1,//结点id
                name:'提交者',//结点名称
                templateEntity:{
                  id:1,
                  title:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                  mark:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                  createPersonal:1,
                  createTime:'2022-03-03 12:12:00'
                },//审批模板实体
                template:1,//审批模板id
                type:1,//结点类型
                mark:'',//结点备注信息
                status:'1',//结点状态
                isDel:'0',//结点是否删除
                position:{
                  positionX:120,//x轴
                  positionY:20//y轴
                },//结点展示时出现的位置
                createPersonalEntity:{
                  userId:1,
                  userName:'admin',
                  nickName:'肖家海',
                  deptId:103,
                },//结点创建人信息
                createPersonal:1,//结点创建人id
                createTime:'2022-03-03 12:12:00',//结点创建时间
                updatePersonalEntity:{
                  userId:1,
                  userName:'admin',
                  nickName:'肖家海',
                  deptId:103,
                },//结点更新人信息
                updatePersonal:1,//结点更新人id
                updateTime:'2022-03-03 12:12:00',//结点更新人时间
                nextNode:[          {
                  id:1,//结点id
                  name:'提交者',//结点名称
                  templateEntity:{
                    id:1,
                    title:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                    mark:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                    createPersonal:1,
                    createTime:'2022-03-03 12:12:00'
                  },//审批模板实体
                  template:1,//审批模板id
                  type:1,//结点类型
                  mark:'',//结点备注信息
                  status:'1',//结点状态
                  isDel:'0',//结点是否删除
                  position:{
                    positionX:120,//x轴
                    positionY:20//y轴
                  },//结点展示时出现的位置
                  createPersonalEntity:{
                    userId:1,
                    userName:'admin',
                    nickName:'肖家海',
                    deptId:103,
                  },//结点创建人信息
                  createPersonal:1,//结点创建人id
                  createTime:'2022-03-03 12:12:00',//结点创建时间
                  updatePersonalEntity:{
                    userId:1,
                    userName:'admin',
                    nickName:'肖家海',
                    deptId:103,
                  },//结点更新人信息
                  updatePersonal:1,//结点更新人id
                  updateTime:'2022-03-03 12:12:00',//结点更新人时间
                  nextNode:[          {
                    id:1,//结点id
                    name:'提交者',//结点名称
                    templateEntity:{
                      id:1,
                      title:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                      mark:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                      createPersonal:1,
                      createTime:'2022-03-03 12:12:00'
                    },//审批模板实体
                    template:1,//审批模板id
                    type:1,//结点类型
                    mark:'',//结点备注信息
                    status:'1',//结点状态
                    isDel:'0',//结点是否删除
                    position:{
                      positionX:120,//x轴
                      positionY:20//y轴
                    },//结点展示时出现的位置
                    createPersonalEntity:{
                      userId:1,
                      userName:'admin',
                      nickName:'肖家海',
                      deptId:103,
                    },//结点创建人信息
                    createPersonal:1,//结点创建人id
                    createTime:'2022-03-03 12:12:00',//结点创建时间
                    updatePersonalEntity:{
                      userId:1,
                      userName:'admin',
                      nickName:'肖家海',
                      deptId:103,
                    },//结点更新人信息
                    updatePersonal:1,//结点更新人id
                    updateTime:'2022-03-03 12:12:00',//结点更新人时间
                    nextNode:[          {
                      id:1,//结点id
                      name:'提交者',//结点名称
                      templateEntity:{
                        id:1,
                        title:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                        mark:'南昌航空大学科技学院-图书馆图书采购审批流程(试行办法-2022版)',
                        createPersonal:1,
                        createTime:'2022-03-03 12:12:00'
                      },//审批模板实体
                      template:1,//审批模板id
                      type:1,//结点类型
                      mark:'',//结点备注信息
                      status:'1',//结点状态
                      isDel:'0',//结点是否删除
                      position:{
                        positionX:120,//x轴
                        positionY:20//y轴
                      },//结点展示时出现的位置
                      createPersonalEntity:{
                        userId:1,
                        userName:'admin',
                        nickName:'肖家海',
                        deptId:103,
                      },//结点创建人信息
                      createPersonal:1,//结点创建人id
                      createTime:'2022-03-03 12:12:00',//结点创建时间
                      updatePersonalEntity:{
                        userId:1,
                        userName:'admin',
                        nickName:'肖家海',
                        deptId:103,
                      },//结点更新人信息
                      updatePersonal:1,//结点更新人id
                      updateTime:'2022-03-03 12:12:00',//结点更新人时间
                      nextNode:[],//子结点
                      nodeResult:{
                        purchase:{
                          id:1,
                          agoId:1,
                          title:'南航科院-2022年第一批图书新增',
                          approvePersonal:1,
                          approveDept:1,
                          startTime:'2022-03-03 12:12:00',
                          endTime:'2022-04-03 12:12:00',
                          termValidity:'2022-05-03 12:12:00',
                          purchaseTemplate:1,
                          purchaseStatus:'1',
                          mark:'南昌航空大学2022-三月份图书采购计划',
                        },//申请信息
                        approveId:1,//申请id
                        purchaseId:1,//结点id
                        handelPersonal:1,//处理人
                        handelPersonalEntity:{
                          userId:1,
                          userName:'admin',
                          nickName:'肖家海',
                          deptId:103,
                        },//处理人信息
                        handelResult:1,//处理结果类型
                        mark:'null',//备注
                        createPersonal:1,//处理人id
                        createTime:'2022-03-03 12:12:00',//处理时间
                        updatePersonal:1,//更新人id
                        updateTime:'2022-03-03 12:12:00',//更新时间
                      },
                    },],//子结点
                    nodeResult:{
                      purchase:{
                        id:1,
                        agoId:1,
                        title:'南航科院-2022年第一批图书新增',
                        approvePersonal:1,
                        approveDept:1,
                        startTime:'2022-03-03 12:12:00',
                        endTime:'2022-04-03 12:12:00',
                        termValidity:'2022-05-03 12:12:00',
                        purchaseTemplate:1,
                        purchaseStatus:'1',
                        mark:'南昌航空大学2022-三月份图书采购计划',
                      },//申请信息
                      approveId:1,//申请id
                      purchaseId:1,//结点id
                      handelPersonal:1,//处理人
                      handelPersonalEntity:{
                        userId:1,
                        userName:'admin',
                        nickName:'肖家海',
                        deptId:103,
                      },//处理人信息
                      handelResult:1,//处理结果类型
                      mark:'null',//备注
                      createPersonal:1,//处理人id
                      createTime:'2022-03-03 12:12:00',//处理时间
                      updatePersonal:1,//更新人id
                      updateTime:'2022-03-03 12:12:00',//更新时间
                    },
                  },],//子结点
                  nodeResult:{
                    purchase:{
                      id:1,
                      agoId:1,
                      title:'南航科院-2022年第一批图书新增',
                      approvePersonal:1,
                      approveDept:1,
                      startTime:'2022-03-03 12:12:00',
                      endTime:'2022-04-03 12:12:00',
                      termValidity:'2022-05-03 12:12:00',
                      purchaseTemplate:1,
                      purchaseStatus:'1',
                      mark:'南昌航空大学2022-三月份图书采购计划',
                    },//申请信息
                    approveId:1,//申请id
                    purchaseId:1,//结点id
                    handelPersonal:1,//处理人
                    handelPersonalEntity:{
                      userId:1,
                      userName:'admin',
                      nickName:'肖家海',
                      deptId:103,
                    },//处理人信息
                    handelResult:1,//处理结果类型
                    mark:'null',//备注
                    createPersonal:1,//处理人id
                    createTime:'2022-03-03 12:12:00',//处理时间
                    updatePersonal:1,//更新人id
                    updateTime:'2022-03-03 12:12:00',//更新时间
                  },
                },],//子结点
                nodeResult:{
                  purchase:{
                    id:1,
                    agoId:1,
                    title:'南航科院-2022年第一批图书新增',
                    approvePersonal:1,
                    approveDept:1,
                    startTime:'2022-03-03 12:12:00',
                    endTime:'2022-04-03 12:12:00',
                    termValidity:'2022-05-03 12:12:00',
                    purchaseTemplate:1,
                    purchaseStatus:'1',
                    mark:'南昌航空大学2022-三月份图书采购计划',
                  },//申请信息
                  approveId:1,//申请id
                  purchaseId:1,//结点id
                  handelPersonal:1,//处理人
                  handelPersonalEntity:{
                    userId:1,
                    userName:'admin',
                    nickName:'肖家海',
                    deptId:103,
                  },//处理人信息
                  handelResult:1,//处理结果类型
                  mark:'null',//备注
                  createPersonal:1,//处理人id
                  createTime:'2022-03-03 12:12:00',//处理时间
                  updatePersonal:1,//更新人id
                  updateTime:'2022-03-03 12:12:00',//更新时间
                },
              },
            ],
          },
          node:{
            id:null,//结点id
            name:null,//结点名称
            templateEntity:{},//审批模板实体
            template:null,//审批模板id
            type:null,//结点类型
            mark:null,//结点备注信息
            status:null,//结点状态
            isDel:null,//结点是否删除
            createPersonalEntity:{},//结点创建人信息
            createPersonal:null,//结点创建人id
            createTime:null,//结点创建时间
            updatePersonalEntity:{},//结点更新人信息
            updatePersonal:null,//结点更新人id
            updateTime:null,//结点更新人时间
            nextNode:[{}],//下一结点
            nodeResult:{
              purchase:{},//申请信息
              approveId:null,//申请id
              purchaseId:null,//结点id
              handelPersonal:null,//处理人
              handelPersonalEntity:{},//处理人信息
              handelResult:null,//处理结果类型
              mark:null,//备注
              createPersonal:null,//处理人id
              createTime:null,//处理时间
              updatePersonal:null,//更新人id
              updateTime:null,//更新时间
            },
          },

        }
      },
      created() {
        // document.getElementById('panel').append(this.div);
      },
      methods:{

      },
      mounted (){
        const query = {
          purchaseTemplate:this.purchase.purchaseTemplate,
          isDel:'0',
          status:'1',
          pageSize:1000,
          pageNum:1,
        };
        listPurchaseNode(query).then((res)=>{
          // this.purchaseNodes = res.rows;
        });
      }
    }
</script>

<style scoped>

</style>


