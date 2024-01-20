<template>
  <div class="position-relative h-100">
    <div class="create-content">
      <div class="form-section">
        <v-form class="d-flex flex-column ga-3" fast-fail @submit.prevent="onSubmit">
          <v-card elevation="12" rounded="lg" class="position-sticky" style="top: 0; z-index: 10;">
            <v-card-item>
              <div class="d-flex flex-row justify-space-between align-center">
                <div>
                  <v-card-title>Journey builder</v-card-title>
                  <v-card-subtitle>Build your journey interactively</v-card-subtitle>
                </div>
                <v-btn
                    color="primary"
                    prepend-icon="i-ph:floppy-disk-back-duotone"
                    text="Save journey"
                    size="x-large"
                    variant="tonal"
                    type="submit"
                ></v-btn>
              </div>
            </v-card-item>
          </v-card>

          <v-card elevation="4" rounded="lg">
            <v-card-item>
              <v-card-title>General information</v-card-title>
            </v-card-item>
            <v-card-text>
              <template v-if="data.teamId">
                <v-checkbox
                    label="Journey made with team"
                    v-model="team.value.value"
                    hide-details
                />
              </template>
              <v-row>
                <v-col cols="6">
                  <v-text-field
                      label="Start date"
                      v-model="startDate.value.value"
                      :error-messages="startDate.errorMessage.value"
                      required
                      type="datetime-local"
                  ></v-text-field>
                </v-col>
                <v-col cols="6">
                  <v-text-field
                      label="End date"
                      v-model="endDate.value.value"
                      :error-messages="endDate.errorMessage.value"
                      required
                      type="datetime-local"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>

          <v-card elevation="4" rounded="lg">
            <v-card-item>
              <v-card-title>Tracked segments</v-card-title>
            </v-card-item>
            <v-card-text>
              <AtomsStationAutocomplete
                  label="Start station"
                  hint="The station where you started this segment"
                  v-model="startStation"
              ></AtomsStationAutocomplete>

              <v-combobox
                  v-model="endStation"
                  label="End station"
                  :items="connectingStations"
                  item-title="title"
                  item-value="value"
                  :disabled="!connectingStations.length"
                  :hint="!connectingStations.length ? 'Please select a start station first' : ''"
                  persistent-hint
              >
                <template v-slot:append>
                  <v-dialog v-model="pathfindingDialog" max-width="60dvw" persistent>
                    <template v-slot:activator="{ props }">
                      <v-btn
                          icon="i-ph:magnifying-glass-duotone"
                          color="grey-lighten-1"
                          variant="outlined"
                          v-bind="props"
                      ></v-btn>
                    </template>
                    <v-card :loading="pathfindingLoading">
                      <v-card-title>Select an end station</v-card-title>
                      <v-card-text>
                        <AtomsStationAutocomplete
                            label="End station"
                            hint="The station where you want to end this segment"
                            v-model="endPathfindStation"
                        ></AtomsStationAutocomplete>

                        <template v-if="!pathfindingResults.length">
                          <v-divider class="my-3"></v-divider>
                          <p class="text-grey-darken-2">Select an end station above to see pathfinding results.</p>
                        </template>
                        <template v-else>
                          <p>Select a path below, or refine your search</p>
                          <v-list lines="two">
                            <v-list-item
                                v-for="(path, i) of pathfindingResults"
                                :key="i"
                                :value="path"
                                class="border-b"
                                :class="{'border-t': i === 0}"
                                @click="addPathfindingSegment(path)"
                            >
                              <v-list-item-title>
                                {{ startStation.name }} ({{ startStation.abbreviatedName }})
                                to
                                {{ endPathfindStation.name }} ({{ endPathfindStation.abbreviatedName }})
                              </v-list-item-title>
                              <v-list-item-subtitle>
                                {{ (path.totalDistance / 1000).toFixed(2) }}km via
                                {{ keepPartOfArray(path.stationNames).join(', ') }}
                              </v-list-item-subtitle>
                              <v-list-item-subtitle class="text-grey-darken-2 mt-1">
                                {{ path.stationAbbreviatedNames.join('-') }}
                              </v-list-item-subtitle>
                            </v-list-item>
                          </v-list>
                        </template>
                      </v-card-text>
                      <v-card-actions class="justify-end">
                        <v-btn
                            variant="text"
                            color="error"
                            text="Cancel"
                            @click="pathfindingDialog = false"
                        />
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </template>
              </v-combobox>

              <div class="d-flex justify-end">
                <v-btn
                    color="primary"
                    variant="tonal"
                    text="Add segment"
                    @click="addConnectingSegment"
                ></v-btn>
              </div>

              <template
                  v-for="(segment, i) in segments"
                  :key="i"
              >
                <v-divider class="my-3"/>
                <h4 class="mb-1">Segment {{ i + 1 }}</h4>
                <div v-if="segment.type === 'connecting-station'">
                  <p>
                    {{ segment.start.name }} ({{ segment.start.abbreviatedName }})
                    via line {{ segment.end.line }} ({{ segment.end.lineName }})
                    to {{ segment.end.name }} ({{ segment.end.abbreviatedName }})
                  </p>
                  <span class="text-grey-darken-2">
                    Total distance: {{ (segment.end.totalDistance / 1000).toFixed(2) }}km
                  </span>
                </div>
                <div v-if="segment.type === 'path-finding'">
                  <p>
                    {{ segment.start.name }} ({{ segment.start.abbreviatedName }})
                    to {{ segment.end.name }} ({{ segment.end.abbreviatedName }})
                  </p>
                  <span class="text-grey-darken-2">
                    {{ (segment.path.totalDistance / 1000).toFixed(2) }}km
                    via {{ keepPartOfArray(segment.path.stationNames).join(', ') }}
                  </span>
                </div>
              </template>
            </v-card-text>
          </v-card>

          <v-card elevation="4" rounded="lg">
            <v-card-item>
              <v-card-title>Review</v-card-title>
            </v-card-item>
            <v-card-text>
              <v-rating
                  v-model="reviewGrade.value.value"
                  :error-messages="reviewGrade.errorMessage.value"
                  half-increments
                  hover
                  :length="5"
                  :size="32"
                  active-color="primary"
              />
              <v-textarea
                  label="Review note"
                  v-model="reviewNote.value.value"
                  :error-messages="reviewNote.errorMessage.value"
                  required
                  :counter="2000"
              />
            </v-card-text>
          </v-card>
        </v-form>
      </div>
      <div class="map-section">
        <v-sheet height="100%" rounded="lg" elevation="6" class="position-relative overflow-hidden">
          <mgl-map
              ref="map"
              :map-style="styleUrl"
              :center="[8.226667, 46.801111]"
              :zoom="7"
              :drag-rotate="false"
              :drag-pand="false"
          ></mgl-map>
        </v-sheet>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { MglMap, useMap } from 'vue-maplibre-gl';
