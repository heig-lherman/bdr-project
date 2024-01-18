// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
    modules: [
        '@nuxt/image',
        '@sidebase/nuxt-auth',
        '@unocss/nuxt',
        '@vee-validate/nuxt',
        '@vueuse/nuxt',
        'nuxt-open-fetch',
        'vuetify-nuxt-module',
    ],
    build: {
        transpile: [
            'jsonwebtoken',
            'vue-sonner',
        ]
    },
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
    openFetch: {
        clients: {
            default: {
                baseURL: `${process.env.API_URL}/api`,
                schema: `${process.env.API_URL}/api/api-docs`
            },
        },
        disablePlugin: true,
    },
    runtimeConfig: {
        public: {
            baseURL: `${process.env.API_URL}/api/v1`,
            maps: {
                geopsKey: process.env.GEOPS_API_KEY,
                styleUrl: `https://maps.geops.io/styles/base_dark_v2/style.json?key=${process.env.GEOPS_API_KEY}`
            },
        },
    },
    devtools: {enabled: true},
    ssr: false,
});
