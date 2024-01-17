<template>
  <div>
    <NuxtLayout name="404">
      <div class="d-flex h-100 w-100 align-center justify-center flex-column">
        <h1 class="text-h1 text-amber-darken-1">{{ error.statusCode }}</h1>
        <p class="text-h4 my-4">Looks like you got lost.</p>
        <p class="text-h5 font-weight-light">Maybe try turning the junction towards home.</p>
        <v-btn color="primary" class="mt-4" @click="handleError" prepend-icon="i-ph:house-duotone" text="Home"></v-btn>
      </div>
    </NuxtLayout>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  error: Object
});

const handleError = () => clearError({ redirect: '/' });
</script>

<style scoped>
.button {
  padding: 4px 6px;
  margin: 10px 0px;
  background: black;
  color: white;
}
</style>
