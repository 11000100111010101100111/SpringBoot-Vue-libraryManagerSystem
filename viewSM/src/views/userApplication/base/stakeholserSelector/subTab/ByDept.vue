<template>
  <div class="app-container">
    <el-row type="flex">
      <el-col style="width: 25%">
        <!--部门数据-->
        <SelectTree v-on:selectNode="selectDept" style="max-height: 55vh;overflow: hidden"/>
      </el-col>
      <el-col v-loading="loading" style="width: 71%" >
        <el-divider content-position="left" style="margin: 20px;">
          <el-tag style="margin-right: 1px;" v-for="tag in curNodeLink" :key="tag" :type="tag.type" effect="dark">{{tag.label}}</el-tag>
        </el-divider>
        <selected :checked-list="checkedList" v-on:selected="handleCheckedChange" v-if="chiose"
                  :stakeholser-list="stakeholserList" :loading="loading"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>


  import {selectByDept} from "@/api/system/user"
  import SelectTree from "../../libraryElement";
  import Selected from "./select";
    export default {
      props:{
        checkedList:{
          type:Array,
          default:[]
        },
        deptTree:{
          type:Object
        }
      },
        name: "ByDept",
      components: {Selected, SelectTree},
      comments:[],
        data(){
            return{
              loading:false,
              // checkList:[],
              stakeholserList:[],
              curNodeLink:[],
              chiose:false,
            }
        },
        methods:{
          getUserList(query){
            this.loading = true;
            selectByDept(query).then(res=>{
              this.stakeholserList = res.data;
              this.chiose  =true;
              this.loading = false;
            });
          },
          // 部门树节点单击事件
          selectDept(selectData) {
            this.curNodeLink = [];
            let query={
              'deptId' : selectData.curData.id,
            };
            //获取当前结点全体祖先结点信息
            let isStart = true;
            if(undefined != selectData.nodes ) {
              for (let i = 0; i < selectData.nodes.length; i++) {
                let node = selectData.nodes[i];
                if (undefined != node.id) {
                  this.curNodeLink.push({
                    'id': node.id,
                    'label': node.label,
                    'type': isStart ? 'warning' : 'success'
                  });
                }
                if (isStart) {
                  isStart = false;
                }
              }
            }
            this.getUserList(query);
          },
          handleCheckedChange(value) {
            this.$emit('selected',value)
          }
        }
    }
</script>

<style scoped>

</style>
