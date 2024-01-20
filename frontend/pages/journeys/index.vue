<template>
  <div class="d-flex flex-row justify-space-between align-end mb-6">
    <h1 class="text-h2">My journeys</h1>
    <v-btn
        color="secondary"
        prepend-icon="i-ph:traffic-sign-duotone"
        to="/journeys/create"
    >
      Create a journey
    </v-btn>
  </div>

  <v-sheet rounded="lg" elevation="4">
    <JourneysSimpleTable/>
  </v-sheet>
</template>

<script setup lang="ts">
useHead({ title: 'My journeys' })
</script>
