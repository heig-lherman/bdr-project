<template>
  <div class="d-flex flex-row justify-space-between align-end mb-6">
    <h1 class="text-h2">Teams</h1>
    <v-dialog v-model="createDialog" max-width="500">
      <template v-slot:activator="{props}">
        <v-btn
            v-if="!inTeam"
            color="secondary"
            prepend-icon="i-ph:users-duotone"
            v-bind="props"
        >
          Create a team
        </v-btn>
      </template>

      <v-card title="Create a team">
        <v-card-text>
          <TeamsCreationForm
            @create="() => {
              createDialog = false;
              table?.refresh?.();
              getSession();
            }"
          />
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>

  <v-alert v-if="!inTeam" type="info" variant="tonal" border>
    You are not in a team. You can either create a new one or join an existing one below.
  </v-alert>
  <TeamsTeamCard
      v-else
      :team-id="data.teamId"
  />

  <v-sheet rounded="lg" elevation="4">
    <TeamsSimpleTable
        class="mt-4"
        :current-team="data.teamId"
        ref="table"
    />
  </v-sheet>
</template>

<script setup lang="ts">
import type { TeamsSimpleTable } from '#components';

useHead({
  title: 'Teams'
});

const {data, getSession} = useAuth();

const inTeam = computed(() => !!data.value?.teamId);

const createDialog = ref(false);
const table = ref<InstanceType<typeof TeamsSimpleTable>>(null);
</script>
