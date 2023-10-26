package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.vote.RequestVoteDto;
import com.wcd.boardservice.dto.vote.ResponseVoteDto;
import com.wcd.boardservice.dto.vote.VoteDto;
import com.wcd.boardservice.entity.Vote;
import com.wcd.boardservice.entity.VoteItem;
import com.wcd.boardservice.entity.VoteRecord;
import com.wcd.boardservice.repository.VoteItemRepository;
import com.wcd.boardservice.repository.VoteRecordRepository;
import com.wcd.boardservice.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.bouncycastle.cert.ocsp.Req;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class voteServiceImpl implements VoteService{
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    VoteItemRepository voteItemRepository;
    @Autowired
    VoteRecordRepository voteRecordRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseVoteDto createVote(RequestVoteDto requestVoteDto) {
        try {
            Vote newVote = modelMapper.map(requestVoteDto, Vote.class);
            for(VoteItem voteItem : newVote.getVoteItems()) {
                voteItem.setVote(newVote);
            }
            Vote saveVote = voteRepository.save(newVote);
            ResponseVoteDto responseVoteDto = modelMapper.map(saveVote, ResponseVoteDto.class);
            return responseVoteDto;
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVoteDto updateVote(Long voteId, Long userId, RequestVoteDto requestVoteDto) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new Exception());
            if (!vote.getWriterId().equals(userId)) {
                throw new Exception();
            }
            if(!vote.getVoteRecords().isEmpty()) {
                throw new Exception();
            }

            // VoteDto에서 받아온 VoteItem 엔티티들을 Vote 엔티티에 설정합니다.
            vote.getVoteItems().clear();

//            for(VoteItemDto voteItemDto : voteDto.getVoteItemDtos()) {
//                voteItem.setVote(vote);
//                vote.getVoteItems().add(voteItem);
//            }

            // Vote 엔티티를 저장합니다.
            Vote savedVote = voteRepository.save(vote);
            VoteDto savedVoteDto = modelMapper.map(savedVote, VoteDto.class);
            return null;
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteVote(Long voteId, Long userId) {
        try {
            Vote deleteVote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            if (!deleteVote.getWriterId().equals(userId)) {
                throw new Exception();
            }
            voteRepository.delete(deleteVote);
        } catch (Exception e) {

        }
    }

    @Override
    public ResponseVoteDto getVoteById(Long voteId) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            ResponseVoteDto responseVoteDto = modelMapper.map(vote, ResponseVoteDto.class);

            return responseVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseVoteDto castVote(Long voteId, Long[] itemIds, Long userId) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            List<VoteItem> voteItems = voteItemRepository.findByIdIn(itemIds);
            for (VoteItem voteItem : voteItems) {
                VoteRecord voteRecord = new VoteRecord(userId, vote, voteItem);
                VoteRecord saveVoteRecord = voteRecordRepository.save(voteRecord);
                voteItem.getVoteRecords().add(saveVoteRecord);
            }
            ResponseVoteDto responseVoteDto = modelMapper.map(vote, ResponseVoteDto.class);
            return responseVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseVoteDto reCastVote(Long voteId, Long[] itemIds, Long userId) {
        try {
            voteRecordRepository.deleteByvoteIdAndUserId(voteId, userId);
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            voteRecordRepository.deleteByvoteIdAndUserId(voteId, userId);
            List<VoteItem> voteItems = voteItemRepository.findByIdIn(itemIds);
            for (VoteItem voteItem : voteItems) {
                VoteRecord voteRecord = new VoteRecord(userId, vote, voteItem);
                VoteRecord saveVoteRecord = voteRecordRepository.save(voteRecord);
                voteItem.getVoteRecords().add(saveVoteRecord);
            }
            ResponseVoteDto responseVoteDto = modelMapper.map(vote, ResponseVoteDto.class);
            return responseVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseVoteDto unCastVote(Long voteId, Long userId) {
        try {
            voteRecordRepository.deleteByvoteIdAndUserId(voteId, userId);
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            ResponseVoteDto responseVoteDto = modelMapper.map(vote, ResponseVoteDto.class);
            return responseVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseVoteDto addItem(Long voteId, VoteDto voteDto) {
        try {
            if (voteDto.isAddItem() == true) {
                Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
                vote = modelMapper.map(voteDto, Vote.class);
                Vote saveVote = voteRepository.save(vote);
                ResponseVoteDto responseVoteDto = modelMapper.map(saveVote, ResponseVoteDto.class);

                return responseVoteDto;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
