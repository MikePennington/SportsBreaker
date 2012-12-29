package com.breaker.webapp;

import java.util.List;

import com.breaker.newsstory.CommentBean;
import com.breaker.newsstory.StoryBean;
import com.breaker.user.User;

/**
 * View class for story.jsp
 * 
 * @author Mike Pennington
 */
public class StoryView extends BasicView {

    private StoryBean               story;
    private User                    brokenBy;
    private List<CommentViewHelper> comments;
    private int                     replyToId;
    private boolean                 newComment;

    public int getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(int replyToId) {
        this.replyToId = replyToId;
    }

    public StoryBean getStory() {
        return story;
    }

    public void setStory(StoryBean story) {
        this.story = story;
    }

    public User getBrokenBy() {
        return brokenBy;
    }

    public void setBrokenBy(User brokenBy) {
        this.brokenBy = brokenBy;
    }

    public List<CommentViewHelper> getComments() {
        return comments;
    }

    public void setComments(List<CommentViewHelper> comments) {
        this.comments = comments;
    }

    public int getNumberOfComments() {
        return comments.size();
    }

    public boolean isNewComment() {
        return newComment;
    }

    public void setNewComment(boolean newComment) {
        this.newComment = newComment;
    }

    /**
     * A Helper class so that the JSP can format the comments correctly.
     */
    public class CommentViewHelper {
        private CommentBean commentBean;
        private int         level;

        public CommentViewHelper(CommentBean commentBean, int level) {
            this.commentBean = commentBean;
            this.level = level;
        }

        public CommentBean getCommentBean() {
            return commentBean;
        }

        public void setCommentBean(CommentBean commentBean) {
            this.commentBean = commentBean;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLeftMargin() {
            return level * 20;
        }

        public int getWidth() {
            return 800 - (level * 20);
        }
    }
}