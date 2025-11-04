<template>
  <div
    id="captcha-box" :style="{
      '--bar-color': loadingBarStyle.color,
      '--bar-bg-color': loadingBarStyle.bgColor,
    }"
  />
</template>

<script setup lang="ts">
import type { TAC } from '@/utils/captcha-util.ts'
import { onMounted } from 'vue'
import { loadTAC } from '@/utils/captcha-util.ts'
import debounce from '@/utils/debounce.ts'

interface PromiseAction {
  resolve: (value: unknown) => void
  reject: (reason?: any) => void
}

const promiseAction: PromiseAction = {
  resolve: (_) => {},
  reject: (_) => {},
}

let tac: TAC

const debounceReload = debounce((tac: TAC) => tac.reloadCaptcha(), 500)

onMounted(async () => {
  tac = await loadTAC({
    // 生成接口 (必选项,必须配置, 要符合tianai-captcha默认验证码生成接口规范)
    requestCaptchaDataUrl: `${import.meta.env.VITE_REQUEST_BASE_URL}/public/captcha/gen`,
    // 验证接口 (必选项,必须配置, 要符合tianai-captcha默认验证码校验接口规范)
    validCaptchaUrl: `${import.meta.env.VITE_REQUEST_BASE_URL}/public/captcha/check`,
    // 验证码绑定的div块 (必选项,必须配置)
    bindEl: '#captcha-box',
    // 验证成功回调函数(必选项,必须配置)
    validSuccess: (res, _ctx, tac) => {
      // 销毁验证码服务
      tac.destroyWindow()
      promiseAction.resolve(res.data.token ?? '')
    },
    // 验证失败的回调函数(可忽略，如果不自定义 validFail 方法时，会使用默认的)
    validFail: (_res, _ctx, tac) => {
      // 验证失败后重新拉取验证码
      debounceReload(tac)
    },
    // 刷新按钮回调事件
    btnRefreshFun: (_el, tac) => {
      debounceReload(tac)
    },
    // 关闭按钮回调事件
    btnCloseFun: (_el, tac) => {
      tac.destroyWindow()
      promiseAction.reject('验证码校验失败')
    },
  }, {
    // 按钮样式
    btnUrl: `${import.meta.env.VITE_BASEURL}/tac/images/btn.png`,
    // 背景样式
    bgUrl: `${import.meta.env.VITE_BASEURL}/tac/images/bg.jpg`,
    // logo地址
    logoUrl: `${import.meta.env.VITE_BASEURL}/tac/images/logo.png`,
    // 滑动边框样式
    moveTrackMaskBgColor: '#0c0c0c',
    moveTrackMaskBorderColor: '#000000',
  })
  tac.init()
})

// 加载条样式
const loadingBarStyle = {
  color: '#ff4655',
  bgColor: '#ff465580',
}

// 打开弹窗执行验证
function doVerify() {
  return new Promise((resolve, reject) => {
    promiseAction.resolve = resolve
    promiseAction.reject = reject
  })
}

defineExpose({ doVerify })
</script>

<style lang="less">
#captcha-box {
  #tianai-captcha-loading, .loading {
    background-image: linear-gradient(var(--bar-color) 0 0) !important;
    background-color: var(--bar-bg-color) !important;
  }

  #tianai-captcha {
    .content {
      .bg-img-div, .slider-img-div {
        &:after {
          content: '';
          position: absolute;
          inset: 0;
          background: transparent;
          z-index: 9999;
          pointer-events: all;
        }
      }
    }

    .slider-move {
      filter: opacity(1) !important;

      .slider-move-track {
        filter: opacity(.3) !important;
        background-color: #fff !important;

        #tianai-captcha-slider-move-track-mask {
          opacity: 1 !important;
          top: 8px !important;
          height: 15px !important;
          border-radius: 2px !important;
        }
      }

      .slider-move-btn {
        background-color: #ffffff00 !important;
        border-radius: 0 !important;
      }
    }
  }
}
</style>
