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
            <v-icon icon="i-ph:user-plus-duotone" size="2.1rem" class="mb-4"/>
            <h1 class="text-h5">Hello!</h1>
            <span class="mt-2 font-weight-light">Please enter your details for registration</span>

            <v-form
                class="mt-10 w-100 d-flex justify-center"
                fast-fail
                @submit.prevent="onSubmit"
            >
              <v-responsive max-width="20rem">
                <div class="d-flex flex-column ga-2">
                  <v-text-field
                      v-model="username.value.value"
                      :error-messages="username.errorMessage.value"
                      label="Email"
                      type="email"
                      required
                      hide-details
                  ></v-text-field>

                  <v-text-field
                      v-model="displayName.value.value"
                      :error-messages="displayName.errorMessage.value"
                      label="Display name"
                      required
                      hide-details
                  ></v-text-field>

                  <v-text-field
                      v-model="firstName.value.value"
                      :error-messages="firstName.errorMessage.value"
                      label="First name"
                      required
                      hide-details
                  ></v-text-field>

                  <v-text-field
                      v-model="lastName.value.value"
                      :error-messages="lastName.errorMessage.value"
                      label="Last name"
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
                  ></v-text-field>

                  <v-btn
                      variant="tonal"
                      type="submit"
                      block
                  >
                    Submit
                  </v-btn>
                </div>
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

useHead({
  title: 'Sign-up'
});

definePageMeta({
  layout: 'login',
  auth: {
    unauthenticatedOnly: true,
    navigateAuthenticatedTo: '/'
  }
});

const {signUp} = useAuth();

const validationSchema = toTypedSchema(zod.object({
  username: zod.string().min(1, 'An email is required').email('Must be a valid email'),
  displayName: zod.string().min(1, 'A display name is required'),
  firstName: zod.string().min(1, 'A first name is required'),
  lastName: zod.string().min(1, 'A last name is required'),
  password: zod.string().min(8, 'Password is required')
}));

const {handleSubmit} = useForm({
  validationSchema,
});

const username = useField('username', validationSchema);
const displayName = useField('displayName', validationSchema);
const firstName = useField('firstName', validationSchema);
const lastName = useField('lastName', validationSchema);
const password = useField('password', validationSchema);

const onSubmit = handleSubmit(async (values) => signUp(values, {
  callbackUrl: '/',
}));
</script>
