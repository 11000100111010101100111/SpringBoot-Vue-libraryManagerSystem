<template >

  <view >
    <view>{{date}}</view>
    <view>{{time}}</view>
  </view>

</template>

<script>

  export default {
    name: "timeNow",
    data:{
      date:'',
      timerID:'',
      time:'',
      week:['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
      item:''
    },
    mounted:function(){
      this.updateTime();
      this.timerID = setInterval(this.updateTime, 1000);
    },
    created:function(){

    },

    onLoad:function(option){
      let d=JSON.parse(option.data);
      this.item=d;

    },
    methods:{
      updateTime:function(){
        let cd = new Date();
        this.time = this.zeroPadding(cd.getHours(), 2) + ':' + this.zeroPadding(cd.getMinutes(), 2) + ':' + this.zeroPadding(cd.getSeconds(), 2);
        this.date = this.zeroPadding(cd.getFullYear(), 4) + '-' + this.zeroPadding(cd.getMonth()+1, 2) + '-' + this.zeroPadding(cd.getDate(), 2) + ' ' + this.week[cd.getDay()];

      },
      zeroPadding:function(num, digit){

        let zero = '';
        for(let i = 0; i < digit; i++) {
          zero += '0';
        }
        return (zero + num).slice(-digit);
      }

    }

  }
</script>

<style>

</style>
