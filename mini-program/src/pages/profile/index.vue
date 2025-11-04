<template>
  <background-container>
    <profile-card />
    <nut-button @tap="login.doLogin()">
      微信一键登录
    </nut-button>
  </background-container>
</template>

<script setup lang="ts">
import ProfileCard from '@/pages/profile/components/ProfileCard.vue'
import login from '@/service/login'
</script>

<style module lang="less">

</style>
