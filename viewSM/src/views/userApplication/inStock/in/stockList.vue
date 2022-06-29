<template>
    <div v-loading="inStock.loading">
      <el-table
        class="parent-table"
        v-loading="approvedPurchase.loading"
        :data="approvedPurchase.list"
        @expand-change="expandSelect"
        :row-key="getRowKeys"
        @row-click="clickTable"
        ref="purchaseApprovedTab"
        :expand-row-keys="expands"
        @selection-change="getApprovedPurchaseList">
        <el-table-column type="expand" width="55" align="center" >
          <template slot-scope="slot">
            <el-table :load="loadSub" class="sub-table" v-loading="purchaseSub.loading" :data="purchaseSub.list">
            <el-table-column label="id" align="center" prop="id" v-if="false"/>
            <el-table-column label="图书名称" align="center" prop="entityName" width="200" />
            <el-table-column label="申请数" align="center" prop="purchaseNumber" />
            <el-table-column label="已入库数" align="center" prop="purchaseOverNumber" />
            <el-table-column label="待入库数" align="center" width="180">
              <template slot-scope="scope">
                <span>{{ scope.row.purchaseNumber - scope.row.purchaseOverNumber }}</span>
              </template>
            </el-table-column>
            <el-table-column label="计量单位" align="center" prop="utilName" />
            <el-table-column label="备注" align="center" width="180">
              <template slot-scope="scope">
                <el-tooltip :content="scope.row.remark">
                  <span>{{scope.row.remark.substring(0,5)}}...</span>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="small"
                  type="primary"
                  icon="el-icon-truck"
                  @click="quickilyInStock(scope.row)"
                  v-if="scope.row.purchaseNumber - scope.row.purchaseOverNumber > 0"
                  v-hasPermi="['userApplication:token:edit']"
                >一键入库</el-button>
              </template>
            </el-table-column>
          </el-table>
          </template>
        </el-table-column>
        <el-table-column label="id" align="center" prop="id" v-if="false"/>
        <el-table-column label="入库单名称" align="center" prop="purchase.title" width="500" />
        <el-table-column label="申请部门" align="center" prop="dept.deptName" width="300"/>
        <el-table-column label="申请人" align="center" prop="createPersonal.userName" width="200" />
        <el-table-column label="截止日期" align="center" prop="purchase.termValidity" width="200">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.purchase.termValidity, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="purchase.mark" >
          <template slot-scope="scope">
            <el-tooltip :content="scope.row.purchase.mark" >
              <span>{{scope.row.purchase.mark.substring(0,15)}}{{scope.row.purchase.mark.length<=15?'':'...'}}</span>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="approvedPurchase.total>0"
        :total="approvedPurchase.total"
        :page.sync="approvedPurchase.queryParams.pageNum"
        :limit.sync="approvedPurchase.queryParams.pageSize"
        @pagination="getApprovedPurchaseList"
      />

      <el-dialog
        z-index="1000"
        :title="inStock.title"
        :visible.sync="inStock.visible"
        append-to-body
        destroy-on-close
      :before-close="closeDetial">
        <div>
          <el-divider
            content-position="left">图书基本信息<i style="color: #1ab394;font-size: 14px;margin-left: 5px;" class="el-icon-document-copy"></i></el-divider>
          <el-row>
            <el-col>
              <el-descriptions :column="3">
                <el-descriptions-item label="图书名称">{{inStock.inResult.book.name}}</el-descriptions-item>
                <el-descriptions-item label="图书编码">{{inStock.inResult.book.code}}</el-descriptions-item>
                <el-descriptions-item label="图书版本">{{inStock.inResult.book.version}}</el-descriptions-item>
                <el-descriptions-item label="图书ISBN">{{inStock.inResult.book.isbn}}</el-descriptions-item>
                <el-descriptions-item label="图书系列">{{inStock.inResult.book.series}}</el-descriptions-item>
                <el-descriptions-item label="出版日期">{{inStock.inResult.book.publicationDate}}</el-descriptions-item>
              </el-descriptions>
            </el-col>
            <el-col>

            </el-col>
          </el-row>
          <el-divider
            content-position="left">申请部门基本信息<i style="color: #1ab394;font-size: 14px;margin-left: 5px;" class="el-icon-school"></i></el-divider>
          <el-row>
            <el-col>
              <el-descriptions  :column="4">
                <el-descriptions-item label="部门名称">{{inStock.inResult.approveDept.deptName}}</el-descriptions-item>
                <el-descriptions-item label="管理人员">{{inStock.inResult.approveDept.leader}}</el-descriptions-item>
                <el-descriptions-item label="联系邮箱">{{inStock.inResult.approveDept.email}}</el-descriptions-item>
                <el-descriptions-item label="联系电话">{{inStock.inResult.approveDept.phone}}</el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
          <el-divider
            content-position="left">采购单基本信息<i style="color: #1ab394;font-size: 14px;margin-left: 5px;" class="el-icon-tickets"></i></el-divider>
          <el-row>
            <el-descriptions   :column="4">
              <el-descriptions-item label="待入库图书">{{inStock.inResult.subPurchase.entityName}}</el-descriptions-item>
              <el-descriptions-item label="需入库总数">{{inStock.inResult.subPurchase.purchaseNumber}}</el-descriptions-item>
              <el-descriptions-item label="已入库总数">{{inStock.inResult.subPurchase.purchaseOverNumber}}</el-descriptions-item>
              <el-descriptions-item label="计量单位">{{inStock.inResult.subPurchase.utilName}}</el-descriptions-item>
              <el-descriptions-item label="说明">{{inStock.inResult.subPurchase.remark}}</el-descriptions-item>
            </el-descriptions>
          </el-row>
          <el-row>
            <el-col>
              <el-divider
                v-if="undefined!=inStock.inResult.stockList && null !=inStock.inResult.stockList && inStock.inResult.stockList.length>0"
                content-position="left">本次入库结果<i style="color: #1ab394;font-size: 14px;margin-left: 5px;" class="el-icon-printer"></i></el-divider>
              <el-row :column="4"
                               v-if="undefined!=inStock.inResult.stockList && null !=inStock.inResult.stockList&& inStock.inResult.stockList.length>0">
                <el-col>
                  <el-descriptions :column="2">
                    <el-descriptions-item label="本次入库数">
                      <el-tag type="success" effect="dark">{{inStock.inResult.stockList.length}}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item label="剩余未入库数">
                      <el-tag type="warning" effect="dark">{{inStock.inResult.subPurchase.purchaseNumber - inStock.inResult.stockList.length}}</el-tag>
                    </el-descriptions-item>
                  </el-descriptions>
                </el-col>
              </el-row>
              <el-row  v-if="undefined!=inStock.inResult.stockList && null !=inStock.inResult.stockList && inStock.inResult.stockList.length>0">
                <el-col>
                  <el-table :data="inStock.inResult.stockList" style="max-height: 300px;overflow-y: auto;">
                    <el-table-column label="图像"  align="center" prop="stock.url">
                      <template slot-scope="scope">
                        <el-avatar :src="scope.row.stock.url"/>
                      </template>
                    </el-table-column>
                    <el-table-column label="库位编码" align="center" prop="stock.stockCode">
                      <template slot-scope="scope">
                        <span>{{scope.row.stock.stockCode}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="所在书架层" align="center" prop="storey.name">
                      <template slot-scope="scope">
                        <span>{{scope.row.storey.name}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="所在书架" align="center" prop="shelf.name">
                      <template slot-scope="scope">
                        <span>{{scope.row.shelf.name}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="所属部门" align="center" prop="shelf.name">
                      <template>
                        <span>{{inStock.inResult.approveDept.deptName}}</span>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-col>
              </el-row>

              <el-descriptions title="入库详情" :column="2"
                               v-if="undefined==inStock.inResult.stockList || null ==inStock.inResult.stockList">
                <el-descriptions-item label="本次入库数">
                  <el-tag type="danger" effect="dark">0</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="剩余未入库数">
                  <el-tag type="warning" effect="dark">{{inStock.inResult.subPurchase.purchaseNumber - inStock.inResult.subPurchase.purchaseOverNumber}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="失败说明"><span style="color: #ff3b35;">{{inStock.inResult.msg}}</span></el-descriptions-item>
              </el-descriptions>
              <el-divider
                v-if="undefined==inStock.inResult.stockList || null ==inStock.inResult.stockList"
                content-position="left">缺少符合以下条件的库位<i style="color: #ff3b35;font-size: 14px;margin-left: 5px;" class="el-icon-warning-outline"></i></el-divider>
              <el-descriptions :column="4"
                               v-if="undefined==inStock.inResult.stockList || null ==inStock.inResult.stockList">
                <el-descriptions-item label="相关部门"><el-tag type="danger" effect="dark">{{inStock.inResult.approveDept.deptName}}</el-tag></el-descriptions-item>
                <el-descriptions-item label="存书类别"><el-tag type="danger" effect="dark">{{inStock.inResult.claz.name}}</el-tag></el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import {parentList} from '@/api/system/dept';
  import {approvedPurchaseNode,vo} from '@/api/userApplication/purchase';
  import {purchaseSubList,inStock} from '@/api/userApplication/purchaseSub';
  import Template from "../../template/index";
  // 闲置库位信息
    export default {
        name: "stockList",
      components: {Template},
      data(){
          return{
            //主表唯一索引
            expands: [],
            approvedPurchase:{
              loading:false,
              list:[],
              total:0,
              queryParams:{
                pageNum:10,
                pageSize:1,
              }
            },
            purchaseSub:{
              loading:false,
              list:[],
            },
            inStock:{
              loading:false,
              visible:false,
              title:'',
              inResult:{
                book:{},
                bookFile:[],
                approveDept:{},
                subPurchase:{},
                stockList:[],
                claz:{},
                msg:'',
                status:null,
              },//入库结果信息
            },
            subId:null,
          }
        },
        methods:{
          getApprovedPurchaseList(){
            this.approvedPurchase.loading = true;
            vo().then(res=>{
              this.approvedPurchase.list = res.rows;
              this.approvedPurchase.total = res.total;
              this.approvedPurchase.loading = false;
            }).catch(err=>{
              this.approvedPurchase.list = [];
              this.approvedPurchase.loading = false;
            });
          },
          getApprovedPurchaseSubList(approvedPurchaseId){
            this.purchaseSub.loading = true;
            let query={
              'groupId':approvedPurchaseId,
              'isDel':'0',
              'status':'1'
            };
            purchaseSubList(query).then(res=>{
              this.purchaseSub.list = res.data;
              this.purchaseSub.loading = false;
            }).catch(_=>{
              this.purchaseSub.list = [];
              this.purchaseSub.loading = false;
            });
          },
          /**快速入库**/
          quickilyInStock(row){
            this.inStock.loading = true;
            inStock(row).then(res=>{
              this.inStock.inResult = res.data;
              if(undefined == res.data.subPurchase || null == res.data.subPurchase){
                this.inStock.inResult.subPurchase = row;
              }
              this.inStock.inResult.stockList = this.setUrl(this.inStock.inResult.stockList);
              this.inStock.title = '图书 '+row.entityName+' 的入库结果';
              this.$modal.msgSuccess(res.data.msg);
              this.inStock.visible = true;
              this.inStock.loading = false;
            }).catch(_=>{
              this.$modal.msgWarning("入库失败");
              this.inStock.loading = false;
            });
          },
          loadSub(tree, treeNode, resolve){
            resolve(this.purchaseSub.list);
          },
          // 折叠面板每次只能展开一行
          expandSelect(row, expandedRows) {
            this.purchaseSub.list = [];
            let that = this;
            if (expandedRows.length) {
              that.expands = [];
              if (row) {
                that.expands.push(row.id); // 每次push进去的是每行的ID
              }
            } else {
              that.expands = []; // 默认不展开
            }
            this.subId = row.id;
            this.getApprovedPurchaseSubList(row.id);
          },

          getRowKeys(row) {
            return row.id;
          },
          // 点击行展开事件
          clickTable(row, index, e) {
            this.$refs.purchaseApprovedTab.toggleRowExpansion(row.purchase);
          },

          closeDetial(done){
            this.getApprovedPurchaseSubList(this.subId);
            done();
          },
          setUrl(list){
            if (undefined==list||null==list||list.length<1){
              return list;
            }
            for(let i=0;i<list.length;i++){
              list[i].stock.url = process.env.VUE_APP_BASE_API + list[i].stock.url;
            }
            return list;
          }
        },
        mounted() {
          this.getApprovedPurchaseList();
          // this.parentList();
        }
    }
</script>

<style scoped>

</style>
