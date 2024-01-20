// uno.config.ts
import { defineConfig } from 'unocss';
import presetIcons from '@unocss/preset-icons';

export default defineConfig({
    presets: [
        presetIcons({
            scale: 1.2,
            collections: {
                chc: () => import('./icons/ch-cantons.json').then((data) => data.default),
            },
        }),
    ]
});
