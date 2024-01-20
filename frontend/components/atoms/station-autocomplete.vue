<template>
  <v-autocomplete
      :model-value="autocompleteItem"
      :error-messages="errorMessages"
      :label="label"
      :hint="hint"
      :loading="stationsPending"
      :items="autocompleteItems"
      item-title="title"
      item-value="value"
      @update:model-value="selectStation($event)"
      @update:search="queryInput = $event"
  >
    <template v-slot:item="{ props, item }">
      <v-list-item
          v-bind="props"
          :title="item.value.name"
      >
        <v-list-item-subtitle>
          CH{{ item.value.opuic }} &mdash; {{ item.value.abbreviatedName }}
        </v-list-item-subtitle>
      </v-list-item>
    </template>

    <template v-slot:append>
      <v-menu location="bottom center">
        <template v-slot:activator="{ props }">
          <v-btn
              icon="i-ph:map-pin-duotone"
              color="grey-lighten-1"
              variant="outlined"
              v-bind="props"
              @click="onLocationOpen"
          ></v-btn>
        </template>
        <v-card>
          <v-card-title>Closest stations from your position</v-card-title>
          <v-card-text>
            <v-list lines="one">
              <v-list-item
                  v-for="(station, i) in geoposStations"
                  :key="station.opuic"
                  :value="station"
                  :title="station.name"
                  class="border-b"
                  :class="{'border-t': i === 0}"
                  @click="selectStation(station)"
              >
                <v-list-item-subtitle>
                  CH{{ station.opuic }} &mdash; {{ station.abbreviatedName }}
                </v-list-item-subtitle>
              </v-list-item>
            </v-list>
          </v-card-text>
        </v-card>
      </v-menu>
    </template>
  </v-autocomplete>
</template>

<script setup lang="ts">
import type { PropType } from 'vue';
import type { components } from '#build/types/nuxt-open-fetch/default';

type StationSearchViewModel = components['schemas']['StationSearchViewModel'];
type ListItem = { title: string, value: StationSearchViewModel };
const {$defaultFetch} = useNuxtApp();

const emit = defineEmits(['update:modelValue']);
const props = defineProps({
  label: String,
  hint: String,
  modelValue: Object as PropType<StationSearchViewModel>,
  required: Boolean,
  errorMessages: {type: Array, default: () => []},
});

const selectedStation = ref<StationSearchViewModel | null>(null);
const autocompleteItem = computed((): ListItem | null => {
  if (!selectedStation.value) {
    return null;
  }

  return {
    title: `${selectedStation.value.name} — ${selectedStation.value.abbreviatedName}`,
    value: selectedStation.value,
  };
});

const queryInput = ref<string>('');
const query = refDebounced(queryInput, 300);
const {data: stations, pending: stationsPending} = useDefaultFetch('/v1/stations/search', {
  query: {query: computed(() => query.value.split('—')[0].trim())
  },
});

const autocompleteItems = computed((): ListItem[] => {
  if (!stations.value) {
    return [];
  }

  return stations.value.map((station) => {
    return {
      title: `${station.name} — ${station.abbreviatedName}`,
      value: station,
    };
  });
});

const {coords} = useGeolocation();
const geoposStations = ref<StationSearchViewModel[]>([]);

async function onLocationOpen() {
  if (!coords.value) {
    return;
  }

  await $defaultFetch('/v1/stations/search/{lon}/{lat}/closest', {
    path: {
      lon: coords.value.longitude,
      lat: coords.value.latitude,
    },
  }).then((result) => {
    geoposStations.value = result;
  });
}

function selectStation(station: StationSearchViewModel) {
  selectedStation.value = station;
}

watch(toRef(props, 'modelValue'), (newValue) => {
  if (newValue) {
    selectedStation.value = newValue;
  }
});

watch(selectedStation, (newValue) => {
  emit("update:modelValue", newValue);
});
</script>
