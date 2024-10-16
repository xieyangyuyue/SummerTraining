<template>
  <div id="app">
    <router-view></router-view>
    <WeatherTime />
  </div>
</template>

<script>
import WeatherTime from './components/WeatherTime.vue';

export default {
  name: 'App',
  components: {
    WeatherTime
  }
}
</script>

<style>
#app {
  height: 100%;
  position: relative; /* 使子组件的绝对定位相对于 #app */
}
</style>
