import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserProfileStore = defineStore('user-profile', () => {
  const id = ref(-1)
  const nickname = ref(' ')

  // TODO 获取用户信息
  setTimeout(() => {
    id.value = 666
    nickname.value = '这是临时昵称'
  }, 3000)

  return {
    id,
    nickname,
  }
})
