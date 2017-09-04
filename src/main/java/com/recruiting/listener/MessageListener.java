package com.recruiting.listener;

import com.recruiting.domain.Message;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by Martha on 6/21/2017.
 */
public class MessageListener {

    @PrePersist
    public void messagePrePersist(Message ob) {
//        Conversation conversation = ob.getConversation();
//        conversation.setLastMessage(ob);
    }

    @PreUpdate
    public void messagePreUpdate(Message ob) {
//        Conversation conversation = ob.getConversation();
//        conversation.setLastMessage(ob);
    }
}
