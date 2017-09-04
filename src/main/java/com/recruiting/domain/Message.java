package com.recruiting.domain;

import com.recruiting.listener.MessageListener;
import com.recruiting.utils.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martha on 4/5/2017.
 */
@Entity
@Table(name = "message")
@EntityListeners(MessageListener.class)
public class Message extends AbstractTitledEntity implements Serializable {

    // region Instance Fields
    @Column(name = "content",
            nullable = false)
    private String content;

    @Column(name = "seen")
    private Boolean seen;

    @OneToOne
    private User author;

    @OneToOne
    private User receiver;
    // endregion

    @OneToOne
    private Conversation conversation;

    // region Constructors
    public Message() {
    }

    public Message(String title,
            String content,
            Boolean seen,
            User author,
            User receiver,
            Conversation conversation) {
        super(title);
        this.content = content;
        this.seen = seen;
        this.author = author;
        this.receiver = receiver;
        this.conversation = conversation;
    }

    // endregion

    // region Transient methods
    public void correctStrings() {
        this.content = StringUtils.correctWhiteSpaces(content);
    }
    // endregion

    // region Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    // endregion

    // region Overrides
    @Override
    public String toString() {
        return "Message [" +
                "id: " + id + "\t" +
                "title: " + getTitle() + "\t" +
                "text: " + content + "\t" +
                "]";
    }
    // endregion

}
