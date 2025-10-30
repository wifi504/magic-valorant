import type { RouteRecordRaw } from 'vue-router'
/**
 * Vue Router 路由配置自动生成(v1.1)
 * @author WIFI连接超时
 * 请不要编辑此文件，因为在重新运行生成脚本后会覆盖，自定义配置请写在 options.ts 中
 */
export default [
  {
    path: '/',
    component: () => import('@/views/Index.vue'),
  },
  {
    path: '/dashboard',
    component: () => import('@/views/dashboard/Index.vue'),
  },
  {
    path: '/login',
    component: () => import('@/views/login/Index.vue'),
  },
] as RouteRecordRaw[]
