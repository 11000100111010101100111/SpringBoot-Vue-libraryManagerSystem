<template>

</template>

<script>
    import Stomp from 'stompjs'
    export default {
      name: 'rabbitmq',
      props:{
        userId:{
          type:String,
        }
      },
      data() {
        return {
          client: null
        }
      },
      created() {
        // this.connect();
      },
      mounted() {
        this.connect();
      },
      methods: {
        onConnected(frame) {
          console.log("Connected: " + frame);
          let exchange = "/purchase_exchange/queue_purchase/purchase_" + this.userId;
          this.client.subscribe(exchange, this.responseCallback, this.onFailed);
          console.log(frame)
        },
        onFailed(frame) {
          console.log("Failed: " + frame);
        },
        responseCallback(frame) {
          console.log("得到的消息 msg=>" + frame.body);
          console.log(frame);
          this.$modal.msgSuccess(frame.body);
          this.$emit("msg",frame.body);
          //接收到服务器推送消息，向服务器定义的接收消息routekey路由purchase_{{userId}}发送确认消息
          this.client.send("/purchase_exchange/queue_purchase/purchase_ack_" + this.userId, {"content-type": "text/plain"}, frame.body);
        },
        connect() {
          this.client = Stomp.client("ws://175.178.127.39:15674/ws");
          let headers = {
            "login": "xjh",
            "passcode": "!xjh13579",
            //虚拟主机，默认“/”
            "host": "/xjh",
            "heart-beat": "0,0"
          };
          this.client.connect(headers, this.onConnected, this.onFailed);
        }
      }
    }
</script>

<style scoped>

</style>
