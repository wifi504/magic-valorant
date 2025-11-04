<template>
  <view :class="$style['profile-card']">
    <view :class="$style['top-bar']">
      <view :class="$style.username">
        {{ userProfileStore.nickname }}
      </view>
      <view :class="$style.subtitle">
        标签
      </view>
      <view :class="$style.avatar">
        image
      </view>
    </view>
    <view :class="$style['middle-bar']">
      123
    </view>
    <view :class="$style['bottom-bar']">
      456
    </view>
  </view>
</template>

<script setup lang="ts">
import { useUserProfileStore } from '@/store/user-profile'

const userProfileStore = useUserProfileStore()
</script>

<style module lang="less">
.profile-card {
  background-color: #eeeeee;
  width: calc(100% - (2 * 32px));
  margin: calc(36px + 32px) auto 10px auto;
  color: #000000;

  .top-bar {
    background-color: #b6b8b9;
    color: #303030;
    line-height: 1;
    padding: 36px;
    position: relative;

    .username {
      min-height: 42px;
      font-weight: bold;
      font-size: 42px;
    }

    .subtitle {
      margin-top: 8px;
      font-size: 28px;
    }

    .avatar {
      width: 150px;
      height: 150px;
      box-sizing: border-box;
      background-color: #fff;
      position: absolute;
      right: 36px;
      top: -36px;
      border: 4px solid #2a387c;
      box-shadow: 0 0 0 8px #e8e8df,
                  0 0 0 16px #2d2324;
    }
  }
  .middle-bar {
    background-color: #63b6a8;
    padding: 10px 36px;
    color: #ffffff;
  }
  .bottom-bar {
    background-color: #191c21;
    color: #bcc3c3;
  }
}
</style>
