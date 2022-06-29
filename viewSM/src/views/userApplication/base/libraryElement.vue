<template>
  <div>
    <div class="head-container">
      <el-input
        v-model="deptName"
        placeholder="请输入部门名称"
        clearable
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 20px;"
      />
    </div>
<!--    <el-checkbox v-model="expandAll" @change="expandChange">{{expandLabel}}</el-checkbox>-->
    <div class="head-container" :style="autoStyle">
      <el-tree
        v-loading="loading"
        :data="options"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        :icon-class="nodeIcon"
        node-key="id"
        :lazy="lazyLoad"
        ref="selectTree"
        :default-expand-all="expandAll"
        empty-text="暂无数据..."
        @node-click="handleNodeClick"
      />
    </div>
  </div>
</template>

<script>
    import { treeselect } from "@/api/system/dept";
    import { bookclasstreeselect } from "@/api/main/bookClass";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import Template from "../template/index";
    export default {
        name: "selectTree",
        props:{
          treeType:{
            type:String,
            default:'dept'
          },
          expandAll:{
            type:Boolean,
            default:false
          },
          nodeIcon:{
            type:String,
            default:'el-icon-rank'
          },
          lazyLoad:{
            type:Boolean,
            default:false
          },
          autoStyle:{
            type:String,
            default:'max-height: 80vh;overflow-y: auto;'
          }
        },
        components: {Template, Treeselect },
        data(){
            return{
              loading:true,
              expandLabel:'',
              // 部门名称
              name: undefined,
              // 部门树选项
              options: undefined,
              defaultProps: {
                children: "children",
                label: "label"
              },
              currentSelect:{
                curNode:{},//当前选择结点
                curData:{},//当前选择结点数据
                nodes:[],//当前选择结点及其全体父节点
              }
            }
        },
        watch: {
          // 根据名称筛选部门树
          deptName(val) {
            this.$refs.selectTree.filter(val);
          },
          currentSelect:function (value,oldValue) {
            if( ( undefined != value && undefined == oldValue.curNode )
                || (undefined != value && undefined != value.curNode && value.curNode != oldValue.curNode)){
              this.$emit('input', this.currentSelect);
            }
          }
        },
        created() {

        },
        methods:{
          /** 查询部门下拉树结构 */
          getTreeselect() {

            this.loading = true;
            switch (this.treeType) {
              case 'dept':
                treeselect().then(response => {
                  this.options = response.data;
                  this.loading = false;
                }).catch(()=>{
                  this.loading = false;
                });
                break;
              case 'bookType':
                bookclasstreeselect().then(response => {
                  this.options = response.data;
                  this.loading = false;
                }).catch(()=>{
                  this.loading = false;
                });
                break;
            }

          },
          // 筛选部门树节点
          filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
          },
          // 部门树节点单击事件
          handleNodeClick(data,node) {
            //获取当前结点全体祖先结点信息
            let curNode = node;
            let isStart = true;
            let selectData = {
              curData:data,
              curNode:node,
              nodes:[]
            };
            while(undefined != curNode ){
              if (undefined != curNode.data.id) {
                selectData.nodes.push(curNode.data);
              }
              if (isStart){
                isStart = false;
              }
              curNode = curNode.parent;
            }

            this.currentSelect = selectData;

            //返回自定义事件selectDept，提供回调，返回选择结点结果
            this.$emit('selectNode',this.currentSelect);
          },
          expandChange(){
            this.expandAll = !this.expandAll;
            this.expandLabel = this.expandAll?'已展开':'已折叠';
          },
          expand(){
            let treeList = this.options;
            for (let i = 0; i < treeList.length; i++) {
              this.$refs.selectTree.store.nodesMap[treeList[i].id].expanded = this.expandAll;
            }
          }
        },
        mounted (){
          this.getTreeselect();//初始化，获取树结点信息
          this.expand();
        }
    }
</script>

<style scoped>

</style>
