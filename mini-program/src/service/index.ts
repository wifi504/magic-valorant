import request from '@/service/request'

export default {
  // 微信登录，返回用户登录令牌
  async wechatLogin(code: string) {
    const res = await request.get('/wechatLogin', {
      params: { code },
    })
    if (res.data.code !== 200) {
      throw new Error(res.data.msg)
    }
    return res.data.data as string
  },
}
