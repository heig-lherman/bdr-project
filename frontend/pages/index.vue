<template>
  <div>
    <AtomsUnsplashImage
        src="https://images.unsplash.com/photo-1690403755220-d7702a39ea4a"
        :height="600"
        cover
        class="position-relative"
    >
      <div class="d-flex flex-column fill-height justify-center align-center text-white image-cover--text">
        <h1 class="text-h2 font-weight-light mb-4 text-center">
          Project <b>Choo</b>Choo
        </h1>
        <h4 class="subheading font-weight-light">
          track the tracks you tracked
        </h4>
      </div>
      <div class="image-cover"></div>
    </AtomsUnsplashImage>
    <div class="hero-section">
      <v-container class="h-100">
        <div class="d-flex w-100 h-100 align-center justify-center flex-column ga-12">
          <h1 class="text-h2 font-weight-medium text-blue-accent-1">Start tracking now</h1>
          <v-btn
              color="purple"
              prepend-icon="i-ph:airplane-takeoff-duotone"
              text="My journeys"
              size="x-large"
              to="/journeys"
          />
        </div>
      </v-container>
    </div>
  </div>
</template>

<script setup lang="ts">
definePageMeta({
  layout: 'fluid',
  auth: false,
})

useHead({
  titleTemplate: 'Project ChooChoo'
})
</script>

<style scoped>
.hero-section {
  height: 600px;
}

.image-cover {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
  background: linear-gradient(to right bottom, rgba(0, 0, 0, 0.125), rgba(0, 0, 0, 0.5));
}

.image-cover--text {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 10;
}
</style>
