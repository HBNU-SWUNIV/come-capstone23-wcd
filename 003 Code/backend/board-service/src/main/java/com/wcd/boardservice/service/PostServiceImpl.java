package com.wcd.boardservice.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.wcd.boardservice.client.UserServiceClient;
import com.wcd.boardservice.dto.post.*;
import com.wcd.boardservice.dto.user.RequestUserNamesDto;
import com.wcd.boardservice.dto.user.ResponseUserNamesDto;
import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.exception.UnAuthorizedEditException;
import com.wcd.boardservice.repository.PostRepository;
import com.wcd.boardservice.repository.VoteItemRepository;
import com.wcd.boardservice.repository.VoteRepository;
import com.wcd.boardservice.specs.PostSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    VoteRepository voteRepository;
    VoteItemRepository voteItemRepository;
    VoteService voteService;
    UserServiceClient userServiceClient;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           VoteRepository voteRepository,
                           VoteItemRepository voteItemRepository,
                           VoteService voteService,
                           UserServiceClient userServiceClient) {
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
        this.voteItemRepository = voteItemRepository;
        this.voteService = voteService;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Long createPost(Long clubId, Long writerId, RequestPostDto requestPostDto) {
        try {
            Post newPost = requestPostDto.toEntity(clubId, writerId);
            Post savedPost = postRepository.save(newPost);

//            // 만약 PostDto에 VoteDto가 포함되어 있다면,
//            if (requestPostDto.getRequestVoteDto() != null) {
//                Vote newVote = modelMapper.map(requestPostDto.getRequestVoteDto(), Vote.class);
//                newVote.setPost(savedPost);
//
//                Vote saveVote = voteRepository.save(newVote);
//                ResponseVoteDto responseVoteDto = modelMapper.map(saveVote, ResponseVoteDto.class);
//
//                List<ResponseVoteItemDto> responseVoteItemDtos = new ArrayList<>();
//                for(RequestVoteItemDto requestVoteItemDto : requestPostDto.getRequestVoteDto().getRequestVoteItemDtos()) {
//                    VoteItem voteItem = modelMapper.map(requestVoteItemDto, VoteItem.class);
//                    voteItem.setVote(newVote);
//                    VoteItem savedVoteItem = voteItemRepository.save(voteItem);
//                    responseVoteItemDtos.add(modelMapper.map(savedVoteItem, ResponseVoteItemDto.class));
//                }
//                responseVoteDto.setResponseVoteItemDtos(responseVoteItemDtos);
//                responsePostDto.setResponseVoteDto(responseVoteDto);
//            }

            return savedPost.getId();
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponsePostDto updatePost(Long postId, Long writerId, UpdateRequestPostDto updateRequestPostDto) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());

            if (!post.getWriterId().equals(writerId)) {
                throw new UnAuthorizedEditException("User " + writerId + " is not authorized to edit post " + postId);
            }

            post.update(updateRequestPostDto);

            List writerIds = new ArrayList();
            writerIds.add(writerId);
            Map<Long, String> writerIdToNameMap = getUserNames(writerIds);
            ResponsePostDto responsePostDto = post.toResponsePostDto(writerIdToNameMap.get(writerId));

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
                throw new UnAuthorizedEditException("User " + writerId + " is not authorized to edit post " + postId);
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

            ResponsePostDto responsePostDto = post.toResponsePostDto(writerIdToNameMap.get(post.getWriterId()));

//            if (responsePostDto.getResponseVoteDto() != null) {
//                responsePostDto.setResponseVoteDto(modelMapper.map(post.getVote(), ResponseVoteDto.class));
//
//                List<ResponseVoteItemDto> responseVoteItemDtos = new ArrayList<>();
//                for (VoteItem voteItem : post.getVote().getVoteItems()){
//                    ResponseVoteItemDto responseVoteItemDto = modelMapper.map(voteItem, ResponseVoteItemDto.class);
//
//                    List<ResponseVoteRecordDto> responseVoteRecordDtos = new ArrayList<>();
//                    for (VoteRecord voteRecord : voteItem.getVoteRecords()) {
//                        ResponseVoteRecordDto responseVoteRecordDto = modelMapper.map(voteRecord, ResponseVoteRecordDto.class);
//                        responseVoteRecordDtos.add(responseVoteRecordDto);
//                    }
//                    responseVoteItemDto.setResponseVoteRecordDtos(responseVoteRecordDtos);
//
//                    responseVoteItemDtos.add(responseVoteItemDto);
//                }
//                responsePostDto.getResponseVoteDto().setResponseVoteItemDtos(responseVoteItemDtos);
//            }

            return responsePostDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<ResponsePostListDto> getPostList(RequestSearchCondition condition, Pageable pageable) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_SNAKE_CASE);
            Map<PostSpecs.SearchKey, Object> map = objectMapper
                    .convertValue(condition, new TypeReference<Map<PostSpecs.SearchKey, Object>>() {});
            Page<Post> postLists = postRepository.findAll(PostSpecs.searchWith(map), pageable);

            Page<ResponsePostListDto> responsePostListDtos = getPostListWithWriterNames(postLists);

            return responsePostListDtos;
        } catch (Exception e) {
            return null;
        }
    }

    // postLists에 작성자 명을 더한 리스트를 반환하는 메서드
    private Page<ResponsePostListDto> getPostListWithWriterNames(Page<Post> postLists) {
        // Collect userIds from post lists.
        List<Long> writerIds = postLists.stream()
                .map(Post::getWriterId)
                .distinct()
                .collect(Collectors.toList());

        // Call userServiceClient to get user names.
        Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

        // Convert each post to ResponsePostListDto and replace userId with userName.
        Page<ResponsePostListDto> responsePostListDtos = postLists.map(postList -> {
            ResponsePostListDto dto = postList.toResponsePostListDto(writerIdToNameMap.get(postList.getWriterId()));
            return dto;
        });

        return responsePostListDtos;
    }

    // userIds에 해당하는 UserName을 가져오는 메서드
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
