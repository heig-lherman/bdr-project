<template>
  <div>
    <NuxtLayout>
      <NuxtLoadingIndicator />
      <NuxtPage/>
    </NuxtLayout>
  </div>
</template>

<script setup lang="ts">
useHead({
  titleTemplate: (titleChunk) => titleChunk
      ? `${titleChunk} - ChooChoo`
      : 'ChooChoo'
});
</script>
