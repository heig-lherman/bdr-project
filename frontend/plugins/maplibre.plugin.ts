import VueMaplibreGl from 'vue-maplibre-gl'

export default defineNuxtPlugin(nuxtApp => {
    nuxtApp.vueApp.use(VueMaplibreGl);
});
