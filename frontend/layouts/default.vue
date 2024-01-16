<template>
  <v-app id="inspire">
    <v-app-bar flat>
      <v-container class="mx-auto d-flex align-center justify-center">
        <v-app-bar-title>
          <div class="tw-flex tw-items-center">
            <v-icon
                icon="i-ph:train-simple-duotone"
                class="me-4 "
                color="grey-darken-1"
                size="32"
            />

            <b>Choo</b>Choo
          </div>
        </v-app-bar-title>

        <v-btn
            v-for="link in links"
            :key="link"
            :text="link"
            variant="text"
        ></v-btn>

        <v-spacer></v-spacer>

        <v-responsive max-width="160">
          <v-text-field
              density="compact"
              flat
              hide-details
              label="Search"
              rounded="lg"
              single-line
              variant="solo-filled"
          ></v-text-field>
        </v-responsive>
      </v-container>
    </v-app-bar>

    <v-main>
      <v-container>
        <v-row>
          <v-col cols="2">
            <v-sheet rounded="lg">
              <v-list rounded="lg">
                <v-list-item
                    v-for="n in 5"
                    :key="n"
                    link
                    :title="`List Item ${n}`"
                ></v-list-item>

                <v-divider class="my-2"></v-divider>

                <v-list-item
                    color="grey-lighten-4"
                    link
                    title="Refresh"
                ></v-list-item>
              </v-list>
            </v-sheet>
          </v-col>

          <v-col>
            <v-sheet
                min-height="70vh"
                rounded="lg"
            >
              <!--  -->
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>
<script setup lang="ts">
</script>
