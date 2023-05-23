package com.wcd.chattingservice.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wcd.chattingservice.dto.response.QResponseChat;
import com.wcd.chattingservice.dto.response.ResponseChat;
import com.wcd.chattingservice.entity.Chat;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.wcd.chattingservice.entity.QChat.*;
import static com.wcd.chattingservice.entity.QChatRoom.*;

public class ChatRepositoryImpl implements ChatRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ChatRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ResponseChat> getChats(Pageable pageable) {
        List<ResponseChat> content = queryFactory
                .select(new QResponseChat(
                        chat.id,
                        chatRoom.id.as("chatRoomId"),
                        chat.senderId,
                        chat.message,
                        chat.sendTime
                ))
                .from(chat)
                .leftJoin(chat.chatRoom, chatRoom)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Chat> countQuery = queryFactory
                .selectFrom(chat)
                .leftJoin(chat.chatRoom, chatRoom);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }
}
