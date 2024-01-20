<template>
  <v-card rounded="lg" elevation="8">
    <v-card-item>
      <v-card-title>Your active team: {{ team.name }}</v-card-title>
    </v-card-item>

    <v-row>
      <v-col cols="12" md="3">
        <v-card-subtitle>Team members</v-card-subtitle>
        <v-list lines="one" density="compact">
          <v-list-item
              v-for="member in team.users"
              :key="member.displayName"
              :title="`${member.firstName} ${member.lastName}`"
              :subtitle="member.displayName"
          />
        </v-list>
      </v-col>
      <v-col cols="12" md="9">
        <v-card-subtitle>Team statistics</v-card-subtitle>
        <v-card-text>
          <AtomsCantonsProgress :items="cantonsData" display="horizontal" />
        </v-card-text>
      </v-col>
    </v-row>
  </v-card>
</template>

<script setup lang="ts">
import { useDefaultFetch } from '#imports';

const props = defineProps({
  teamId: {
    type: Number,
    required: true
  }
});

const {data: team} = await useDefaultFetch('/v1/teams/{teamId}', {
  path: {teamId: props.teamId}
});

const {data: cantonsData} = useDefaultFetch('/v1/teams/{teamId}/progress/cantons', {
  path: {teamId: props.teamId},
});
</script>
