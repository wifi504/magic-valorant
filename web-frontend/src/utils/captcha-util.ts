export interface TAC {
  reloadCaptcha: () => void
  destroyWindow: () => void
  init: () => void
}

export interface Context {
  boxEl: {
    dom: HTMLDivElement
    domStr: string
  }
  styleConfig: {
    btnUrl: string
    moveTrackMaskBgColor: string
    moveTrackMaskBorderColor: string
    i18n: {
      tips_success: string
      tips_error: string
      slider_title: string
      concat_title: string
      image_click_title: string
      rotate_title: string
      slider_title_size: string
      concat_title_size: string
      rotate_title_size: string
    }
    bgUrl: string
    logoUrl: string
  }
  type: 'SLIDER' | 'ROTATE' | 'CONCAT' | 'WORD_IMAGE_CLICK' | 'DISABLED'
  el: {
    dom: HTMLDivElement
    domStr: string
  }
}

export interface Config {
  // 生成接口 (必选项,必须配置, 要符合tianai-captcha默认验证码生成接口规范)
  requestCaptchaDataUrl: string
  // 验证接口 (必选项,必须配置, 要符合tianai-captcha默认验证码校验接口规范)
  validCaptchaUrl: string
  // 验证码绑定的div块 (必选项,必须配置)
  bindEl: string
  // 验证成功回调函数(必选项,必须配置)
  validSuccess: (res: any, ctx: Context, tac: TAC) => void
  // 验证失败的回调函数(可忽略，如果不自定义 validFail 方法时，会使用默认的)
  validFail?: (res: any, ctx: Context, tac: TAC) => void
  // 刷新按钮回调事件
  btnRefreshFun: (el: PointerEvent, tac: TAC) => void
  // 关闭按钮回调事件
  btnCloseFun: (el: PointerEvent, tac: TAC) => void
}

export interface Style {
  // 按钮样式
  btnUrl?: string
  // 背景样式
  bgUrl?: string
  // logo地址
  logoUrl?: string
  // 滑动边框样式
  moveTrackMaskBgColor?: string
  moveTrackMaskBorderColor?: string
}

export async function loadTAC(config: Config, style: Style) {
  // 动态加载 TAC
  await new Promise<void>((resolve, reject) => {
    const script = document.createElement('script')
    script.src = `${import.meta.env.VITE_BASEURL}/tac/load.min.js`
    script.async = true
    script.onload = () => resolve()
    script.onerror = () => reject(new Error('TAC 加载失败'))
    document.head.appendChild(script)
  })

  const win = window as any
  if (!win.initTAC) {
    throw new Error('TAC 加载失败')
  }

  try {
    return (await win.initTAC(`${import.meta.env.VITE_BASEURL}/tac`, config, style)) as TAC
  } catch {
    throw new Error('TAC 初始化失败')
  }
}
