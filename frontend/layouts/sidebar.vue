<template>
  <v-app>
    <LayoutAppHeader/>
    <v-main>
      <v-container>
        <v-row>
          <v-col cols="2">
            <LayoutAppSidebar/>
          </v-col>

          <v-col>
            <v-sheet
                min-height="70vh"
                rounded="lg"
            >
              <slot/>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>
