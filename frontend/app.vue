<template>
  <div>
    <NuxtLayout>
      <NuxtLoadingIndicator />
      <ClientOnly>
        <VSonner position="top-right" offset="6rem"/>
      </ClientOnly>
      <NuxtPage/>
    </NuxtLayout>
  </div>
</template>

<script setup lang="ts">
import { VSonner } from 'vuetify-sonner';

useHead({
  titleTemplate: (titleChunk) => titleChunk
      ? `${titleChunk} - ChooChoo`
      : 'ChooChoo'
});
</script>

<style>
[data-sonner-toast] {
  width: 100%;
}
</style>
