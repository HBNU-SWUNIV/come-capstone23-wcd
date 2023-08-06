<template>
  <div class="PageNavArea mt-5 d-flex justify-content-center">
    <button class="btn btn-outline-secondary mx-1" @click="prevPage" :disabled="currentPage === 1">
      Previous
    </button>
    <div v-for="pageNumber in range" :key="pageNumber" class="PageBox">
      <button class="btn btn-outline-secondary mx-1" @click="changePage(pageNumber)" :class="{ 'btn-primary': currentPage === pageNumber }">
        {{ pageNumber }}
      </button>
    </div>
    <button class="btn btn-outline-secondary mx-1" @click="nextPage" :disabled="currentPage === totalPages">
      Next
    </button>
  </div>
</template>

<script>
export default {
  props: {
    currentPage: {
      type: Number,
      required: true
    },
    totalPages: {
      type: Number,
      required: true
    }
  },
  computed: {
    range() {
      return Array.from({ length: this.totalPages }, (_, i) => i + 1)
    }
  },
  methods: {
    prevPage() {
      if (this.currentPage > 1) {
        this.$emit('page-changed', this.currentPage - 1);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.$emit('page-changed', this.currentPage + 1);
      }
    },
    changePage(pageNumber) {
      if (pageNumber !== this.currentPage) {
        this.$emit('page-changed', pageNumber);
      }
    }
  }
};
</script>

<style>
/* Add some styling for the new pagination buttons */
.PageNavArea .btn {
  margin: 0 5px;
}
.PageBox {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>