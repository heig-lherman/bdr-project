<template>
  <v-data-table
      :headers="headers"
      :items="data"
      :items-length="data?.length ?? 0"
      items-key="id"
      :loading="pending"
  >
    <template v-slot:item.grade="{ item }">
      <template v-if="!!item.grade">
        <v-tooltip :text="item.comment" activator="parent" location="start"/>
        <v-rating
            :model-value="(item.grade / 2).toFixed(1)"
            half-increments
            readonly=""
            :length="5"
            :size="32"
            active-color="primary"
        />
      </template>
      <template v-else>
        <v-btn
            v-if="!item.grade"
            color="secondary"
            variant="tonal"
            prepend-icon="i-ph:shooting-star-duotone"
            text="Review this line"
            @click="() => {
            createReviewItem = item;
            createReviewDialog = true;
          }"
        />
      </template>
    </template>
  </v-data-table>

  <v-dialog v-model="createReviewDialog" max-width="750" theme="dark">
    <v-card title="Create a team">
      <v-card-text>
        <ReviewCreationForm @create="($event) => onSubmit($event, createReviewItem.line)"/>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
const {$defaultFetch, $toast} = useNuxtApp();

const createReviewDialog = ref(false);
const createReviewItem = ref<any>(null);

const {data, pending, refresh} = await useDefaultFetch('/v1/reviews');

const headers = [
  {title: 'Line ID', key: 'line', sortable: true},
  {title: 'Line name', key: 'lineName', sortable: true},
  {title: 'First station', key: 'stationStart', sortable: true},
  {title: 'Last station', key: 'stationEnd', sortable: true},
  {title: 'Grade', key: 'grade', sortable: false},
];

async function onSubmit($event: any, lineId: number) {
  await $defaultFetch('/v1/reviews', {
    method: 'POST',
    body: {
      line: lineId,
      grade: Math.round($event.grade * 2),
      review: $event.review,
    },
  }).then(() => {
    $toast.success('Team created');
    createReviewDialog.value = false;
    refresh();
  });
}

defineExpose({
  refresh,
});
</script>
