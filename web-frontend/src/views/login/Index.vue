<template>
  <div>
    <div>用户登录</div>
    <div class="login-form">
      <n-form label-placement="left" :label-width="60">
        <n-form-item label="邮箱">
          <n-input placeholder="请输入邮箱" />
        </n-form-item>
        <n-form-item label="密码">
          <n-input placeholder="请输入密码" />
        </n-form-item>
        <n-form-item label="验证码">
          <n-button style="flex: 1;">
            点击验证
          </n-button>
        </n-form-item>
        <n-form-item label="&nbsp;">
          <div class="actions">
            <n-button type="primary">
              登录
            </n-button>
            <n-button type="primary" ghost>
              立即注册
            </n-button>
          </div>
        </n-form-item>
      </n-form>
    </div>
    <captcha />
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="less">
.login-form {
  width: 400px;
  background-color: var(--background-color-light);

  .actions {
    width: 100%;
    display: flex;
    gap: 20px;

    button {
      flex: 1;
    }
  }
}
</style>
