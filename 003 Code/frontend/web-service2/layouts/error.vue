<template>
  <v-container class="fill-height" fluid>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-alert type="error" dense>
          404 - 페이지를 찾을 수 없습니다!
        </v-alert>
        <v-card>
          <v-card-title>
            Oops! 페이지를 찾을 수 없습니다.
          </v-card-title>
          <v-card-text>
            요청한 페이지가 없거나 이동했을 수 있습니다.
          </v-card-text>
          <v-card-actions>
            <v-btn to="/" color="primary" text>홈으로 돌아가기</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
</script>

<style scoped>
/* 필요한 CSS 스타일링 */
</style>