<template>
  <v-app>
    <LayoutAppHeader></LayoutAppHeader>

    <v-main>
      <v-container class="h-100">
        <slot/>
      </v-container>
    </v-main>
  </v-app>
</template>
