import EzRequest from '@ezview/request'

const request = new EzRequest('magic-valorant', {
  baseURL: import.meta.env.VITE_REQUEST_BASE_URL,
  timeout: 3000,
})

export { request }
