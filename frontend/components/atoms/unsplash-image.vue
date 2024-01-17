<template>
  <v-img
      :lazy-src="img(src, { width: 10, quality: 70 })"
      :src="img(src, { height, quality: 70 })"
      :srcset="imgSrcset.srcset"
      :sizes="imgSrcset.sizes"
      :height="height"
      v-bind="$attrs"
  >
    <slot/>
  </v-img>
</template>

<script setup lang="ts">
const props = defineProps({
  src: {
    type: String,
    required: true
  },
  height: {
    type: Number,
    default: 600
  }
});

const img = useImage();
const imgSrcset = computed(() => img.getSizes(
    props.src,
    {
      sizes: 'xs:100vw sm:100vw md:100vw lg:100vw xl:100vw',
      modifiers: {
        format: 'webp',
        quality: 70,
        height: props.height
      }
    }
));
</script>
