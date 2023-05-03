package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.VoteDto;
import com.wcd.boardservice.entity.Vote;
import com.wcd.boardservice.entity.VoteItem;
import com.wcd.boardservice.entity.VoteRecord;
import com.wcd.boardservice.repository.VoteItemRepository;
import com.wcd.boardservice.repository.VoteRecordRepository;
import com.wcd.boardservice.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@Service
public class voteServiceImpl implements VoteService{
    VoteRepository voteRepository;
    VoteItemRepository voteItemRepository;
    VoteRecordRepository voteRecordRepository;
    ModelMapper modelMapper;

    @Override
    public VoteDto createVote(VoteDto voteDto) {
        try {
            Vote newVote = modelMapper.map(voteDto, Vote.class);
            for (VoteItem voteItem : newVote.getVoteItems()) {
                voteItemRepository.save(voteItem);
            }
            Vote saveVote = voteRepository.save(newVote);
            VoteDto saveVoteDto = modelMapper.map(saveVote, VoteDto.class);
            return saveVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VoteDto updateVote(Long voteId, Long userId, VoteDto voteDto) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            if (!vote.getWriterId().equals(userId)) {
                throw new Exception();
            }
            if(vote.getVoteRecords() != null) {
                throw new Exception();
            }

            for (VoteItem voteItem : vote.getVoteItems()) {
                voteItemRepository.delete(voteItem);
            }
            vote = modelMapper.map(voteDto, Vote.class);
            for (VoteItem voteItem : vote.getVoteItems()) {
                voteItemRepository.save(voteItem);
            }
            Vote updateVote = voteRepository.save(vote);
            VoteDto updateVoteDto = modelMapper.map(updateVote, VoteDto.class);
            return updateVoteDto;
        } catch (Exception e) {
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
            for(VoteItem voteItem : deleteVote.getVoteItems()) {
                voteItemRepository.delete(voteItem);
            }
            for(VoteRecord voteRecord : deleteVote.getVoteRecords()) {
                voteRecordRepository.delete(voteRecord);
            }
            voteRepository.delete(deleteVote);
        } catch (Exception e) {

        }
    }

    @Override
    public VoteDto getVoteById(Long voteId) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            VoteDto voteDto = modelMapper.map(vote, VoteDto.class);

            return voteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VoteDto castVote(Long voteId, Long[] itemIds, Long userId) {
        try {
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            List<VoteItem> voteItems = voteItemRepository.findByIdIn(itemIds);
            for (VoteItem voteItem : voteItems) {
                VoteRecord voteRecord = new VoteRecord(userId, vote, voteItem);
                VoteRecord saveVoteRecord = voteRecordRepository.save(voteRecord);
                voteItem.getVoteRecords().add(saveVoteRecord);
            }
            VoteDto saveVoteDto = modelMapper.map(vote, VoteDto.class);
            return saveVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VoteDto reCastVote(Long voteId, Long[] itemIds, Long userId) {
        try {
            voteRecordRepository.deleteByvoteIdAnduserId(voteId, userId);
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            List<VoteItem> voteItems = voteItemRepository.findByIdIn(itemIds);
            for (VoteItem voteItem : voteItems) {
                VoteRecord voteRecord = new VoteRecord(userId, vote, voteItem);
                VoteRecord saveVoteRecord = voteRecordRepository.save(voteRecord);
                voteItem.getVoteRecords().add(saveVoteRecord);
            }
            VoteDto saveVoteDto = modelMapper.map(vote, VoteDto.class);
            return saveVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VoteDto unCastVote(Long voteId, Long userId) {
        try {
            voteRecordRepository.deleteByvoteIdAnduserId(voteId, userId);
            Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
            VoteDto saveVoteDto = modelMapper.map(vote, VoteDto.class);
            return saveVoteDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VoteDto addItem(Long voteId, VoteDto voteDto) {
        try {
            if (voteDto.isAddItem() == true) {
                Vote vote = voteRepository.findById(voteId).orElseThrow(() -> new NoSuchElementException());
                vote = modelMapper.map(voteDto, Vote.class);
                Vote saveVote = voteRepository.save(vote);
                VoteDto saveVoteDto = modelMapper.map(saveVote, VoteDto.class);

                return saveVoteDto;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return null;
        }
    }
}
