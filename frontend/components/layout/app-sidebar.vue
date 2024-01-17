<template>
  <v-sheet rounded="lg">
    <v-list rounded="lg">
      <v-list-item
          v-for="n in 5"
          :key="n"
          link
          :title="`List Item ${n}`"
      ></v-list-item>

      <v-divider class="my-2"></v-divider>

      <v-list-item
          color="grey-lighten-4"
          link
          title="Refresh"
      ></v-list-item>
    </v-list>
  </v-sheet>
</template>

<script setup lang="ts">
</script>
