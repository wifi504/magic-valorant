import Taro from '@tarojs/taro'
import axios from 'axios'
import env from '@/env.config'

// 配置 Axios
const instance = axios.create({
  baseURL: env.baseURL,
  timeout: env.timeout,
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = Taro.getStorageSync('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  },
)

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    return Promise.reject(error)
  },
)

export default {
  get: instance.get,
  post: instance.post,
  put: instance.put,
  delete: instance.delete,
}
