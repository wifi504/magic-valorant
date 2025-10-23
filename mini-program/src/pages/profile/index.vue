<template>
  <background-container :bg-opacity="0.25">
    123 <br>
    <view v-for="i in count" :key="i">
      这里有很多条数据，这是第{{ i }}条
    </view>
    <view>
      <nut-button @tap="doRequest">
        请求10条数据
      </nut-button>
    </view>
  </background-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const count = ref(0)

function doRequest() {
  count.value += 10
}
</script>

<style module lang="less">

</style>
