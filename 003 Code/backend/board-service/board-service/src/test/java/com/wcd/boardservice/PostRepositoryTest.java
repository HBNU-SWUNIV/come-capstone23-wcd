//package com.wcd.boardservice;
//
//import com.wcd.boardservice.entity.Post;
//import com.wcd.boardservice.repository.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class PostRepositoryTest {
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    public void saveNewPost() {
//        Post post = new Post("가입인사", 1L, 1L, "안녕하세요", "잘부탁드려요");
//        postRepository.save(post);
//    }
//
//    @Test
//    public void deletePost() {
//        Post post = new Post("가입인사", 1L, 1L, "안녕하세요", "잘부탁드려요");
//        Post savedPost = postRepository.save(post);
//        postRepository.delete(savedPost);
//    }
//
//    @Test
//    public void updatePost() {
//        Post post = new Post("가입인사", 1L, 1L, "안녕하세요", "잘부탁드려요");
//        Post savedPost = postRepository.save(post);
//        savedPost.changeTitle("다시 안녕하세요");
//        savedPost.changeContent("요로시쿠");
//        postRepository.save(savedPost);
//    }
//}
