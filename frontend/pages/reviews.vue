<template>
  <div class="d-flex flex-row justify-space-between align-end mb-6">
    <h1 class="text-h2">Reviews</h1>
  </div>

  <v-sheet rounded="lg" elevation="4">
    <ReviewSimpleTable/>
  </v-sheet>
</template>

<script setup lang="ts">
useHead({ title: 'Reviews' });
</script>
