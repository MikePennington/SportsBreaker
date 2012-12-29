package com.breaker.webapp;

import static com.breaker.utils.Constants.STAT_TYPE_COMMENT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.CommentBean;
import com.breaker.newsstory.CommentsFactory;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryFactory;
import com.breaker.newsstory.StoryStatsFactory;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;
import com.breaker.webapp.StoryView.CommentViewHelper;

public class StoryAction implements MVCActionInterface {

    private static final String PARAM_STORY_ID           = "id";
    private static final String PARAM_REPLY_TO           = "replyTo";
    private static final String PARAM_NEW_COMMENT_SUMBIT = "newCommentSubmit";
    private static final String PARAM_NEW_COMMENT        = "newComment";
    private static final String PARAM_REPLYING           = "replying";
    private static final String PARAM_REPLY_COMMENT      = "replyComment";
    private static final String PARAM_REPLY_SUBMIT       = "replySubmit";

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        StoryView view = new StoryView();

        int storyId = RequestUtils.getParameterAsInt(request, PARAM_STORY_ID, 0);
        int replyToId = RequestUtils.getParameterAsInt(request, PARAM_REPLY_TO, -1);

        User user = LoginUtils.getUserObject(request, response);

        logView(storyId, user, request);

        // Take care of a comment being submitted.
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String error = null;
            if (RequestUtils.getParameterAsString(request, PARAM_NEW_COMMENT_SUMBIT, null) != null) {
                error = saveCommentToDB(storyId, replyToId,
                        request.getParameter(PARAM_NEW_COMMENT), user);
            } else if (RequestUtils.getParameterAsString(request, PARAM_REPLY_SUBMIT, null) != null) {
                if (replyToId > 0
                        && RequestUtils.getParameterAsBoolean(request, PARAM_REPLYING, false)) {
                    error = saveCommentToDB(storyId, replyToId, request
                            .getParameter(PARAM_REPLY_COMMENT), user);
                }
            }
            if (error == null) {
                MVCRedirector redirector = new MVCRedirector();
                redirector.setRedirectUrl("story.jsp?" + PARAM_STORY_ID + "=" + storyId);
                return redirector;
            } else {
                view.setError(error);
            }
        }

        StoryBean story = StoryFactory.getStoryById(storyId);
        if (story != null) {
            view.setStory(story);
            view.setBrokenBy(story.getBrokenByUser());
            view.setComments(getCommentsHelperList(storyId));
        } else {
            view.setError("The story you are looking for no longer exists.");
        }

        view.setReplyToId(replyToId);
        view.setNewComment(replyToId == 0);
        request.setAttribute("StoryView", view);
        return null;
    }

    private List<CommentViewHelper> getCommentsHelperList(int storyId) {
        List<CommentViewHelper> commentViewHelperList = new ArrayList<CommentViewHelper>();
        List<CommentBean> comments = CommentsFactory.getCommentsForStory(storyId, true);
        for (int i = 0; i < comments.size(); i++) {
            CommentBean comment = comments.get(i);
            if (comment.getReplyToId() == 0) {
                CommentViewHelper helper = new StoryView().new CommentViewHelper(comment, 0);
                commentViewHelperList.add(helper);
                getReplyToCommentsFromList(commentViewHelperList, comments, comment.getCommentId(),
                        1);
            }
        }
        return commentViewHelperList;
    }

    private void getReplyToCommentsFromList(List<CommentViewHelper> commentViewHelperList,
            List<CommentBean> comments, int parentCommentId, int level) {
        for (int i = 0; i < comments.size(); i++) {
            CommentBean comment = comments.get(i);
            if (comment.getReplyToId() == parentCommentId) {
                CommentViewHelper helper = new StoryView().new CommentViewHelper(comment, level);
                commentViewHelperList.add(helper);
                getReplyToCommentsFromList(commentViewHelperList, comments, comment.getCommentId(),
                        ++level);
            }
        }
    }

    /**
     * Parses the information and calls CommentFactory to save the comment to
     * the database.
     * 
     * @param storyId
     * @param replyToId
     * @param commentStr
     * @param user
     * @return Returns an error message if the story could not be saved.
     */
    private String saveCommentToDB(int storyId, int replyToId, String commentStr, User user) {
        if (user == null || (!user.isSoftLoggedIn() && !user.isHardLoggedIn()))
            return "You must be logged in to post a comment.";

        CommentBean comment = new CommentBean();
        comment.setComment(commentStr);
        comment.setCommentDate(new Date());
        comment.setReplyToId(replyToId);
        comment.setStoryId(storyId);
        comment.setUserId(user.getUserId());
        boolean inserted = CommentsFactory.insertComment(comment);
        if (!inserted) {
            return "There was a problem saving your comment.";
        } else {
            StoryStatsFactory.saveStat(storyId, user.getUserId(), STAT_TYPE_COMMENT);
            return null;
        }
    }

    private boolean logView(int storyId, User user, HttpServletRequest request) {
        long userId = 0;
        if (user == null) {
            try {
                String ip = request.getRemoteAddr().replaceAll(".", "");
                userId = StringUtils.toLong(ip, 0);
                if (userId > 0)
                    userId = -userId;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            userId = user.getUserId();
        }
        return StoryStatsFactory.saveStat(storyId, userId, Constants.STAT_TYPE_VIEW_DETAIL);
    }
}