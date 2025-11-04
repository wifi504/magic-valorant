<template>
  <n-modal
    :show="showModal"
    @after-enter="onModalReady"
  >
    <captcha ref="captchaRef" />
  </n-modal>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const showModal = ref(false)
const captchaRef = ref()

// 用于保存 Promise 的 resolve/reject
let resolver: (value: unknown) => void
let rejecter: (reason?: any) => void

async function doVerify() {
  showModal.value = true
  return new Promise((resolve, reject) => {
    resolver = resolve
    rejecter = reject
  }).finally(() => {
    showModal.value = false
  })
}

// modal 动画结束，captcha 确认渲染完毕
function onModalReady() {
  if (!captchaRef.value) {
    console.warn('captcha组件还未挂载')
    return
  }
  captchaRef.value.doVerify()
    .then(resolver)
    .catch(rejecter)
}

defineExpose({ doVerify })
</script>
