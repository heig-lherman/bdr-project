<template>
  <div class="d-flex justify-center align-center h-100">
    <v-card
        elevation="12"
        max-width="70rem"
        rounded="lg"
        class="w-100"
    >
      <v-row>
        <v-col>
          <v-card-text class="d-flex flex-column justify-center align-center h-100">
            <v-icon icon="i-ph:user-duotone" size="2.1rem" class="mb-4"/>
            <h1 class="text-h5">Welcome back</h1>
            <span class="mt-2 font-weight-light">Please enter your details</span>

            <v-form
                class="mt-10 w-100 d-flex justify-center"
                fast-fail
                @submit.prevent="onSubmit"
            >
              <v-responsive max-width="20rem">
                <v-text-field
                    v-model="username.value.value"
                    :error-messages="username.errorMessage.value"
                    label="Email"
                    type="email"
                    required
                    hide-details
                ></v-text-field>

                <v-text-field
                    v-model="password.value.value"
                    :error-messages="password.errorMessage.value"
                    label="Password"
                    type="password"
                    required
                    hide-details
                    class="mt-4"
                ></v-text-field>

                <v-btn
                    variant="tonal"
                    type="submit"
                    class="mt-4"
                    block
                >
                  Submit
                </v-btn>
              </v-responsive>
            </v-form>
          </v-card-text>
        </v-col>
        <v-col lg="5" class="d-none d-lg-flex">
          <AtomsUnsplashImage
              src="https://images.unsplash.com/uploads/1413387158190559d80f7/6108b580"
              :height="600"
              cover
          />
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import * as zod from 'zod';

definePageMeta({
  layout: 'login',
  auth: {
    unauthenticatedOnly: true,
    navigateAuthenticatedTo: '/'
  }
});
useHead({
  title: 'Sign-in'
});

const {$toast} = useNuxtApp();
const {signIn} = useAuth();

const validationSchema = toTypedSchema(zod.object({
  username: zod.string().min(1, 'An email is required').email('Must be a valid email'),
  password: zod.string().min(8, 'Password is required')
}));

const {handleSubmit} = useForm({
  validationSchema,
});

const username = useField('username', validationSchema);
const password = useField('password', validationSchema);

const onSubmit = handleSubmit(async (values) => signIn(values, {
  callbackUrl: '/',
}).catch(() => {
  $toast.error('Login failed', { description: 'An error occurred.' });
}));
</script>
