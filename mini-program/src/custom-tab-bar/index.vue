<template>
  <view :class="$style['mv-tabbar']">
    <view :class="$style.hr" />
    <template v-for="page in tabbarStore.list" :key="page.pagePath">
      <view v-if="page.text === '开始'" :class="$style['main-item-wrapper']">
        <view :class="$style.text">
          开始
        </view>
        <view :class="$style.border" />
      </view>
      <view v-else :class="$style['normal-item-wrapper']">
        <view :class="$style.text">
          {{ page.text }}
        </view>
        <view :class="$style.border" />
      </view>
    </template>
  </view>
</template>

<script setup lang="ts">
import { useTabbarStore } from '../store/tabbar'

const tabbarStore = useTabbarStore()
</script>

<style module lang="less">
.item-wrapper {
  background-color: #506475;
  text-align: center;
  color: #f6f7f7;
  position: relative;
}

.mv-tabbar {
  background-color: #1e4159;
  border-top: 1px solid #dcdcdc;
  padding: 16px 32px 66px 32px;
  display: flex;
  align-items: center;
  align-content: center;
  justify-content: space-around;
  position: relative;
  z-index: 1;

  .normal-item-wrapper {
    // 宽高多大
    --width: 160px;
    --height: 50px;
    // 装饰边框深度多大
    --depth: 5px;

    .item-wrapper();
    width: var(--width);
    height: var(--height);

    .text {
      font-size: 24px;
      position: relative;
      top: 48%;
      transform: translateY(-50%);
    }

    // 往里缩的边框
    .border {
      border: 1px solid rgba(255, 255, 255, .3);
      width: calc(var(--width) - 2 * var(--depth));
      height: calc(var(--height) - 2 * var(--depth));
      position: absolute;
      top: calc(var(--depth) - 1px);
      left: calc(var(--depth) - 1px);
    }

    // 再加4个点，完美复刻
    &::after {
      content: '';
      position: absolute;
      width: var(--depth);
      height: var(--depth);
      background-color: #fff;
      top: 0;
      left: 0;
      box-shadow:
        calc(var(--width) - var(--depth)) 0,
        calc(var(--width) - var(--depth)) calc(var(--height) - var(--depth)),
        0 calc(var(--height) - var(--depth));
    }
  }

  .main-item-wrapper {
    // 宽高多大
    --width: 265px;
    --height: 80px;
    // 装饰边框深度多大
    --depth: 5px;

    .item-wrapper();
    background-color: #ff4655;
    width: var(--width);
    height: var(--height);

    .text {
      font-size: 48px;
      color: #fff;
      font-weight: bold;
      position: relative;
      top: 45%;
      transform: translateY(-50%);
    }

    // 往外扩的边框
    .border {
      border: 1px solid rgba(255, 255, 255, .3);
      width: calc(var(--width) + 2 * var(--depth));
      height: calc(var(--height) + 2 * var(--depth));
      position: absolute;
      top: 0;
      left: 0;
      transform:
        translateX(calc(-1 * var(--depth) - 1px))
        translateY(calc(-1 * var(--depth) - 1px));
    }
  }

  .hr {
    background-color: rgba(255, 255, 255, .3);
    height: 2px;
    width: 50%;
    position: absolute;
    z-index: 0;
  }
}
</style>
