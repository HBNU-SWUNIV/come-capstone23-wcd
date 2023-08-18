// plugins/vuetify.ts
import { createVuetify, ThemeDefinition } from 'vuetify'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { defineNuxtPlugin } from "nuxt/app";

// TODO: Light 테마 색상 설정 (취향대로)
const LightTheme: ThemeDefinition = {
    dark: false,
    variables: {},
    colors: {
        primary: '#4376fb',
        accent: '#757575',
        secondary: '#FF8F00',
        info: '#26A69A',
        warning: '#FFC107',
        error: '#bb252a',
        success: '#4CAF50'
    }
}

// TODO: Nuxt3 vuetify plugin 설정
export default defineNuxtPlugin((NuxtApp) => {
    const vuetify = createVuetify({
        components,
        directives,
        theme: {  // 테마 설정
            themes: {
                light: LightTheme,
                variables: {}
            }
        },
        icons: { // 아이콘 설정
            defaultSet: 'mdi',
            aliases,
            sets: {
                mdi
            }
        }
    })

    NuxtApp.vueApp.use(vuetify)
})