import * as zod from 'zod';
import type { ListItem } from '~/types/list-item';
import type {
  ConnectingStationViewModel,
  PathfindingViewModel,
  StationSearchViewModel,
  TrackedSegment
} from '~/types/tracked-segment';
import { MapboxOverlay as DeckOverlay } from '@deck.gl/mapbox/typed';
import { GeoJsonLayer, ScatterplotLayer } from '@deck.gl/layers/typed';

useHead({title: 'Journey Creator'});
definePageMeta({layout: 'fluid'});

// Page setup

const {$defaultFetch, $toast} = useNuxtApp();
const {public: {maps: {styleUrl}}} = useRuntimeConfig();
const {data} = useAuth();

const validationSchema = toTypedSchema(zod.object({
  startDate: zod.coerce.date(),
  endDate: zod.coerce.date(),
  team: zod.boolean().default(false),
  segments: zod.array(zod.number()),
  reviewGrade: zod.number(),
  reviewNote: zod.string().min(1, 'A review note is required').max(2000),
}).refine((data) => data.endDate > data.startDate, {
  message: 'End date must be after start date',
  path: ['endDate'],
}));

const {handleSubmit} = useForm({validationSchema});

// general information
const startDate = useField('startDate', validationSchema);
const endDate = useField('endDate', validationSchema);
const team = useField('team', validationSchema);

// segments
const segmentsField = useField('segments', validationSchema);

// review
const reviewGrade = useField('reviewGrade', validationSchema);
const reviewNote = useField('reviewNote', validationSchema);

const pathfindingDialog = ref(false);

// Segment data mapping
const segments = reactive<TrackedSegment[]>([]);

const startStation = ref<StationSearchViewModel | null>(null);
const endStation = ref<ConnectingStationViewModel | null>(null);

const connectingStations = ref<ListItem<ConnectingStationViewModel>[]>([]);
watch(startStation, async (newStation) => {
  if (!newStation) {
    connectingStations.value = [];
    return;
  }

  await $defaultFetch('/v1/stations/{opuic}/search/connecting', {
    path: {
      opuic: newStation.opuic,
    }
  }).then((data) => {
    connectingStations.value = data.map((station) => {
      return {
        title: `${station.name} (${station.abbreviatedName}) via ${station.lineName}`,
        value: station,
      };
    });
  });
});

