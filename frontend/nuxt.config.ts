// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    modules: [
        '@nuxt/image',
        '@sidebase/nuxt-auth',
        '@unocss/nuxt',
        '@vee-validate/nuxt',
        '@vueuse/nuxt',
        'vuetify-nuxt-module',
    ],
    app: {
        head: {
            charset: 'utf-8',
            viewport: 'width=device-width, initial-scale=1',
        }
    },
    auth: {
        isEnabled: true,
        baseURL: `${process.env.API_URL}/api/v1/auth`,
        provider: {
            type: 'refresh',
            endpoints: {
                signIn: {path: '/login', method: 'post'},
                signUp: {path: '/register', method: 'post'},
                signOut: {path: '/logout', method: 'post'},
                refresh: {path: '/refresh-token', method: 'post'},
                getSession: {path: '/@me', method: 'get'},
            },
            pages: {
                login: '/auth/login',
            },
            token: {
                signInResponseTokenPointer: '/accessToken',
                maxAgeInSeconds: 3600,
                sameSiteAttribute: 'strict'
            },
            refreshToken: {
                signInResponseRefreshTokenPointer: '/refreshToken',
                maxAgeInSeconds: 86400,
            },
            sessionDataType: {
                email: 'string',
                displayName: 'string',
                firstname: 'string',
                lastName: 'string'
            }
        },
        globalAppMiddleware: true,
    },
    image: {
        provider: 'unsplash'
    },
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
    runtimeConfig: {
        public: {
            baseURL: `${process.env.API_URL}/api/v1`,
        },
    },
    devtools: {enabled: true},
    ssr: false,
});
