import colors from 'vuetify/es5/util/colors'

const fs = require("fs");
const path = require('path');

export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    titleTemplate: '%s - web-service',
    title: 'web-service',
    htmlAttrs: {
      lang: 'en'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ],
    script: [
      {
        src: '../node_modules/@ckeditor/ckeditor5-build-classic/build/ckeditor.js',
        type: 'text/javascript',
        charset: 'utf-8',
      },
      {
        src: '../node_modules/@ckeditor/ckeditor5-vue2/dist/ckeditor.js',
        type: 'text/javascript',
        charset: 'utf-8',
      },
    ],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
    '@/static/custom-ckeditor-styles.css',
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    { src: '~/plugins/axios.js', mode: 'client' },
    { src: '~/plugins/ckeditor.js', mode: 'client' },
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/vuetify
    '@nuxtjs/vuetify',
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    '@nuxtjs/axios',
    'nuxt-vuex-localstorage'
  ],

  axios: {
    baseURL: process.env.API_BASE_URL
  },

  // Vuetify module configuration: https://go.nuxtjs.dev/config-vuetify
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: true,
      light: false,
      themes: {
        dark: {
          primary: "#ffffff",
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent4,
          success: colors.green.accent3,
        },
        light: { // 라이트 모드 색상 설정
          primary: colors.blue.lighten2,
          accent: colors.grey.lighten3,
          secondary: colors.amber.lighten3,
          info: colors.teal.darken1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent2,
          success: colors.green.accent2
        }
      }
    }
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
  },

  router: {
    middleware: ['auth'],
  },

  vue: {
    config: {
      ignoredElements: ['openvidu-webcomponent']
    }
  },

  devServer: {
    disableHostCheck: true,
    https: {
      key: path.join(__dirname, process.env.SSL_PRIVATE_KEY),
      cert: path.join(__dirname, process.env.SSL_CERTIFICATE),
      ca: path.join(__dirname, process.env.SSL_CA_BUNDLE)
    }
  },

  env:{
    FIREBASE_API_KEY: process.env.FIREBASE_API_KEY,
    FIREBASE_AUTH_DOMAIN: process.env.FIREBASE_AUTH_DOMAIN,
    FIREBASE_PROJECT_ID: process.env.FIREBASE_PROJECT_ID,
    FIREBASE_STORAGE_BUCKET: process.env.FIREBASE_STORAGE_BUCKET,
    FIREBASE_MESSAGING_SENDER_ID: process.env.FIREBASE_MESSAGING_SENDER_ID,
    FIREBASE_APP_ID: process.env.FIREBASE_APP_ID,
    FIREBASE_MEASUREMENT_ID: process.env.FIREBASE_MEASUREMENT_ID,
    FIREBASE_VAPID_KEY: process.env.FIREBASE_VAPID_KEY,
  },

  ssr: false,

}
