import Taro from '@tarojs/taro'
import service from '@/service'

export default {
  // 微信一键登录
  async doLogin() {
    // 获取登录码
    const code = (await Taro.login()).code
    // 执行登录
    const token = await service.wechatLogin(code)
    // 缓存 token
    Taro.setStorageSync('token', token)
    // TODO 获取当前用户信息
  },
}
