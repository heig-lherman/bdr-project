<template>
  <v-app-bar flat>
    <v-container class="mx-auto d-flex align-center justify-center">
      <nuxt-link
          to="/"
          class="d-flex align-center me-4"
          style="text-decoration: none; color: inherit;"
      >
        <v-icon
            icon="i-ph:train-simple-duotone"
            class="me-4"
            color="grey-lighten-1"
            size="32"
        />

        <b>Choo</b>Choo
      </nuxt-link>

      <nuxt-link
          v-for="link in links"
          :key="link.path"
          :to="link.path"
          custom
      >
        <v-btn
            :text="link.label"
            :to="link.path"
            variant="text"
            class="me-2"
        ></v-btn>
      </nuxt-link>

      <v-spacer></v-spacer>

      <template v-if="status == 'authenticated'">
        <span>{{ data.firstName }} {{ data.lastName }} ({{ data.displayName }})</span>
        <v-btn icon="i-ph:sign-out" class="ms-2" @click="signOut({ callbackUrl: '/auth/login' })"/>
      </template>
      <template v-else>
        <nuxt-link to="/auth/register" custom>
          <v-btn
              text="Sign-up"
              to="/auth/register"
              variant="text"
              class="me-2"
          ></v-btn>
        </nuxt-link>
        <nuxt-link to="/auth/login" custom>
          <v-btn
              text="Sign-in"
              to="/auth/login"
              variant="text"
          ></v-btn>
        </nuxt-link>
      </template>
    </v-container>
  </v-app-bar>
</template>

<script setup lang="ts">
const links = [
  {path: '/', label: 'Home'},
  {path: '/teams', label: 'Teams'}
];

const {data, status, signOut} = useAuth();
</script>
