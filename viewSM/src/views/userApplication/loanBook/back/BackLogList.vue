<template>
  <div>
    <el-row>
      <el-divider content-position="left" v-if="undefined!=data&&null!=data">
        共
        <el-tag effect="dark" type="info" style="margin: 5px;">{{data.length}}</el-tag>
        条归还记录
      </el-divider>
      <el-divider content-position="left" v-if="undefined==data||null==data">
        <el-tag effect="dark" type="danger" style="margin: 5px;">暂无归还记录</el-tag>
      </el-divider>
      <el-col :span="6">
        <div class="block" style="max-height: 72vh;min-height: 72vh;overflow-y: auto;">
          <el-timeline>
            <el-timeline-item
              v-for="(log,index) in data"
              :timestamp="log.backTime" placement="top">
              <el-card>
                <h4>
                  <el-tag
                    type="success"
                    style="margin-right: 5px;"
                    effect="dark">{{(1+index)}}</el-tag>
                  {{log.bookName}}</h4>
                <p>{{log.userName}}-- 归还图书</p>
                <el-button type="primary"  style="float: right;" @click="showLog(log)">查看</el-button>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-col>
      <el-divider direction="vertical"/>
      <el-col span="18">
        <div>
          <el-row>
            <el-col>
              <el-divider content-position="left" ><el-avatar style="margin-left: 10px;" :src="mine.avatar"/><sex-label :sex="mine.sex"/></el-divider>
              <el-descriptions class="margin-top" :column="2" border>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-user"></i>
                    我的账号
                  </template>
                  {{mine.userName}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    我的名称
                  </template>
                  {{mine.nickName}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    联系邮箱
                  </template>
                  {{mine.email}}
                </el-descriptions-item>
                <el-descriptions-item>
                  <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    联系电话
                  </template>
                  {{mine.phonenumber}}
                </el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
          <el-divider content-position="left">详细信息</el-divider>

          <el-empty
            :description="undefined!=data&&null!=data&&data.length>0?'暂无归还记录':'请选择一条归还记录'"
            v-if="undefined == showData || null == showData"/>
          <el-row  v-if="undefined != showData && null != showData">
            <el-col>
              <el-descriptions
                class="margin-top"
                :column="1"
                :title="showData.bookName" >
                <template slot="extra">
                  <el-button type="primary" size="small" @click="deleteLog(showData.id)">删除记录</el-button>
                </template>
                <el-descriptions-item label="图书名称" label-style="font-size: 16px;margin-left:20px;margin-bottom:20px;">
                  <span style="font-size: 16px;">{{showData.bookName}}</span>
                </el-descriptions-item>
                <el-descriptions-item label="借书时间" label-style="font-size: 16px;margin-left:20px;margin-bottom:20px;">
                 <span style="font-size: 16px;"> {{showData.createTime}}</span>
                </el-descriptions-item>
                <el-descriptions-item label="归还时间" label-style="font-size: 16px;margin-left:20px;margin-bottom:20px;">
                  <span style="font-size: 16px;">{{showData.backTime}}</span>
                </el-descriptions-item>
                <el-descriptions-item label="累计时长" label-style="font-size: 16px;margin-left:20px;margin-bottom:20px;">
                  <el-tag type="success" effect="dark">{{showData.totalTime}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="存书位置" label-style="font-size: 16px;margin-left:20px;margin-bottom:20px;">
                  <span style="font-size: 16px;">-</span>
                </el-descriptions-item>
              </el-descriptions>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { listHistory,getMyHistory, getBackHistory,id, delHistory, addHistory, updateHistory } from "@/api/userApplication/history";
  import {getUserProfile} from '@/api/system/user';
  import SexLabel from "../../base/SexLabel";
  import UserAvatar from "../../../system/user/profile/userAvatar";
  import Picture from "../../base/Picture";
export default {
        name: "BackLogList",
  components: {Picture, UserAvatar, SexLabel},
  data(){
          return{
            data:[],
            queryParams:{
              pageNum: 1,
              pageSize: 10000,
              userId: null,
              bookId: null,
              stockId: null,
              backTime: null,
              hasBack: '1',
              status: '1',
              isDel: '0',
              createPersonal: null,
              updatePersonal: null,
            },
            showData:null,
            mine:{}
          }
      },created() {
        this.getList();
      },

      methods:{
        showLog(log){
          this.$modal.msgWarning(log.bookName);
          // this.showData = log;
          id({'id':log.id}).then(res=>{
            this.showData = res.data;
          });
        },

        /** 查询借书信息列表 */
        getList() {
          getBackHistory(this.queryParams).then(response => {
            this.data = response.data;
            this.showData = null;
          });
          getUserProfile().then(res=>{
            this.mine = res.data;
            this.mine.avatar =  process.env.VUE_APP_BASE_API + this.mine.avatar;
          });
        },

        deleteLog(id){
          this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            delHistory(id).then(res=>{
              this.$notify({
                title: '成功',
                message: '归还记录删除成功！',
                type: 'success'
              });

              this.getList();
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        }
      }
    }
</script>

<style scoped>

</style>
