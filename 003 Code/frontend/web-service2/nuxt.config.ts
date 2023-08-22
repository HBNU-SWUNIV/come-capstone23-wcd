// nuxt.config.ts
import vuetify from 'vite-plugin-vuetify'
import { defineNuxtConfig } from 'nuxt/config'

export default defineNuxtConfig({
  // 생략
  css: [
    '@mdi/font/css/materialdesignicons.min.css',
    'vuetify/lib/styles/main.sass'
  ],
  components: [
    {
      path: '~/components',
      pathPrefix: false
    }
  ],
  modules: [
    '@pinia/nuxt',
    '@pinia-plugin-persistedstate/nuxt',
    async (options, nuxt) => {
      nuxt.hooks.hook('vite:extendConfig', (config) =>
          // @ts-ignore
          config.plugins.push(
              vuetify({
                autoImport: true,
                styles: {
                  configFile: 'assets/scss/settings.scss'
                }
              })
          )
      )
    }
  ],
  vite: {
    define: {
      'process.env.DEBUG': false
    },
  },
  build: { transpile: ['vuetify'] },
  runtimeConfig: {
    public: {
      baseURL: process.env.BASE_URL || 'https://localhost:8000/',
    },
  },
  sourcemap: {
    server: false,
    client: false
  },
})