<template>
  <v-data-table
      :headers="headers"
      :items="data"
      :items-length="data?.length ?? 0"
      items-key="id"
      :loading="pending"
  >
    <template v-slot:item.actions="{ item }">
      <v-btn
          v-if="currentTeam !== item.id"
          color="secondary"
          prepend-icon="i-tabler:users-plus"
          text="Join"
          small
          class="ms-2"
          @click="onJoinTeam(item.id)"
      />
      <v-btn
          v-else
          color="error"
          prepend-icon="i-tabler:users-minus"
          text="Leave"
          small
          class="ms-2"
          @click="onLeaveTeam"
      />
    </template>
  </v-data-table>
</template>

<script setup lang="ts">
const {$defaultFetch, $toast} = useNuxtApp();

const props = defineProps({
  currentTeam: {
    type: Number,
    default: null,
  }
});

const {data, pending, refresh} = await useDefaultFetch('/v1/teams');
const {getSession: refreshUser} = useAuth();

const headers = [
  {title: 'ID', key: 'id', sortable: true},
  {title: 'Team name', key: 'name', sortable: true},
  {title: '# Members', key: 'userCount', sortable: true},
  {title: 'Actions', key: 'actions', sortable: false}
];

async function onJoinTeam(teamId: number) {
  await $defaultFetch('/v1/teams/{teamId}/join', {
    method: 'POST',
    path: {teamId},
  }).then(async () => {
    $toast.success('Team joined!', { description: 'You have successfully joined the team.' });
    await refresh();
    await refreshUser();
  }).catch(() => {
    $toast.error('Team could not be joined', { description: 'An error occurred while attempting to join the team.' });
  });
}

async function onLeaveTeam() {
  await $defaultFetch('/v1/teams/leave', {
    method: 'POST',
  }).then(async () => {
    $toast.success('Disbanded from team', { description: 'You have successfully left the team.' });
    await refresh();
    await refreshUser();
  }).catch(() => {
    $toast.error('Team could not be left', { description: 'An error occurred while attempting to leave the team.' });
  });
}

defineExpose({
  refresh: () => refresh(),
})
</script>
