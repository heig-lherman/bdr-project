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
