<template>
  <div>
    首页
    <div>部署路径：{{ baseUrl }}</div>
    <div>服务端接口：{{ requestBaseUrl }}</div>
    <div>服务端测试：{{ res }}</div>
    <div>当前提交SHA：{{ gitSha }}</div>
    <div>版本：{{ version }}</div>
    <n-button @click="router.push('/dashboard')">
      转到控制台
    </n-button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { request } from '@/service/request-service.ts'

const requestBaseUrl = import.meta.env.VITE_REQUEST_BASE_URL
const gitSha = import.meta.env.VITE_GIT_SHA
const version = import.meta.env.VITE_VERSION
const baseUrl = import.meta.env.VITE_BASEURL

const router = useRouter()

const res = ref()
request.get('/login', {
  retry: 10,
  retryDelay: 1000,
  pendingResult: '正在请求...',
  errorResult: (attempt: number) => {
    if (attempt > 0) {
      return `正在执行第${attempt}次重试...`
    }
    return '请求失败'
  },
  assert: (resultData: any) => resultData.code === 200,
  resultRef: res,
})
</script>

<style scoped>

</style>
