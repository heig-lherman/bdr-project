<template>
  <v-form
      fast-fail
      @submit.prevent="onSubmit"
  >
    <v-rating
        v-model="grade.value.value"
        :error-messages="grade.errorMessage.value"
        half-increments
        hover
        :length="5"
        :size="32"
        active-color="primary"
    />

    <v-textarea
        label="Review note"
        v-model="review.value.value"
        :error-messages="review.errorMessage.value"
        required
        :counter="2000"
    />

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

const emit = defineEmits(['create']);

const validationSchema = toTypedSchema(zod.object({
  grade: zod.number().min(0).max(5),
  review: zod.string().min(1, 'A review is required').max(2000)
}));

const {handleSubmit} = useForm({
  validationSchema,
});

const grade = useField('grade', { validationSchema });
const review = useField('review', { validationSchema });

const onSubmit = handleSubmit(async (body) => {
  emit('create', body);
});
</script>
