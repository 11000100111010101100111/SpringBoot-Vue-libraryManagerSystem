<template>

</template>

<script>
    export default {
        name: "mq"
    }
    // import Stomp from 'stompjs'
    //
    // let client = Stomp.client('ws://127.0.0.1:15674/ws')
    // // 账号密码
    // var headers = {
    //   'login': 'admin',
    //   'passcode': '123456'
    // }
    // client.connect(headers, (frame) => {
    //   // exchange 固定  press_exchange是后台定义的交换机的名称  userId 当前登录用户的ID
    //   state.client.subscribe('/exchange/press_exchange/' + userId, (frame1) => {
    //     // 获取消息
    //     const body = JSON.parse(frame1.body)
    //     // 获取消息后的操作
    //   }, (frame) => {
    //     console.log('通讯连接失败，将无法收到信息！')
    //   })
    // }, (frame) => {
    //   console.log('通讯连接失败，将无法收到信息！')
    // })
    //
    // // 断开连接
    // client.disconnect()
</script>

<style scoped>

</style>
