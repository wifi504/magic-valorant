<template>
  <background-container :bg-opacity="0.25">
    <nut-button @tap="doRequest">
      微信一键登录
    </nut-button>
  </background-container>
</template>

<script setup lang="ts">
import Taro from '@tarojs/taro'

async function doRequest() {
  const codeRes = await Taro.login()
  console.log(codeRes.code)
}
</script>

<style module lang="less">

</style>
