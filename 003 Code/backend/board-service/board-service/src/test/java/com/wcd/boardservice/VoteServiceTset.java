//package com.wcd.boardservice;
//
//import com.wcd.boardservice.dto.vote.VoteDto;
//import com.wcd.boardservice.entity.Vote;
//import com.wcd.boardservice.entity.VoteItem;
//import com.wcd.boardservice.service.VoteService;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class VoteServiceTset {
//    @Autowired
//    VoteService voteService;
//    @Autowired
//    ModelMapper modelMapper;
//
//    @Test
//    public void createVoteTest() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        voteService.createVote(voteDto);
//    }
//
//    @Test
//    public void updateVoteTest() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "3번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        VoteDto savedVoteDto = voteService.createVote(voteDto);
//
//        List<VoteItem> newVoteItems = new ArrayList<>();
//        newVoteItems.add(new VoteItem(null, null, null, "1번"));
//        newVoteItems.add(new VoteItem(null, null, null, "3번"));
//        newVoteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote newVote = new Vote(savedVoteDto.getId(), null, savedVoteDto.getClubId(), savedVoteDto.getWriterId(), newVoteItems, null, null, false, false, false);
//        VoteDto newVoteDto = modelMapper.map(newVote, VoteDto.class);
//        voteService.updateVote(savedVoteDto.getId(), savedVoteDto.getWriterId(), newVoteDto);
//    }
//
//    @Test
//    public void deleteVote() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        VoteDto savedVoteDto = voteService.createVote(voteDto);
//        Long[] itemIds = {1L};
//        voteService.castVote(savedVoteDto.getId(), itemIds, 1L);
//        voteService.deleteVote(savedVoteDto.getId(), savedVoteDto.getWriterId());
//    }
//
//    @Test
//    public void castVoteItemTest() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        VoteDto savedVoteDto = voteService.createVote(voteDto);
//        Long[] itemIds = {1L};
//        voteService.castVote(savedVoteDto.getId(), itemIds, 1L);
//    }
//
//    @Test
//    public void reCastVoteItemTest() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        VoteDto savedVoteDto = voteService.createVote(voteDto);
//        Long[] itemIds = {1L};
//        voteService.castVote(savedVoteDto.getId(), itemIds, 1L);
//        Long[] newItemIds = {1L, 2L};
//        voteService.reCastVote(savedVoteDto.getId(), newItemIds, 1L);
//    }
//
//    @Test
//    public void unCastVoteItemTest() {
//        List<VoteItem> voteItems = new ArrayList<>();
//        voteItems.add(new VoteItem(null, null, null, "1번"));
//        voteItems.add(new VoteItem(null, null, null, "2번"));
//        Vote vote = new Vote(null, null, 1L, 1L, voteItems, null, null, false, false, false);
//        VoteDto voteDto = modelMapper.map(vote, VoteDto.class);
//        VoteDto savedVoteDto = voteService.createVote(voteDto);
//        Long[] itemIds = {1L};
//        voteService.castVote(savedVoteDto.getId(), itemIds, 1L);
//        voteService.deleteVote(savedVoteDto.getId(), 1L);
//    }
//}
