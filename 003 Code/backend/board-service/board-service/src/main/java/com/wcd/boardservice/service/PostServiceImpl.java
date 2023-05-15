package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.vote.VoteDto;
import com.wcd.boardservice.dto.vote.voteitem.VoteItemDto;
import com.wcd.boardservice.dto.vote.voterecord.VoteRecordDto;
import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.RequestPostDto;
import com.wcd.boardservice.dto.post.ResponsePostDto;
import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.entity.Vote;
import com.wcd.boardservice.entity.VoteItem;
import com.wcd.boardservice.entity.VoteRecord;
import com.wcd.boardservice.repository.PostRepository;
import com.wcd.boardservice.repository.VoteItemRepository;
import com.wcd.boardservice.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService{
    @Autowired
    PostRepository postRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    VoteItemRepository voteItemRepository;
    @Autowired
    VoteService voteService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponsePostDto createPost(RequestPostDto requestPostDto) {
        try {
            // PostDto를 Post 엔티티로 변환
            Post newPost = modelMapper.map(requestPostDto, Post.class);

            Post savedPost = postRepository.save(newPost);
            ResponsePostDto responsePostDto = modelMapper.map(savedPost, ResponsePostDto.class);

            // 만약 PostDto에 VoteDto가 포함되어 있다면,
            if (requestPostDto.getVoteDto() != null) {
                Vote newVote = modelMapper.map(requestPostDto.getVoteDto(), Vote.class);
                newVote.setPost(savedPost);

                Vote saveVote = voteRepository.save(newVote);
                VoteDto savedVoteDto = modelMapper.map(saveVote, VoteDto.class);

                List<VoteItemDto> voteItemDtos = new ArrayList<>();
                for(VoteItemDto voteItemDto : requestPostDto.getVoteDto().getVoteItemDtos()) {
                    VoteItem voteItem = modelMapper.map(voteItemDto, VoteItem.class);
                    voteItem.setVote(newVote);
                    VoteItem savedVoteItem = voteItemRepository.save(voteItem);
                    voteItemDtos.add(modelMapper.map(savedVoteItem, VoteItemDto.class));
                }
                savedVoteDto.setVoteItemDtos(voteItemDtos);
                responsePostDto.setVoteDto(savedVoteDto);
            }

            return responsePostDto;
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponsePostDto updatePost(Long postId, Long userId, RequestPostDto requestPostDto) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());

            if (!post.getWriterId().equals(userId)) {
                throw new Exception();
            }

            if (requestPostDto.getVoteDto().equals(null)) {
//                voteService.deleteVote(post.getVote().getId(), userId);
            }

            post = modelMapper.map(requestPostDto, Post.class);

            Post updatePost = postRepository.save(post);
            ResponsePostDto responsePostDto = modelMapper.map(updatePost, ResponsePostDto.class);

            return responsePostDto;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        try {
            Post deletePost = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
            if (!deletePost.getWriterId().equals(userId)) {
                throw new Exception("");
            }
            postRepository.delete(deletePost);
        } catch (NoSuchElementException e) {

        } catch (Exception e) {

        }
    }

    @Override
    public ResponsePostDto getPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
            ResponsePostDto responsePostDto = modelMapper.map(post, ResponsePostDto.class);
            responsePostDto.setVoteDto(modelMapper.map(post.getVote(), VoteDto.class));
            List<VoteItemDto> voteItemDtos = new ArrayList<>();
            for (VoteItem voteItem : post.getVote().getVoteItems()){
                VoteItemDto voteItemDto = modelMapper.map(voteItem, VoteItemDto.class);

                List<VoteRecordDto> voteRecordDtos = new ArrayList<>();
                for (VoteRecord voteRecord : voteItem.getVoteRecords()) {
                    VoteRecordDto voteRecordDto = modelMapper.map(voteRecord, VoteRecordDto.class);
                    voteRecordDtos.add(voteRecordDto);
                }
                voteItemDto.setVoteRecordDtos(voteRecordDtos);

                voteItemDtos.add(voteItemDto);
            }
            responsePostDto.getVoteDto().setVoteItemDtos(voteItemDtos);

            return responsePostDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getAllPost(Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findAll(pageable);
            List<ResponsePostListDto> responsePostListDtos = postList.stream()
                    .map(post -> modelMapper.map(post, ResponsePostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(responsePostListDtos, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getAllClubPost(Long clubId, Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findByclubId(clubId, pageable);
            List<ResponsePostListDto> responsePostListDtos = postList.stream()
                    .map(post -> modelMapper.map(post, ResponsePostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(responsePostListDtos, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getAllUserPost(Long userId, Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findBywriterId(userId, pageable);
            List<ResponsePostListDto> responsePostListDtos = postList.stream()
                    .map(post -> modelMapper.map(post, ResponsePostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(responsePostListDtos, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }
}
