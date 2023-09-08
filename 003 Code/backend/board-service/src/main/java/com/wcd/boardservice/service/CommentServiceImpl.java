package com.wcd.boardservice.service;

import com.wcd.boardservice.client.UserServiceClient;
import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.comment.UpdateRequestCommentDto;
import com.wcd.boardservice.dto.user.RequestUserNamesDto;
import com.wcd.boardservice.dto.user.ResponseUserNamesDto;
import com.wcd.boardservice.entity.Comment;
import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.exception.CommentNotFoundException;
import com.wcd.boardservice.exception.PostNotFoundException;
import com.wcd.boardservice.exception.UnauthorizedCommentEditException;
import com.wcd.boardservice.repository.CommentRepository;
import com.wcd.boardservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    UserServiceClient userServiceClient;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserServiceClient userServiceClient) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public ResponseCommentDto createComment(Long clubId, Long postId, Long writerId, RequestCommentDto requestCommentDto) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException("Post not found with id" + postId));
            Comment newComment = requestCommentDto.toEntity(clubId, post, writerId);

            Comment savedComment = commentRepository.save(newComment);

            ResponseCommentDto responseCommentDto = savedComment.toResponseCommentDto(userServiceClient.getUserNameById(writerId));

            return responseCommentDto;
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseCommentDto updateComment(Long commentId, Long writerId, UpdateRequestCommentDto updateRequestCommentDto) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found with id" + commentId));

            if (!comment.getWriterId().equals(writerId)) {
                throw new UnauthorizedCommentEditException("User " + writerId + " is not authorized to edit comment " + commentId);
            }

            comment.update(updateRequestCommentDto);

            ResponseCommentDto responseCommentDto = comment.toResponseCommentDto(userServiceClient.getUserNameById(writerId));

            return responseCommentDto;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteComment(Long commentId, Long writerId) {
        try {
            Comment deleteComment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found with id" + commentId));
            if (!deleteComment.getWriterId().equals(writerId)) {
                throw new UnauthorizedCommentEditException("User " + writerId + " is not authorized to edit comment " + commentId);
            }
            commentRepository.delete(deleteComment);
        } catch (NoSuchElementException e) {

        } catch (Exception e) {

        }
    }

    @Override
    public ResponseCommentDto getCommentById(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new CommentNotFoundException("Comment not found with id" + commentId));

            ResponseCommentDto responseCommentDto = comment.toResponseCommentDto(userServiceClient.getUserNameById(comment.getWriterId()));

            return responseCommentDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getAllComment(Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findAll(pageable);
            Page<ResponseCommentDto> responseCommentDtos = geCommentListWithWriterNames(commentLists);
            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getAllCommentsInPost(Long postId, Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findBypostId(postId, pageable);
            Page<ResponseCommentDto> responseCommentDtos = geCommentListWithWriterNames(commentLists);
            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getAllCommentsByUserInClub(Long clubId, Long writerId, Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findByClubIdAndWriterId(clubId, writerId, pageable);
            Page<ResponseCommentDto> responseCommentDtos = geCommentListWithWriterNames(commentLists);

            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getALlUserComment(Long userId, Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findBywriterId(userId, pageable);
            Page<ResponseCommentDto> responseCommentDtos = geCommentListWithWriterNames(commentLists);

            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public Page<CommentDto> getAllClubComment(Long clubId, Pageable pageable) {
//        try {
//            Page<Comment> commentList= commentRepository.findByclubId(clubId, pageable);
//            List<CommentDto> commentDtoList = commentList.stream()
//                    .map(comment -> modelMapper.map(comment, CommentDto.class))
//                    .collect(Collectors.toList());
//
//            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
//        } catch (Exception e) {
//            return null;
//        }
//    }

    // commentLists에 작성자 명을 더한 리스트를 반환하는 메서드
    private Page<ResponseCommentDto> geCommentListWithWriterNames(Page<Comment> commentLists) {
        // Collect userIds from post lists.
        List<Long> writerIds = commentLists.stream()
                .map(Comment::getWriterId)
                .distinct()
                .collect(Collectors.toList());

        // Call userServiceClient to get user names.
        Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

        // Convert each post to ResponsePostListDto and replace userId with userName.
        Page<ResponseCommentDto> responseCommentDtos = commentLists.map(comment -> {
            ResponseCommentDto dto = comment.toResponseCommentDto(writerIdToNameMap.get(comment.getWriterId()));
            return dto;
        });

        return responseCommentDtos;
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
