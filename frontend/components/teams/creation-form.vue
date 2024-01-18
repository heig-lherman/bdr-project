<template>
  <v-form
      fast-fail
      @submit.prevent="onSubmit"
  >
    <v-text-field
        v-model="name.value.value"
        :error-messages="name.errorMessage.value"
        label="Team name"
        required
        :counter="255"
    ></v-text-field>

    <div class="d-flex justify-end">
      <v-btn
          variant="tonal"
          type="submit"
          class="mt-4"
          color="primary"
      >
        Submit
      </v-btn>
    </div>
  </v-form>
</template>

<script setup lang="ts">
import * as zod from 'zod';

const { $defaultFetch, $toast } = useNuxtApp();

const emit = defineEmits(['create']);

const validationSchema = toTypedSchema(zod.object({
  name: zod.string().min(1, 'A name is required').max(255),
}));

const {handleSubmit} = useForm({
  validationSchema,
});

const name = useField('name', validationSchema);

const onSubmit = handleSubmit(async (body) => {
  await $defaultFetch('/v1/teams', {
    method: 'POST',
    body,
  }).then((result) => {
    $toast.success('Team created');
    emit('create', result);
  });
});
</script>
