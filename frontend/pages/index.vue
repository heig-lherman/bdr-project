<template>
  <div>
    <AtomsUnsplashImage
        src="https://images.unsplash.com/photo-1690403755220-d7702a39ea4a"
        height="65dvh"
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
      <mgl-map
          ref="map"
          :map-style="styleUrl"
          :center="[8.226667, 46.801111]"
          :zoom="7.2"
          :interactive="false"
      ></mgl-map>
      <!-- TODO (for user heatmap) move event for dynamic data--
          @map:moveend="console.log"-->

      <div class="map-cover rounded-lg py-8 px-6">
        <h1 class="text-h4 font-weight-black text-blue-accent-1">Share your journeys with others</h1>
        <h3 class="text-h5 font-weight-light">So far, users have travelled
          <strong>{{ ((totalDistance?.value ?? 0) / 1000).toFixed(2) }}km</strong></h3>
      </div>

    </div>

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

    <v-footer>
      &copy; {{ currentYear }} - Project ChooChoo
    </v-footer>
  </div>
</template>

<script setup lang="ts">
import { MglMap, useMap } from 'vue-maplibre-gl';
import { MapboxOverlay as DeckOverlay } from '@deck.gl/mapbox/typed';
import { GeoJsonLayer } from '@deck.gl/layers/typed';
import type { Feature, MultiLineString } from 'geojson';
import type { Color } from '@deck.gl/core/typed';
import { useDefaultFetch } from '#imports';

definePageMeta({
  layout: 'fluid',
  auth: false,
});
useHead({
  titleTemplate: 'Project ChooChoo'
});

const {public: {maps: {styleUrl}}} = useRuntimeConfig();

const {data: heatmapGeo} = useLazyDefaultFetch('/v1/geography/global/heatmap', {
  // query: {
  //   random: true
  // },
  headers: {
    Accept: 'application/geo+json',
  }
});

const {data: totalDistance} = useDefaultFetch('/v1/geography/global/distance');

const map = useMap();
watchOnce(toRef(map, 'isMounted'), () => {
  watchOnce(heatmapGeo, () => {
    const overlay = new DeckOverlay({
      layers: [
        new GeoJsonLayer({
          id: 'heatmap',
          data: {
            type: 'FeatureCollection',
            features: toRaw(heatmapGeo.value) as Feature<MultiLineString>[],
          },
          pickable: false,
          stroked: true,
          extruded: true,
          getLineColor: f => {
            const p = f.properties?.percentage ?? 0;
            return [255 * p, 128 * (1 - p), 255 * (1 - p), 170 + 80 * p] as Color;
          },
          getLineWidth: 10,
          lineWidthScale: 50,
          lineWidthMinPixels: 5,
          getElevation: 30
        }),
      ]
    });

    map.map?.addControl(overlay as any);
  });
});

const currentYear = new Date().getFullYear();
</script>

<style scoped>
.hero-section {
  position: relative;
  height: 70dvh;
}

.map-cover {
  position: absolute;
  top: 1rem;
  left: 1rem;
  z-index: 1;
  background: rgba(0, 0, 0, 0.5);
}

.image-cover {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
  background: linear-gradient(to right bottom, rgba(0, 0, 0, 0.125), rgba(0, 0, 0, 0.75));
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
