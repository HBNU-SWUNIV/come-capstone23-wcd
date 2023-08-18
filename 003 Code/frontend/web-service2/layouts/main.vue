<template>
  <SideNavDrawer/>
  <slot/>
</template>

<script>

import SideNavDrawer from "@/components/SideNavDrawer";

export default {
  components: {
    SideNavDrawer
  },
  setup() {

  }
}
</script>