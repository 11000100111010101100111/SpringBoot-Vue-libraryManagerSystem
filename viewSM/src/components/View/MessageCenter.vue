<template>
  <div>
    <rabbitmq v-on:msg="getMsg" :user-id="1"/>
    <el-dropdown trigger="hover">
      <svg-icon icon-class="message" @click="goto" @mouseover="showMsg" class="el-dropdown-link" />
      <el-dropdown-menu slot="dropdown"
                        style="box-shadow: 0 0 5px #666;"
                        v-if="undefined != item && null !=item && item.length>0">
        <el-dropdown-item v-for="obj in item" class="clearfix" >
          {{obj.title}}
          <el-badge class="mark" :value="obj.number" :type="obj.type"/>
        </el-dropdown-item>
      </el-dropdown-menu>
      <el-dropdown-menu  slot="dropdown"
                         style="box-shadow: 0 0 5px #666;"
                         v-if="undefined == item || null ==item || item.length<1">
        <el-dropdown-item class="clearfix" >
          <i v-if="loading" class="el-icon-loading" />
          <span v-if="loading" style="font-size: 12px;color:#aaa;">正在加载...</span>

          <i v-if="!hasValue" class="el-icon-info" />
          <span  v-if="!hasValue"style="font-size: 12px;color:#aaa;">暂无消息...</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
  import {send} from '@/api/userApplication/queue';
  import { getUserProfile } from "@/api/system/user";
  import Rabbitmq from "../Mq/rabbitmq";
  export default {
    name: 'MessageCenter',
    components: {Rabbitmq},
    data() {
      return {
        userId: -1,
        url: '#',
        loading: false,
        hasValue: true,
        item: [
          // {'title':'待办','number':'10','type':'warning'},
          // {'title':'新消息','number':'5','type':'danger'},
        ],


        socket: null,
        // userId:localStorage.getItem("ms_uuid"),
        toUserId: '2',
        content: '3'
      }
    },
    methods: {
      goto() {
        window.open(this.url)
      },
      showMsg() {
        send();
        this.loading = true;
        // this.sendMessage();
        // this.openSocket();
        // setInterval(function () {
        this.loading = false;
        this.hasValue = false;
        // },1.5);
      },
      getMsg(msg){
        this.$modal.msgSuccess(msg);
      }
      // openSocket() {
      //   if (typeof WebSocket == "undefined") {
      //     this.$modal.msgError("不支持")
      //     // console.log("您的浏览器不支持WebSocket");
      //   } else {
      //     // console.log("您的浏览器支持WebSocket");
      //     //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
      //     //等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
      //     //var socketUrl="${request.contextPath}/im/"+$("#userId").val();
      //     var socketUrl =
      //       "http://localhost:8080/imserver/" + this.userId;
      //     socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
      //     // console.log(socketUrl);
      //     if (this.socket != null) {
      //       this.socket.close();
      //       this.socket = null;
      //     }
      //     // this.socket = new WebSocket(socketUrl);
      //     //打开事件
      //     this.socket = new WebSocket(socketUrl);
      //     //打开事件
      //     this.socket.onopen = function () {
      //       this.$modal.msgSuccess("websocket已打开");
      //       // console.log("websocket已打开");
      //       this.socket.send("这是来自客户端的消息" + location.href + new Date());
      //     };
      //     //获得消息事件
      //     this.socket.onmessage = function (msg) {
      //       this.$modal.msgSuccess(msg.data);
      //       // console.log(msg.data);
      //       //发现消息进入    开始处理前端触发逻辑
      //     };
      //     //关闭事件
      //     this.socket.onclose = function () {
      //       this.$modal.msgSuccess("websocket已关闭...");
      //       // console.log("websocket已关闭");
      //     };
      //     //发生了错误事件
      //     this.socket.onerror = function () {
      //       this.$modal.msgSuccess("websocket发生了错误...");
      //       // console.log("websocket发生了错误");
      //     };
      //   }
      // },
      // sendMessage() {
      //   if (typeof WebSocket == "undefined") {
      //     // console.log("您的浏览器不支持WebSocket");
      //   } else {
      //     // console.log("您的浏览器支持WebSocket");
      //     // console.log(
      //     //   '{"toUserId":"' +
      //     //   this.toUserId +
      //     //   '","contentText":"' +
      //     //   this.content +
      //     //   '"}'
      //     // );
      //     this.socket.send(
      //       '{"toUserId":"' +
      //       this.toUserId +
      //       '","contentText":"' +
      //       this.content +
      //       '"}'
      //     );
      //   }
      // }
    },
    created() {
      getUserProfile().then(res => {
        this.userId = res.data.userId;
        // this.openSocket();
      });
    },
    mounted() {
      getUserProfile().then(res => {
        this.userId = res.data.userId;
        // this.openSocket();
      });
    }
  }
</script>
