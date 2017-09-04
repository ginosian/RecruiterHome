package com.recruiting.repository;

import com.recruiting.domain.Conversation;
import com.recruiting.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Martha on 6/21/2017.
 */
public interface MessageRepository extends BasePagingAndSortingRepository<Message> {


    Page<Message> findByConversationOrderByCreatedAtDesc(Conversation conversation, Pageable pageable);

    Message findTopByConversationOrderByCreatedAtAsc(Conversation conversation);
}
