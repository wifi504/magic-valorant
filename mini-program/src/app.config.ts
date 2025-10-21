export default defineAppConfig({
  pages: [
    'pages/index/index',
    'pages/start/index',
    'pages/profile/index',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: '妙瓦 MagicValorant',
    navigationBarTextStyle: 'black',
  },
  lazyCodeLoading: 'requiredComponents',
  tabBar: {
    custom: true,
    list: [
      { pagePath: 'pages/index/index', text: '首页' },
      { pagePath: 'pages/start/index', text: '妙瓦对局助手' },
      { pagePath: 'pages/profile/index', text: '我的' },
    ],
  },
})
