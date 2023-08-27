//package com.wcd.boardservice;
//
//import com.wcd.boardservice.dto.vote.VoteDto;
//import com.wcd.boardservice.dto.vote.voteitem.VoteItemDto;
//import com.wcd.boardservice.dto.post.PostDto;
//import com.wcd.boardservice.entity.VoteItem;
//import com.wcd.boardservice.service.PostService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest(classes = BoardServiceApplication.class)
//public class PostServiceTest {
//    @Autowired
//    PostService postService;
//    @Autowired
//    ModelMapper modelMapper;
//
//    @Test
//    public void createPostTest() {
//        PostDto newPostDto = new PostDto(null , null, null, "가입인사", 1L, 1L, "안녕하세요", "잘부탁드려요", null);
//        PostDto savePostDto = postService.createPost(newPostDto);
//
//        PostDto savedPostDto = postService.getPostById(savePostDto.getId());
//
//        assertThat(savePostDto).isEqualTo(savedPostDto);
//    }
//
//    @Test
//    public void createPostWithVoteWithVoteItemsTest() {
//        LocalDateTime deadline = LocalDateTime.now().plusHours(1);
//        List<VoteItemDto> voteItemDtos = new ArrayList<>();
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "1번"), VoteItemDto.class));
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "2번"), VoteItemDto.class));
//        VoteDto newVoteDto = new VoteDto(null, null, null, deadline, false, false, false, voteItemDtos, null);
//        PostDto newPostDto = new PostDto(null , null, null, "투표", 1L, 1L, "안녕하세요", "잘부탁드려요", newVoteDto);
//        PostDto savedPostDto = postService.createPost(newPostDto);
//        PostDto findPostDto = postService.getPostById(savedPostDto.getId());
//        assertThat(savedPostDto).isEqualTo(findPostDto);
//    }
//
//    @Test
//    public void deletePostTest() {
//        LocalDateTime deadline = LocalDateTime.now().plusHours(1);
//        List<VoteItemDto> voteItemDtos = new ArrayList<>();
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "1번"), VoteItemDto.class));
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "2번"), VoteItemDto.class));
//        VoteDto newVoteDto = new VoteDto(null, null, null, deadline, false, false, false, voteItemDtos, null);
//        PostDto newPostDto = new PostDto(null , null, null, "투표", 1L, 1L, "안녕하세요", "잘부탁드려요", newVoteDto);
//        PostDto savedPostDto = postService.createPost(newPostDto);
//        postService.deletePost(savedPostDto.getWriterId(), savedPostDto.getId());
//    }
//
//    @Test
//    public void getPostByIdTest() {
//        LocalDateTime deadline = LocalDateTime.now().plusHours(1);
//        List<VoteItemDto> voteItemDtos = new ArrayList<>();
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "1번"), VoteItemDto.class));
//        voteItemDtos.add(modelMapper.map(new VoteItem(null, null, null, "2번"), VoteItemDto.class));
//        VoteDto newVoteDto = new VoteDto(null, null, null, deadline, false, false, false, voteItemDtos, null);
//        PostDto newPostDto = new PostDto(null , null, null, "투표", 1L, 1L, "안녕하세요", "잘부탁드려요", newVoteDto);
//        PostDto savedPostDto = postService.createPost(newPostDto);
//
//        PostDto findPostDto = postService.getPostById(savedPostDto.getId());
//        assertThat(savedPostDto).isEqualTo(findPostDto);
//    }
//}
