<template>
  <v-row>
    <v-col
        v-for="canton in items"
        :key="canton.cantonCode"
        :cols="display === 'vertical' ? 6 : 3"
    >
      <div class="d-flex flex-column justify-end">
        <div class="d-flex flex-row align-center justify-space-between mb-1">
          <div class="d-flex flex-row align-center text-body-1">
            <i :class="`i-chc:${canton.cantonCode.toLowerCase()}`"></i>
            <span class="ms-1">{{canton.cantonName}}</span>
          </div>
          <span class="text-teal">
            {{ getPercentage(canton).toFixed(1) }}%
          </span>
        </div>
        <v-progress-linear
            :model-value="getPercentage(canton)"
            color="teal"
            height="5"
            rounded
        ></v-progress-linear>
      </div>
    </v-col>
  </v-row>
</template>

<script setup lang="ts">
import type { components } from '#build/types/nuxt-open-fetch/default';
import type { PropType } from 'vue';

export type CantonProgress = components['schemas']['CantonProgressViewModel'];

const props = defineProps({
  items: {
    type: Array as PropType<CantonProgress[]>,
    required: true,
  },
  display: {
    type: String as PropType<'vertical' | 'horizontal'>,
    default: 'vertical',
  },
});

function getPercentage(canton: CantonProgress) {
  return (canton.travelledCount / canton.totalCount) * 100;
}
</script>