const endPathfindStation = ref<StationSearchViewModel | null>(null);
const pathfindingResults = ref<PathfindingViewModel[]>([]);
const pathfindingLoading = ref(false);
watch(endPathfindStation, async (newStation) => {
  if (!newStation || !startStation.value) {
    pathfindingResults.value = [];
    return;
  }

  pathfindingLoading.value = true;
  await $defaultFetch('/v1/stations/{startOpuic}/{endOpuic}/search/path-finding', {
    path: {
      startOpuic: startStation.value.opuic,
      endOpuic: newStation.opuic,
    },
    query: {
      amount: 3
    }
  }).then((data) => {
    pathfindingResults.value = data;
    pathfindingLoading.value = false;
  });
});

function addConnectingSegment() {
  if (!startStation.value || !endStation.value) {
    return;
  }

  segments.push({
    type: 'connecting-station',
    start: startStation.value,
    end: endStation.value.value,
  });

  startStation.value = endStation.value.value;
  endStation.value = null;
}

function addPathfindingSegment(path: PathfindingViewModel) {
  if (!startStation.value || !endPathfindStation.value) {
    return;
  }

  segments.push({
    type: 'path-finding',
    start: startStation.value,
    end: endPathfindStation.value,
    path,
  });

  startStation.value = endPathfindStation.value;
  endPathfindStation.value = null;
  pathfindingDialog.value = false;
}

const sumEdges = computed(() => {
  return [...new Set(segments.reduce((acc, segment) => {
    if (segment.type === 'connecting-station') {
      return [...acc, ...segment.end.edges];
    }

    return [...acc, ...segment.path.edges];
  }, []))];
});

watch(sumEdges, (edges) => {
  segmentsField.value.value = edges;
});

// map layers
const map = useMap();
watchOnce(toRef(map, 'isMounted'), () => {
  let previousOverlay = null;
  watch([segments, sumEdges], async ([newSegments, edges]) => {
    if (previousOverlay) {
      map.map?.removeControl(previousOverlay);
    }

    const stationPoints = newSegments.reduce((acc, segment, i) => {
      if (i === newSegments.length - 1) {
        return [...acc, segment.start.position, segment.end.position];
      }

      return [...acc, segment.start.position];
    }, []);

    const data = await $defaultFetch('/v1/geography/path/line-string', {
      query: {
        segments: edges.join(',')
      }
    });

    const overlay = new DeckOverlay({
      layers: [
        new ScatterplotLayer({
          id: 'stations',
          data: stationPoints,
          radiusScale: 1,
          radiusMinPixels: 5,
          getPosition: d => d.coordinates,
          getFillColor: [255, 0, 0],
          getRadius: 25,
        }),
        new GeoJsonLayer({
          id: 'line-string',
          data,
          stroked: true,
          getLineColor: [255, 0, 0],
          getLineWidth: 15,
          lineWidthScale: 1,
          lineWidthMinPixels: 3
        }),
      ]
    });

    map.map?.addControl(overlay as any);
    previousOverlay = overlay;

    map.map?.fitBounds([
      [data.properties.envelope[0], data.properties.envelope[1]],
      [data.properties.envelope[2], data.properties.envelope[3]],
    ], {
      padding: 100,
    });
  });
});

const onSubmit = handleSubmit(async (body) => {
  await $defaultFetch('/v1/journeys', {
    method: 'POST',
    body: {
      startDate: body.startDate.toISOString(),
      endDate: body.startDate.toISOString(),
      segments: body.segments,
      grade: Math.max(Math.round(body.reviewGrade * 2), 10),
      review: body.reviewNote,
      team: body.team,
    },
  }).then(() => {
    $toast.success('Journey saved', {description: 'The journey has been created sucessfully.'});
    return navigateTo('/journeys');
  }).catch(() => {
    $toast.error('Failed to create journey', {description: 'An error occurred while creating the journey.'});
  });
});

const keepPartOfArray = <T>(arr: T[]): T[] => {
  if (arr.length <= 5) {
    return arr;
  }

  if (arr.length <= 15) {
    return arr.filter((value, index) => {
      return index % 3 == 0 || index == arr.length - 1;
    });
  }

  return arr.filter((value, index) => {
    return index % 7 == 0 || index == arr.length - 1;
  });
};
</script>

<style scoped>
.create-content {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 3;
}

.form-section {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  width: 50%;
  padding: 12px 6px 12px 12px;
  overflow-y: auto;
  overflow-y: overlay;
}

.map-section {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  width: 50%;
  padding: 12px 12px 12px 6px;
  overflow: hidden;
}
</style>
