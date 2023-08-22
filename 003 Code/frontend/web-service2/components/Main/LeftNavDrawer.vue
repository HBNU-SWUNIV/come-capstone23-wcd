<template>
  <v-navigation-drawer
      width="244"
  >

    <v-list>
      <v-list-item
          v-for="n in 5"
          :key="n"
          :title="`Item ${ n }`"
          link
      >
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


<script>

</script>