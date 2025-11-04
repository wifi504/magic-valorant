<template>
  <image :src="targetSrc" mode="aspectFill" />
</template>

<script setup lang="ts">
import { ref } from 'vue'

const targetSrc = ref('')

// TODO 异步加载图片
</script>

<style scoped lang="less">

</style>
