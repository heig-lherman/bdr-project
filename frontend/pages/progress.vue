<template>
  <div class="position-relative page-container">
    <div class="px-3 mb-6">
      <div class="d-flex flex-row justify-space-between align-end mb-5">
        <h1 class="text-h2">My progress</h1>
        <span class="text-h2 text-teal">{{ getPercentage(progressData).toFixed(1) }}%</span>
      </div>
      <v-progress-linear
          :model-value="getPercentage(progressData)"
          color="teal"
          height="10"
          rounded
      ></v-progress-linear>
    </div>

    <v-row class="h-100">
      <v-col cols="4" class="cantons-column">
        <v-card height="100%" rounded="lg" elevation="6" class="overflow-hidden">
          <v-card-item>
            <v-card-title>Your progress by canton</v-card-title>
          </v-card-item>
          <v-card-text>
            <AtomsCantonsProgress :items="cantonsData"/>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="8" class="map-column">
        <v-sheet height="100%" rounded="lg" elevation="6" class="position-relative overflow-hidden">
          <mgl-map
              :map-style="styleUrl"
              :center="[8.226667, 46.801111]"
              :zoom="7.4"
              :drag-rotate="false"
              :drag-pand="false"
          ></mgl-map>
        </v-sheet>
      </v-col>
    </v-row>
  </div>
</template>

<script setup lang="ts">
import { MglMap, useMap } from 'vue-maplibre-gl';
import { useDefaultFetch } from '#imports';
import type { components } from '#build/types/nuxt-open-fetch/default';
import { MapboxOverlay as DeckOverlay } from '@deck.gl/mapbox/typed';
import { GeoJsonLayer } from '@deck.gl/layers/typed';

useHead({title: 'My progress'});
const {public: {maps: {styleUrl}}} = useRuntimeConfig();

const {data: cantonsData} = useDefaultFetch('/v1/progress/cantons');
const {data: progressData} = useDefaultFetch('/v1/progress/user');

const map = useMap();
watchOnce(toRef(map, 'isMounted'), () => {
  watchOnce(progressData, (progress) => {
    if (!progress) {
      return;
    }

    const overlay = new DeckOverlay({
      layers: [
        new GeoJsonLayer({
          id: 'progress-linestring',
          data: progress.segments,
          stroked: true,
          getLineColor: [0, 128, 255],
          getLineWidth: 20,
          lineWidthScale: 1,
          lineWidthMinPixels: 3,
        }),
      ]
    });

    map.map?.addControl(overlay as any);

    map.map?.fitBounds([
      [progress.boundingBox[0], progress.boundingBox[1]],
      [progress.boundingBox[2], progress.boundingBox[3]],
    ], {
      padding: 100,
    });
  });
});

function getPercentage(progress: components['schemas']['UserProgressViewModel']) {
  if (!progress) {
    return 0;
  }

  return (progress.travelledCount / progress.totalCount) * 100;
}
</script>

<style scoped>
.page-container {
  height: 100%;
  overflow: hidden;
}

.cantons-column {
  position: absolute;
  top: 6rem;
  bottom: 0;
  left: 0;
}

.map-column {
  position: absolute;
  top: 6rem;
  bottom: 0;
  right: 0;
}
</style>
