<template>
  <view class="index">
    <nut-button type="primary" @click="onClick">
      按一下按钮
    </nut-button>
    <nut-toast v-model:visible="show" msg="按钮被成功按下了" />
  </view>
</template>

<script setup>
import { ref } from 'vue'

const show = ref(false)
function onClick() {
  show.value = true
}
</script>
