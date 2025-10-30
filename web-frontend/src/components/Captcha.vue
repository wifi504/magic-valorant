<template>
  <div>
    <n-button @click="btnClick">
      加载验证码
    </n-button>
    <div id="captcha-box" />
  </div>
</template>

<script setup lang="ts">
import { useTAC } from '@/utils/captcha-util.ts'

async function btnClick() {
  await useTAC()
  login()
}

function login() {
  // config 对象为TAC验证码的一些配置和验证的回调
  const config = {
    // 生成接口 (必选项,必须配置, 要符合tianai-captcha默认验证码生成接口规范)
    requestCaptchaDataUrl: 'http://localhost:8080/api/public/captcha/gen',
    // 验证接口 (必选项,必须配置, 要符合tianai-captcha默认验证码校验接口规范)
    validCaptchaUrl: 'http://localhost:8080/api/public/captcha/check',
    // 验证码绑定的div块 (必选项,必须配置)
    bindEl: '#captcha-box',
    // 验证成功回调函数(必选项,必须配置)
    validSuccess: (res, c, tac) => {
      // 销毁验证码服务
      tac.destroyWindow()
      console.log('验证成功，后端返回的数据为', res)
      // 调用具体的login方法
      login(res.data.token)
    },
    // 验证失败的回调函数(可忽略，如果不自定义 validFail 方法时，会使用默认的)
    validFail: (res, c, tac) => {
      console.log('验证码验证失败回调...')
      // 验证失败后重新拉取验证码
      tac.reloadCaptcha()
    },
    // 刷新按钮回调事件
    btnRefreshFun: (el, tac) => {
      console.log('刷新按钮触发事件...')
      tac.reloadCaptcha()
    },
    // 关闭按钮回调事件
    btnCloseFun: (el, tac) => {
      console.log('关闭按钮触发事件...')
      tac.destroyWindow()
    },
  }
  // 这里分享一些作者自己调的样式供参考
  const style = {
    // 按钮样式
    btnUrl: 'https://minio.tianai.cloud/public/captcha-btn/btn3.png',
    // 背景样式
    bgUrl: 'https://minio.tianai.cloud/public/captcha-btn/btn3-bg.jpg',
    // logo地址
    logoUrl: '/favicon.ico',
    // 滑动边框样式
    moveTrackMaskBgColor: '#f7b645',
    moveTrackMaskBorderColor: '#ef9c0d',
  }

  // -------------- 拉起TAC验证码 -----------------

  // 参数1： tac文件的URL地址前缀， 目录里包含 tac的js和css等文件，
  //      比如参数为: http://xxxx/tac/, 该js会自动加载 http://xxxx/tac/js/tac.min.js 、http://xxxx/tac/css/tac.css等
  //      具体的js文件可以在 https://gitee.com/tianai/tianai-captcha-web-sdk/releases/tag/1.2 下载
  // 参数2： tac验证码相关配置
  // 参数3： tac窗口一些样式配置
  window.initTAC('./tac', config, style).then((tac) => {
    tac.init() // 调用init则显示验证码
  }).catch((e) => {
    console.log('初始化tac失败', e)
  })
}
</script>

<style scoped>

</style>
