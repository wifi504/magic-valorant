<template>
  <n-config-provider :theme-overrides="themeOverrides">
    <n-message-provider>
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </n-message-provider>
  </n-config-provider>
</template>

<script setup lang="ts">
import { onBeforeMount } from 'vue'
import themeOverrides from '@/assets/style/naive-ui-theme-overrides.json'
import { injectAllTheme } from '@/utils/global-theme.ts'

// 全局注入CSS变量
onBeforeMount(() => {
  injectAllTheme()
})
</script>
