// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: [
        '@nuxtjs/tailwindcss',
        '@unocss/nuxt',
        '@vueuse/nuxt',
        'vuetify-nuxt-module',
    ],
    vuetify: {
        moduleOptions: {},
        vuetifyOptions: {
            theme: {
                defaultTheme: 'dark'
            },
            icons: {
                defaultSet: 'unocss-mdi'
            }
        }
    },
    devtools: {enabled: true},
    ssr: false,
});
