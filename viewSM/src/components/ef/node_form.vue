<template>
    <div>
        <div class="ef-node-form">
            <div class="ef-node-form-header">
                编辑
            </div>
            <div class="ef-node-form-body">
                <el-form
                  :model="node"
                  ref="dataForm"
                  label-width="80px"
                  v-show="type === 'node'"
                  style="max-height: 60vh;overflow: auto;"
                >
                    <el-form-item label="类型" v-if="false">
                        <el-input :readonly="!isEdit" v-model="node.type" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="结点名称">
                        <el-input :readonly="!isEdit" v-model="node.name" required placeholder="输入结点名称..."></el-input>
                    </el-form-item>
                  <el-form-item label="干系人">
                    <StakeholderInput :isEdit="isEdit" :user-list="node.user" :ids="node.userId" v-on:addStakeholder="getStakeholser"/>
<!--                    <el-input v-model="node.userName" required @focus="getStakeholser" ref="stakeholserInput" placeholder="单击选择结点相关干系人..."></el-input>-->
                  </el-form-item>
                  <el-form-item label="备注">
                    <el-input :readonly="!isEdit" type="textarea" :rows="5" v-model="node.mark" placeholder="输入备注信息..."></el-input>
                  </el-form-item>
                    <el-form-item :readonly="!isEdit" label="left坐标" v-if="false">
                        <el-input v-model="node.left" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item :readonly="!isEdit" label="top坐标" v-if="false">
                        <el-input v-model="node.top" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item  :readonly="!isEdit" label="ico图标" v-if="false">
                        <el-input v-model="node.ico"></el-input>
                    </el-form-item>
                    <el-form-item :readonly="!isEdit" label="状态" v-if="false">
                        <el-select v-model="node.state" placeholder="请选择">
                            <el-option
                                    v-for="item in stateList"
                                    :key="item.state"
                                    :label="item.label"
                                    :value="item.state">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="isEdit">
                        <el-button icon="el-icon-close">重置</el-button>
                        <el-button type="primary" icon="el-icon-check" @click="save">保存</el-button>
                    </el-form-item>
                </el-form>

                <el-form :model="line" ref="dataForm" label-width="80px" v-show="type === 'line'">
                    <el-form-item label="条件">
                        <el-input :readonly="!isEdit" v-model="line.label"></el-input>
                    </el-form-item>
                    <el-form-item label="类型" required>
                      <el-radio :disabled="!isEdit" v-model="line.audioType" label="1" >通过</el-radio>
                      <el-radio :disabled="!isEdit" v-model="line.audioType" label="2" >拒绝</el-radio>
                    </el-form-item>
                    <el-form-item v-if="isEdit">
                        <el-button icon="el-icon-close" >重置</el-button>
                        <el-button type="primary" icon="el-icon-check" @click="saveLine">保存</el-button>
                    </el-form-item>
                </el-form>
            </div>
<!--                        <div class="el-node-form-tag"></div>-->
        </div>

      <el-dialog :title="stakeholser.title" append-to-body :visible.sync="stakeholser.visible" width="1000px">

<!--        <el-drawer-->
<!--          :title="stakeholser.title"-->
<!--          :visible.sync="stakeholser.visible"-->
<!--          direction="ttb"-->
<!--          style="z-index: 99"-->
<!--          size="90%">-->
          <stakeholser-selector :checked-list="node.userId" v-on:selected="selectStakeholser"/>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitStakeholser">确 定</el-button>
          </div>
<!--        </el-drawer>-->
      </el-dialog>
    </div>
</template>

<script>
    import { cloneDeep } from 'lodash'
    import StakeholserSelector from "../../views/userApplication/base/stakeholserSelector/stakeholserSelector";
    import {users} from "@/api/system/user"
    import StakeholderInput from "../../views/userApplication/base/stakeholserSelector/subTab/StakeholderInput";
    export default {
      props:{
        isEdit:{
          type:Boolean,
          default:true
        }
      },
      components: {StakeholderInput, StakeholserSelector},
      data() {
            return {
                stakeholser:{
                  visible:false,
                  title:'',
                },
                visible: true,
                // node 或 line
                type: 'node',
                node: {},
                line: {
                  audioType:null,
                  label:''
                },
                data: {},
                stateList: [{
                    state: 'success',
                    label: '成功'
                }, {
                    state: 'warning',
                    label: '警告'
                }, {
                    state: 'error',
                    label: '错误'
                }, {
                    state: 'running',
                    label: '运行中'
                }]
            }
        },
        methods: {
            /**
             * 表单修改，这里可以根据传入的ID进行业务信息获取
             * @param data
             * @param id
             */
            nodeInit(data, id) {
                this.type = 'node';
                this.data = data;
                data.nodeList.filter( (node) => {
                    if (node.id === id) {
                        this.node = cloneDeep(node);
                      // this.getUserByIds(node.userId);
                      //
                      // this.getStakeholser();
                      // this.stakeholser.visible = false;
                    }
                });
            },
            lineInit(line) {
                this.type = 'line';
                this.line = line
            },
            // 修改连线
            saveLine() {
                this.$emit('setLineLabel', this.line)
            },
            save() {
                this.data.nodeList.filter((node) => {
                    if (node.id === this.node.id) {
                        node.name = this.node.name;
                        node.left = this.node.left;
                        node.user=this.node.user;
                        node.userId=this.node.userId;
                        node.top = this.node.top;
                        node.ico = this.node.ico;
                        node.state = this.node.state;
                        this.$emit('repaintEverything');
                      this.$modal.msgSuccess('保存成功!');
                    }
                })
            },
          getStakeholser(){
              if(undefined == this.node || undefined == this.node.name){
                this.$modal.msgError("请先在画布中选择一个流程结点才能操作");
                return;
              }
            this.stakeholser.title = "选择结点["+this.node.name+']的相关干系人';
            this.stakeholser.visible = true;
          },
          //选择干系人
          selectStakeholser(stakeholser){
              let userId = [];
              for (let i = 0; i < stakeholser.length; i++) {
                userId.push(stakeholser[i]);
              }
              this.node.userId = userId;
              this.getUserByIds(userId);
          },
          submitStakeholser(){
            this.stakeholser.visible = false;
          },

          getUserByIds(ids){//ids  Array
            users(ids).then(res=>{
              this.node.user = res.data;
              // let nickName = [];
              // for (let i = 0; i < this.node.user.length; i++) {
              //   nickName.push(this.node.user[i].nickName);
              //   // this.node.userName.push(this.node.user[i].nickName);
              // }
              // this.node.userName = nickName.join(',');
            });
          }

        }
    }
</script>

<style>
    .el-node-form-tag {
        position: absolute;
        top: 50%;
        margin-left: -15px;
        height: 40px;
        width: 15px;
        background-color: #fbfbfb;
        border: 1px solid rgb(220, 227, 232);
        border-right: none;
        z-index: 0;
    }
</style>
