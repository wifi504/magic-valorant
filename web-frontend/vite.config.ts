import { execSync } from 'node:child_process'
import * as fs from 'node:fs'
import path from 'node:path'
import process from 'node:process'
import { VIconsResolver } from '@ezview/vue-component-resolvers'
import vue from '@vitejs/plugin-vue'
import { NaiveUiResolver } from 'unplugin-vue-components/resolvers'
import Components from 'unplugin-vue-components/vite'
import { defineConfig, loadEnv } from 'vite'

// 注入当前处在的Git提交SHA
const GIT_SHA = execSync('git rev-parse HEAD').toString().trim()
// 注入当前项目版本号
const PKG_JSON = JSON.parse(fs.readFileSync(path.resolve(__dirname, 'package.json'), 'utf-8'))

// https://vite.dev/config/
export default defineConfig(({ mode, command }) => {
  const env = loadEnv(mode, process.cwd(), '')

  return {
    base: `/${env.VITE_BASEURL}`,
    plugins: [
      vue(),
      Components({
        dirs: ['src/components'],
        extensions: ['vue'],
        deep: true,
        dts: 'src/components.d.ts',
        resolvers: [
          NaiveUiResolver(),
          VIconsResolver(),
        ],
      }),
    ],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'),
      },
    },
    server: {
      port: 5173,
    },
    preview: {
      port: 5174,
    },
    define: {
      'import.meta.env.VITE_GIT_SHA': JSON.stringify(GIT_SHA),
      'import.meta.env.VITE_VERSION': JSON.stringify(PKG_JSON.version),
    },
    esbuild: {
      drop: command === 'build' ? ['console', 'debugger'] : [],
    },
  }
})
