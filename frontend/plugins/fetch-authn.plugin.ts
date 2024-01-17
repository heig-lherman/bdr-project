export default defineNuxtPlugin(() => {
    const {public: {openFetch: clients}} = useRuntimeConfig();
    const {token} = useAuth();
    return {
        provide: Object.fromEntries(Object.entries(clients).map(([name]) => [
            `${name}Fetch`,
            createOpenFetch((options) => ({
                ...clients.default,
                ...options,
                onRequest(ctx) {
                    if (token.value) {
                        ctx.options.headers = {
                            ...(ctx.options.headers ?? {}),
                            Authorization: token.value
                        };
                    }

                    return options.onRequest?.(ctx);
                }
            }))]
        ))
    };
});
