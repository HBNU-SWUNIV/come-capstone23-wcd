<template>
  <div class="item align-self-center" id="search_area">
    <div class="d-flex flex-row justify-content-center">
      <select class="form-select" id="notice_filter">
        <option selected>필터</option>
        <option value="title">제목</option>
        <option value="writer">작성자</option>
        <option value="date">날짜</option>
      </select>

      <input type="text" class="form-control" id="notice_find" />

      <button class="btn" type="submit" id="notice_search">검색</button>
    </div>
  </div>

  <div class="row-vh d-flex flex-row align-items-center" id="notice_area">
    <div style="margin-left: 200px">NO.</div>
    <div style="margin-left: 500px">제목</div>
    <div style="margin-left: 450px">날짜</div>
  </div>

  <div class="" id="contents_area">
    
  </div>
</template>

<script>
export default {};
</script>

<style>
#search_area {
  width: 1500px;
  height: 140px;
}
#notice_area {
  width: 1500px;
  height: 40px;
  background-color: #383838;
  color: white;
  font-size: 18px;
}
#contents_area {
  width: 1500px;
  height: 1000px;
}
#notice_filter {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 40px;
}
#notice_search {
  width: 95px;
  height: 40px;
  text-align: center;
  background-color: #383838;
  color: white;
  border: none;
  border-radius: 0.25rem;
  margin-top: 40px;
}
#notice_find {
  width: 640px;
  height: 40px;
}
</style>