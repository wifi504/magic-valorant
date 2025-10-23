<!-- 具备背景图填充的包裹组件 -->
<template>
  <view
    :class="$style.page" :style="{
      '--bgc-color': light ? `rgba(255, 255, 255, ${bgOpacity})` : `rgba(0, 0, 0, ${bgOpacity})`,
      '--text-color': light ? '#000' : '#fff',
    }"
  >
    <scroll-view :class="$style.content" :scroll-y="true">
      <!-- 页面实际内容 -->
      <slot />
    </scroll-view>
  </view>
</template>

<script setup lang="ts">
withDefaults(defineProps<{
  bgOpacity?: number
  light?: boolean
}>(), {
  bgOpacity: 0,
  light: false,
})
</script>

<style module lang="less">
.page {
  --bgc-color: none;
  --text-color: none;
  position: relative;
  width: 100%;
  min-height: 100vh;
  background-image: url('@/assets/image/background-page.jpg');
  background-size: cover;
  background-position: 50% calc(100% - 135px);
  background-repeat: no-repeat;
  background-attachment: fixed;

  .content {
    position: relative;
    z-index: 1;
    background-color: var(--bgc-color);
    color: var(--text-color);
    width: 100%;
    height: calc(100vh - (150px + 32px));
  }
}
</style>
