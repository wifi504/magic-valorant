export async function useTAC() {
  // 动态加载 load.min.js（即你上面那段 load.js）
  await new Promise<void>((resolve, reject) => {
    const script = document.createElement('script')
    script.src = '/tac/load.min.js'
    script.async = true
    script.onload = () => resolve()
    script.onerror = () => reject(new Error('TAC 加载失败'))
    document.head.appendChild(script)
  })

  // 调用全局暴露的 loadTAC
  const win = window as any
  if (!win.loadTAC) {
    throw new Error('TAC 加载失败')
  }
}
