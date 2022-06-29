<template>
    <div v-loading="loading">
      <el-divider content-position="left">{{title}}</el-divider>
      <el-timeline v-if="undefined != activities && null != activities && activities.length>0">
        <el-timeline-item
          v-for="(activity, index) in activities"
          :key="index"
          :timestamp="activity.createTime"
          :icon="activity.icon"
          :type="activity.type"
          :color="activity.color"
          :size="activity.size" placement="bottom" :reverse="true">
          <el-card>
            <h4>{{activity.purchaseNodeEntity.name}}</h4>
            <p>
              {{activity.handelPersonalEntity.nickName}} 提交于 {{activity.createTime}}
            </p>
            <el-popover
              placement="left"
              width="400"
              trigger="hover">
              <el-descriptions :title="activity.purchaseNodeEntity.name+'信息'" class="margin-top" :direction="false" :column="1">
                <el-descriptions-item>
                  <template>
                    <el-avatar
                      shape="square"
                      :size="50"
                      fit="scale-down"
                      :src="activity.handelPersonalEntity.avatar"/>
                  </template>
                </el-descriptions-item>
              </el-descriptions>
              <el-descriptions class="margin-top" :column="1" >
                <el-descriptions-item label="用户名">{{activity.handelPersonalEntity.nickName}}</el-descriptions-item>
                <el-descriptions-item label="手机号">{{activity.handelPersonalEntity.phonenumber}}</el-descriptions-item>
                <el-descriptions-item label="邮箱">{{activity.handelPersonalEntity.email}}</el-descriptions-item>
                <el-descriptions-item label="所属部门">
                  {{activity.handelPersonalEntity.dept.deptName}}
                </el-descriptions-item>
                <el-descriptions-item label="处理人角色">
                  <el-tag size="small" style="margin-right: 5px;" :key="i" v-for="(role,i) in activity.handelPersonalEntity.roles">{{role.roleName}}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
              <el-row class="margin-top" >
                <el-col v-if="undefined != activity.mark">
                  <el-divider
                    content-position="left"
                    v-if="undefined != activity.mark">
                    <i :style="'color:'+activity.color"  :class="activity.icon"/>
                    备注</el-divider>
                  <span style="padding-left: 2em;padding-bottom: 1em;">{{activity.mark}}</span>
                </el-col>
              </el-row>
              <el-button
                slot="reference"
                icon="el-icon-more"
                :style="'border-color:'+activity.color+';background-color:'+activity.color"
                type="primary"
                circle/>
            </el-popover>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      <el-divider
        content-position="center">
        <i
          class="el-icon-warning"
          style="color: #1ab394">
          {{undefined == activities || null == activities || activities.length<1?'暂无流程处理信息...':'已经没有更多了'}}
        </i>
      </el-divider>
    </div>
</template>

<script>
  const node_type1 ={type:'primary',size:'large',icon:'el-icon-s-promotion',color: '#0bbd87'};
  const node_type2 ={type:'primary',size:'large',icon:'el-icon-check',color: '#0bbd87'};
  const node_type0 ={type:'primary',size:'large',icon:'el-icon-video-pause',color: '#a2a2a2'};
  const node_type3 ={type:'primary',size:'large',icon:'el-icon-video-pause',color: '#e6b422'};
  const node_type_1 ={type:'primary',size:'large',icon:'el-icon-error',color: '#ff333d'};
  const node_type_2 ={type:'primary',size:'large',icon:'el-icon-warning',color: '#ff7046'};
  const node_type_default ={};
  import {linkNodes} from "@/api/userApplication/link"
  import { appendSrcPiffect } from "@/api/userApplication/file";
  import Template from "../template/index";
    export default {
        name: "ProcessPreview",
      components: {Template},
      props:{
        purchaseLink:{
            type:String,
          },
        title:{
          type:String,
          default:''
        }
      },
      data(){
          return{
            loading:false,
            activities: []
          };

      },
      mounted() {
        // init();
        this.getLink({'approveId':this.purchaseLink});
      },
      created() {

      }
      ,
      methods:{
          getLink(query){
            this.loading = true;
            linkNodes(query).then(res=>{
              this.activities = res.data;
              this.activities = this.createType(this.activities);
              this.loading = false;
            }).catch(()=>{
              this.loading = false;
            });
          },
        setNode(obj,aim){
          aim.type=obj.type;
          aim.size = obj.size;
          aim.icon = obj.icon;
          aim.color = obj.color;
          return aim;
        },
         createType(data){
          for (let i=0;i<data.length;i++){
            data[i].handelPersonalEntity.avatar = appendSrcPiffect( data[i].handelPersonalEntity.avatar);
            switch (data[i].handleResult) {
              case '1':data[i] = this.setNode(node_type1,data[i]);break;
              case '2':data[i] = this.setNode(node_type2,data[i]);break;
              case '3':data[i] = this.setNode(node_type3,data[i]);break;
              case '0':data[i] = this.setNode(node_type0,data[i]);break;
              case '-1':data[i] = this.setNode(node_type_1,data[i]);break;
              case '-2':data[i] = this.setNode(node_type_2,data[i]);break;
              default:data[i] = this.setNode(node_type_default,data[i]);
            }
          }
          return data;
        }
      }
    }
</script>

<style scoped>

</style>
