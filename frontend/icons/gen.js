import {cleanupSVG, importDirectory, runSVGO,} from '@iconify/tools';
import fs from 'node:fs/promises';

(async () => {
    // Import icons
    const iconSet = await importDirectory('icons/cantons', {
        prefix: 'ch-cantons',
    });

    // Validate, clean up, fix palette and optimise
    iconSet.forEach((name, type) => {
        if (type !== 'icon') {
            return;
        }

        const svg = iconSet.toSVG(name);
        if (!svg) {
            // Invalid icon
            iconSet.remove(name);
            return;
        }

        // Clean up and optimise icons
        try {
            // Clean up icon code
            cleanupSVG(svg);

            // Optimise
            runSVGO(svg);
        } catch (err) {
            // Invalid icon
            console.error(`Error parsing ${name}:`, err);
            iconSet.remove(name);
            return;
        }

        // Update icon
        iconSet.fromSVG(name, svg);
    });

    // Export as IconifyJSON
    const exported = JSON.stringify(iconSet.export(), null, '\t') + '\n';

    // Save to file
    await fs.writeFile(`icons/${iconSet.prefix}.json`, exported, 'utf8');
})();
