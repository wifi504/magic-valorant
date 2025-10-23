<template>
  <background-container>
    <view :class="$style.index">
      <text :class="$style.title">
        「妙瓦」Magic Valorant
      </text>
      <text :class="$style.p">
        无畏契约道具点位小助手，难道你真的是赋能哥？
      </text>
      <view :class="$style.hr" />
      <text :class="$style.p">
        能在短短的开局十秒时间为任何玩家提供各类点位的有效辅助提示，就像点了一个赋能陪玩手把手教你放技能一样。
      </text>
      <text :class="$style.p">
        项目目前正在企划，敬请期待...
      </text>
      <nut-button type="primary" @click="onClick">
        按一下按钮
      </nut-button>
      <nut-toast v-model:visible="show" msg="按钮被成功按下了" />
    </view>
  </background-container>
</template>

<script lang="ts" setup>
import { ref } from 'vue'

const show = ref(false)
function onClick() {
  show.value = true
  console.log('按钮成功按下')
}
</script>

<style module lang="less">
.index {
  padding: 350px 35px 0 35px;
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 35px;

  .title {
    display: block;
    font-size: 40px;
    font-weight: bold;
  }

  .p {
    text-align: justify;
    display: block;
  }

  .hr {
    width: 100%;
    height: 3px;
    background-color: #dedede;
  }
}
</style>
