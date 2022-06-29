
<template>

  <div>
    <div id="top_panel">
      <el-button-group>
        <el-button @click="appendNewStartNode">开始结点</el-button>
        <el-button @click="appendNewNode">过程结点</el-button>
        <el-button @click="appendNewSomeNode">分支结点</el-button>
        <el-button @click="appendNewendNode">结束结点</el-button>
      </el-button-group>
    </div>
    <div style="
         position: relative;
         min-width: 100px;
         min-height: 500px;
         border-radius: 5px;
         box-shadow: 0 0 5px #aaa;" id="panel_nodes">
          <div
               v-for="(node,index) in purchaseNode" :key="node"
               :style="'display:inline-block;position:absolute;left:'+node.position.positionX+'px;top:'+node.position.positionY+'px;'"
               class="but-nodeTtem"
               v-drag
               @mouseover="hoverNode($event,index)"
               @mouseleave="leaveNode($event,index)">{{node.name}}</div>
    </div>
  </div>
</template>

<script>
    export default {
      props:{
        isEdit:{
          type:Boolean,
          default:false
        },
        tagValue:{
          type:Object
        }
      },
        name: "nodeTag",
      data(){
        return{
          purchaseNode:[],
          show:[],
          visible:false,
          purchaseLink:{
            purchaseId:null,//申请id
            purchaseEntity:{},//申请信息
            purchaseTemplate:{},//申请模板信息
            purchaseNodes:
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
                nextNode:          {
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
                    positionX:200,//x轴
                    positionY:100//y轴
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
                  nextNode:         {
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
                      positionX:400,//x轴
                      positionY:30//y轴
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
                    nextNode:         {
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
                        positionX:150,//x轴
                        positionY:60//y轴
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
                      nextNode:null,//子结点
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
                    },//子结点
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
                  },//子结点
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
                },//子结点
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
          },
        }
      },
      methods:{
        appendNewStartNode(){
          document.getElementById("panel_nodes").appendChild("<div\n" +
            "               v-for=\"(node,index) in purchaseNode\" :key=\"node\"\n" +
            "               :style=\"'display:inline-block;position:absolute;left:'+node.position.positionX+'px;top:'+node.position.positionY+'px;'\"\n" +
            "               class=\"but-nodeTtem\"\n" +
            "               v-drag\n" +
            "               @mouseover=\"hoverNode($event,index)\"\n" +
            "               @mouseleave=\"leaveNode($event,index)\">{{node.name}}</div>")
        },
        appendNewNode(){

        },
        appendNewSomeNode(){

        },
        appendNewEndNode(){

        },
        //最佳新结点
        appendNewNext(){

        },
        //选择下一结点
        chioseNext(){

        },
        //编辑当前结点
        editThis(){

        },
        //移除当前结点
        removeThis(){

        },
        hoverNode(event,index){
          this.show[index] = true;
        },
        leaveNode(event,index){
          this.show[index] = false;
        },

        // 找到子元素在父元素中的相对位置
        getElementTop(element){
            let el = (typeof element == "string") ? document.getElementById(element) : element;

            if (el.parentNode === null || el.style.display == 'none') {
              return false;
            }

            return el.offsetTop - el.parentNode.offsetTop;
        }
      },
      mounted() {
        this.purchaseNode = [];
        let index = 0;
        let curNode = this.purchaseLink.purchaseNodes;
        while(undefined != curNode && null != curNode){
          this.purchaseNode.push(curNode);
          this.show[index] = false;
          curNode = curNode.nextNode;
        }
      },
      // 自定义指令 实现可拖动
      directives: {
        drag(el, bindings) {
          el.onmousedown = function(e) {
            let disx = e.pageX - el.offsetLeft;
            let disy = e.pageY - el.offsetTop;

            let parent = document.getElementById("panel_nodes");
            let topItem = document.getElementById("top_panel");

            let leftBound = parent.offsetLeft - 20;
            let rightBound = parent.offsetWidth + leftBound - 25;
            let topBound = parent.offsetTop - topItem.offsetTop -35 ;
            let bottomBound = parent.offsetHeight+topBound -30;


            document.onmousemove = function (e) {
              let x = e.pageX - disx;
              let y = e.pageY - disy;

              if(x>=rightBound){
                el.style.left = rightBound+'px';
              }else if(x<=leftBound){
                el.style.left = leftBound+'px';
              }else {
                el.style.left = x + 'px';
              }

              if(y>=bottomBound){
                el.style.top = bottomBound+'px';
              }else if(y<=topBound){
                el.style.top = topBound+'px';
              }else {
                el.style.top = y + 'px';
              }
            };
            document.onmouseup = function() {
              document.onmousemove = document.onmouseup = null;
            }
          }
        }
      }
    }
</script>

<style scoped>
  .groupBut{
    margin: 1px;
  }
  .but-nodeTtem{
    border-radius: 3px;
    border: 1px solid #1ab394;
    padding: 5px;
    font-weight: bold;
    font-size: 14px;
    color: #fff;
    background-color: #1ab394;
    cursor: pointer;
  }
</style>
