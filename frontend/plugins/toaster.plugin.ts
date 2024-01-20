// @unocss-include
import { toast } from 'vuetify-sonner';

type Options = Parameters<typeof toast>[1]

function createToastFunction(color: string, icon: string) {
    return function (text: string, options?: Options) {
        return toast(text, {
            prependIcon: icon,
            cardProps: {
                color,
                ...options?.cardProps,
            },
            ...options,
        })
    }
}

export default defineNuxtPlugin(() => ({
    provide: {
        toast: {
            success: createToastFunction('success', 'i-ph:check-circle-duotone'),
            error: createToastFunction('error', 'i-ph:warning-octagon-duotone'),
            warning: createToastFunction('warning', 'i-ph:warning-duotone'),
            info: createToastFunction('info', 'i-ph:info-duotone'),
            dismiss: toast.dismiss
        }
    }
}))

const swissIcons = [
    'i-chc:ag',
    'i-chc:ai',
    'i-chc:ar',
    'i-chc:be',
    'i-chc:bl',
    'i-chc:bs',
    'i-chc:fr',
    'i-chc:ge',
    'i-chc:gl',
    'i-chc:gr',
    'i-chc:ju',
    'i-chc:lu',
    'i-chc:ne',
    'i-chc:nw',
    'i-chc:ow',
    'i-chc:sg',
    'i-chc:sh',
    'i-chc:so',
    'i-chc:sz',
    'i-chc:tg',
    'i-chc:ti',
    'i-chc:ur',
    'i-chc:vd',
    'i-chc:vs',
    'i-chc:zg',
    'i-chc:zh',
]
