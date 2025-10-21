import Taro from '@tarojs/taro'
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'

export const useTabbarStore = defineStore('tabbar', () => {
  // tab 列表
  const list = [
    {
      pagePath: '/pages/index/index',
      text: '首页',
      textActive: '首页',
    },
    {
      pagePath: '/pages/start/index',
      text: '开始',
      textActive: '在游戏中',
    },
    {
      pagePath: '/pages/profile/index',
      text: '我的',
      textActive: '我的',
    },
  ]

  // 获取当前所在的页面
  const currentPages = Taro.getCurrentPages()
  const currentPage = currentPages[currentPages.length - 1]

  const _selected = ref(list.findIndex(item => item.pagePath === `/${currentPage.route}`))
  const selected = computed(() => _selected.value)

  function setSelected(index: number) {
    const url = list[index].pagePath
    Taro.switchTab({ url })
    _selected.value = index
  }

  return {
    selected,
    list,
    setSelected,
  }
})
