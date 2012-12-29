package com.breaker.newsstory;

import java.util.Date;

import com.breaker.user.User;
import com.breaker.utils.StringUtils;

/**
 * A bean representing the comments database table.
 * 
 * @author Mike Pennington
 */
public class CommentBean {
    private int    commentId;
    private int    storyId;
    private long   userId;
    private User   user;
    private int    replyToId;
    private Date   commentDate;
    private String comment;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(int replyToId) {
        this.replyToId = replyToId;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getFormattedDate() {
        return StringUtils.formatDate(commentDate);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
