package com.wcd.boardservice.service;

import com.wcd.boardservice.client.UserServiceClient;
import com.wcd.boardservice.dto.user.RequestUserNamesDto;
import com.wcd.boardservice.dto.user.ResponseUserNamesDto;
import com.wcd.boardservice.dto.vote.ResponseVoteDto;
import com.wcd.boardservice.dto.vote.voteitem.RequestVoteItemDto;
import com.wcd.boardservice.dto.vote.voteitem.ResponseVoteItemDto;
import com.wcd.boardservice.dto.vote.voterecord.ResponseVoteRecordDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService{
    PostRepository postRepository;
    VoteRepository voteRepository;
    VoteItemRepository voteItemRepository;
    VoteService voteService;
    ModelMapper modelMapper;
    UserServiceClient userServiceClient;
    ClubServiceClient clubServiceClient;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           VoteRepository voteRepository,
                           VoteItemRepository voteItemRepository,
                           VoteService voteService,
                           ModelMapper modelMapper,
                           UserServiceClient userServiceClient,
                           ClubServiceClient clubServiceClient) {
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
        this.voteItemRepository = voteItemRepository;
        this.voteService = voteService;
        this.modelMapper = modelMapper;
        this.userServiceClient = userServiceClient;
        this.clubServiceClient = clubServiceClient;
    }

    @Override
    public ResponsePostDto createPost(Long clubId, Long writerId, RequestPostDto requestPostDto) {
        try {
            // PostDto를 Post 엔티티로 변환
            Post newPost = modelMapper.map(requestPostDto, Post.class);
            newPost.setClubId(clubId);
            newPost.setWriterId(writerId);
            Post savedPost = postRepository.save(newPost);
            ResponsePostDto responsePostDto = modelMapper.map(savedPost, ResponsePostDto.class);

            // Call userServiceClient to get user names.
            List writerIds = new ArrayList();
            writerIds.add(writerId);
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);
            responsePostDto.setWriterName(writerIdToNameMap.get(writerId));

            // 만약 PostDto에 VoteDto가 포함되어 있다면,
            if (requestPostDto.getRequestVoteDto() != null) {
                Vote newVote = modelMapper.map(requestPostDto.getRequestVoteDto(), Vote.class);
                newVote.setPost(savedPost);

                Vote saveVote = voteRepository.save(newVote);
                ResponseVoteDto responseVoteDto = modelMapper.map(saveVote, ResponseVoteDto.class);

                List<ResponseVoteItemDto> responseVoteItemDtos = new ArrayList<>();
                for(RequestVoteItemDto requestVoteItemDto : requestPostDto.getRequestVoteDto().getRequestVoteItemDtos()) {
                    VoteItem voteItem = modelMapper.map(requestVoteItemDto, VoteItem.class);
                    voteItem.setVote(newVote);
                    VoteItem savedVoteItem = voteItemRepository.save(voteItem);
                    responseVoteItemDtos.add(modelMapper.map(savedVoteItem, ResponseVoteItemDto.class));
                }
                responseVoteDto.setResponseVoteItemDtos(responseVoteItemDtos);
                responsePostDto.setResponseVoteDto(responseVoteDto);
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

            if (requestPostDto.getRequestVoteDto().equals(null)) {
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
    public void deletePost(Long writerId, Long postId) {
        try {
            Post deletePost = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
            if (!deletePost.getWriterId().equals(writerId)) {
                throw new Exception();
            }
            postRepository.delete(deletePost);
        } catch (NoSuchElementException e) {

        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public ResponsePostDto getPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());

            // Call userServiceClient to get user names.
            List writerIds = new ArrayList();
            writerIds.add(post.getWriterId());
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

            ResponsePostDto responsePostDto = modelMapper.map(post, ResponsePostDto.class);
            responsePostDto.setWriterName(writerIdToNameMap.get(post.getWriterId()));

            if (responsePostDto.getResponseVoteDto() != null) {
                responsePostDto.setResponseVoteDto(modelMapper.map(post.getVote(), ResponseVoteDto.class));

                List<ResponseVoteItemDto> responseVoteItemDtos = new ArrayList<>();
                for (VoteItem voteItem : post.getVote().getVoteItems()){
                    ResponseVoteItemDto responseVoteItemDto = modelMapper.map(voteItem, ResponseVoteItemDto.class);

                    List<ResponseVoteRecordDto> responseVoteRecordDtos = new ArrayList<>();
                    for (VoteRecord voteRecord : voteItem.getVoteRecords()) {
                        ResponseVoteRecordDto responseVoteRecordDto = modelMapper.map(voteRecord, ResponseVoteRecordDto.class);
                        responseVoteRecordDtos.add(responseVoteRecordDto);
                    }
                    responseVoteItemDto.setResponseVoteRecordDtos(responseVoteRecordDtos);

                    responseVoteItemDtos.add(responseVoteItemDto);
                }
                responsePostDto.getResponseVoteDto().setResponseVoteItemDtos(responseVoteItemDtos);
            }

            return responsePostDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getAllPost(Pageable pageable) {
        try {
            Page<Post> postLists= postRepository.findAll(pageable);

            // Collect userIds from post lists.
            List<Long> writerIds = postLists.stream()
                    .map(Post::getWriterId)
                    .distinct()
                    .collect(Collectors.toList());

            // Call userServiceClient to get user names.
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

            // Convert each post to ResponsePostListDto and replace userId with userName.
            Page<ResponsePostListDto> responsePostListDtos = postLists.map(postList -> {
                ResponsePostListDto dto = modelMapper.map(postList, ResponsePostListDto.class);
                dto.setWriterName(writerIdToNameMap.get(postList.getWriterId()));
                return dto;
            });

            return responsePostListDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getClubAllPost(Long clubId, Pageable pageable) {
        try {
            Page<Post> postLists = postRepository.findByclubId(clubId, pageable);

            // Collect userIds from post lists.
            List<Long> writerIds = postLists.stream()
                    .map(Post::getWriterId)
                    .distinct()
                    .collect(Collectors.toList());

            // Call userServiceClient to get user names.
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

            // Convert each post to ResponsePostListDto and replace userId with userName.
            Page<ResponsePostListDto> responsePostListDtos = postLists.map(postList -> {
                ResponsePostListDto dto = modelMapper.map(postList, ResponsePostListDto.class);
                dto.setWriterName(writerIdToNameMap.get(postList.getWriterId()));
                return dto;
            });

            return responsePostListDtos;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public Page<ResponsePostListDto> getUserAllPost(Long userId, Pageable pageable) {
        try {
            Page<Post> postLists = postRepository.findBywriterId(userId, pageable);

            // Collect userIds from post lists.
            List<Long> writerIds = postLists.stream()
                    .map(Post::getWriterId)
                    .distinct()
                    .collect(Collectors.toList());

            // Call userServiceClient to get user names.
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

            // Convert each post to ResponsePostListDto and replace userId with userName.
            Page<ResponsePostListDto> responsePostListDtos = postLists.map(postList -> {
                ResponsePostListDto dto = modelMapper.map(postList, ResponsePostListDto.class);
                dto.setWriterName(writerIdToNameMap.get(postList.getWriterId()));
                return dto;
            });

            return responsePostListDtos;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<Long, String> getUserNames(List userIds) {
        RequestUserNamesDto requestUserNamesDto = new RequestUserNamesDto(userIds);
        ResponseUserNamesDto responseUserNamesDto = new ResponseUserNamesDto();
        responseUserNamesDto.setUserNames(userServiceClient.getUserNames(requestUserNamesDto).getUserNames());

        Map<Long, String> writerIdToNameMap = responseUserNamesDto.getUserNames().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey()),
                        Map.Entry::getValue
                ));

        return writerIdToNameMap;
    }
}
