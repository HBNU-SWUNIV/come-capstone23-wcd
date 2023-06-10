<template>
  <div class="item align-self-center" @submit.prevent="onSubmit">
    <div class="board-contents">
      <input
        type="text"
        class="form-control"
        placeholder="클럽명을 입력하세요."
      />
      <input
        type="text"
        class="form-control"
        placeholder="클럽설명을 입력하세요"
      />
    </div>
    <div class="board-contents">
      <textarea
        class="form-control"
        placeholder="내용을 입력하세요."
      ></textarea>
    </div>
    <div class="d-flex flex-row-reverse mt-3">
      <button type="button" class="btn">생성</button>
      <button type="button" class="btn">취소</button>
    </div>
  </div>
</template>