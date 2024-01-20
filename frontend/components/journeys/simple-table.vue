<template>
  <v-data-table
      :headers="headers"
      :items="data"
      :items-length="data?.length ?? 0"
      items-key="id"
      :loading="pending"
  >
    <template v-slot:item.startDate="{ item }">
      <span>{{ new Date(item.startDate).toLocaleString('fr') }}</span>
    </template>
    <template v-slot:item.endDate="{ item }">
      <span>{{ new Date(item.endDate).toLocaleString('fr') }}</span>
    </template>
    <template v-slot:item.grade="{ item }">
      <v-tooltip :text="item.review" activator="parent" location="start"/>
      <v-rating
          :model-value="(item.grade / 2).toFixed(1)"
          half-increments
          readonly=""
          :length="5"
          :size="32"
          active-color="primary"
      />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
const {data, pending} = await useDefaultFetch('/v1/journeys');
const headers = [
  {title: 'ID', key: 'id', sortable: true},
  {title: 'Start date', key: 'startDate', sortable: true},
  {title: 'End date', key: 'endDate', sortable: true},
  {title: 'Grade', key: 'grade', sortable: true},
  {title: '# Segments', key: 'segmentCount', sortable: true},
];
</script>
