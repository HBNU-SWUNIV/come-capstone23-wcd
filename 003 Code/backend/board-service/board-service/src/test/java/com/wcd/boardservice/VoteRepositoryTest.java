package com.wcd.boardservice;

import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.entity.Vote;
import com.wcd.boardservice.repository.PostRepository;
import com.wcd.boardservice.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VoteRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    VoteRepository voteRepository;

    @Test
    public void saveVoteTest() {
        Post post = new Post("가입인사", 1L, 1L, "안녕하세요", "잘부탁드려요");
        Vote vote = new Vote(null, post, 1L, 1L, null, null, null, false, false, false);
        Vote savedVote = voteRepository.save(vote);
    }
}
