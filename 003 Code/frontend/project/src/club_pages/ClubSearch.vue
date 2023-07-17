<template>
  <div class="item align-self-center" id="search_area">
      <div class="d-flex flex-row justify-content-center">
        <select class="form-select" id="board_filter">
          <option selected>필터</option>
          <option value="title">제목</option>
          <option value="writer">작성자</option>
          <option value="date">날짜</option>
        </select>

        <input type="text" class="search-input" id="board_find" placeholder="클럽 이름 또는 원하는 카테고리를 검색하세요."/>

        <button class="btn" type="submit" id="board_search">
          검색
        </button>
      </div>
    </div>
</template>