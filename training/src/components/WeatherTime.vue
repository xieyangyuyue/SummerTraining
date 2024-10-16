<template>
    <div class="weather-time">
      <div class="weather">
        <span>天气: {{ weather }} 宜昌</span>
      </div>
      <div class="time">
        <span>时间: {{ currentTime }}</span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'WeatherTime',
    data() {
      return {
        weather: '晴天', // 这里可以用API动态获取天气信息
        currentTime: new Date().toLocaleTimeString()
      };
    },
    mounted() {
      // 每秒更新一次时间
      this.timeInterval = setInterval(() => {
        this.currentTime = new Date().toLocaleTimeString();
      }, 1000);
    },
    beforeDestroy() {
      clearInterval(this.timeInterval);
    }
  }
  </script>
  
  <style scoped>
  .weather-time {
    position: fixed;
    bottom: 10px;
    left: 10px;
    background: rgba(0, 0, 0, 0.5); /* 半透明背景 */
    color: #fff;
    padding: 10px;
    border-radius: 5px;
    font-size: 14px;
  }
  .weather-time .weather,
  .weather-time .time {
    margin-bottom: 5px;
  }
  </style>
  