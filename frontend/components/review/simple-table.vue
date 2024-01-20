<template>
  <v-data-table-server
      :headers="headers"
      :items="data"
      :items-length="data?.length ?? 0"
      items-key="id"
      :loading="pending"
  >
  </v-data-table-server>
</template>

<script setup lang="ts">
const {$defaultFetch, $toast} = useNuxtApp();

const props = defineProps({
  currentTeam: {
    type: Number,
    default: null,
  }
});

const {data, pending, refresh} = await useDefaultFetch('/v1/review');
const {getSession: refreshUser} = useAuth();

const headers = [
  {title: 'ID', key: 'id', sortable: true},
  {title: 'Team name', key: 'name', sortable: true},
  {title: '# Members', key: 'userCount', sortable: true},
  {title: 'Actions', key: 'actions', sortable: false}
];


defineExpose({
  refresh,
})
</script>
