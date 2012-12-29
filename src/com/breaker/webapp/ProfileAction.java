package com.breaker.webapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.breaker.navigation.NavBuilder;
import com.breaker.navigation.NavItemBean;
import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.newsstory.StoryBean;
import com.breaker.newsstory.StoryListFactory;
import com.breaker.newsstory.StoryListParameters;
import com.breaker.rss.output.RSS;
import com.breaker.security.PasswordEncryption;
import com.breaker.team.TeamBean;
import com.breaker.team.TeamDBUtils;
import com.breaker.team.TeamTabBean;
import com.breaker.team.TeamTabsDBUtils;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.user.UserManager;
import com.breaker.utils.Constants;
import com.breaker.utils.ImageUtil;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;

public class ProfileAction implements MVCActionInterface {

    private static Logger       logger                      = Logger.getLogger(ProfileAction.class);

    private static final String TAB_PARAMETER_INFO          = "info";
    private static final String TAB_PARAMETER_MANAGE_TEAMS  = "teams";
    private static final String TAB_PARAMETER_EDIT_PROFILE  = "edit";

    private static final String FORM_SUBMIT_MANAGE_TEAM     = "manageTeamSubmit";
    private static final String FORM_SUBMIT_ADD_TEAM        = "addTeamSubmit";
    private static final String PARAM_ADD_TEAM_SELECT_SPORT = "addTabSelectSport";
    private static final String FORM_SUBMIT_RENAME_TEAM     = "renameTeamSubmit";
    private static final String PARAM_NEW_SPORT             = "sport";
    private static final String PARAM_NEW_TEAM              = "new_team";

    private static final String PARAM_RENAME                = "rename";
    private static final String PARAM_STORIES_TO_SHOW       = "show";
    private static final String PARAM_USER_PIC              = "userPic";

    private int                 numStoriesPerPage           = 10;

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {

        User user = LoginUtils.getUserObject(request, response);
        if (user == null) {
            MVCRedirector redirector = new MVCRedirector();
            String uri = request.getRequestURI();
            String queryString = StringUtils.unNull(request.getQueryString());
            String returnUrl = StringUtils.encodeUrl(uri + "?" + queryString);
            redirector.setRedirectUrl("/login.jsp?returnUrl=" + returnUrl);
            return redirector;
        } else {
            user.setCreationDate(UserManager.getUserFromDB(user.getUserId()).getCreationDate());
        }

        boolean viewingSomeoneElseProfile = false;
        long userIdInUrl = RequestUtils.getParameterAsInt(request, Constants.PARAM_USER_ID, 0);
        if (userIdInUrl > 0) {
            if (userIdInUrl != user.getUserId()) {
                viewingSomeoneElseProfile = true;
                user = UserManager.getUserFromDB(userIdInUrl);
            }
        }

        ProfileView view = new ProfileView();

        String tab = null;
        if (viewingSomeoneElseProfile)
            tab = TAB_PARAMETER_INFO;
        else
            tab = RequestUtils.getParameterAsString(request, "tab", TAB_PARAMETER_INFO);

        if (TAB_PARAMETER_MANAGE_TEAMS.equals(tab)) {

            if ("POST".equalsIgnoreCase(request.getMethod())) {
                MVCRedirector redirector = handleTeamTabSubmit(request, user, view);
                if (redirector != null)
                    return redirector;
            }

            List<TeamTabBean> teamTabs = TeamTabsDBUtils.getTeamListByUser(user.getUserId());
            view.setTeamTabs(teamTabs);

            CategoryFactory categoryFactory = new CategoryFactory();
            categoryFactory.setReturnNationalCategory(false);
            List<CategoryBean> categories = categoryFactory.getDisplayCategories(CategoryFactory.ORDER_BY_NAME);
            view.setCategories(categories);
        } else if (TAB_PARAMETER_EDIT_PROFILE.equals(tab)) {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                try {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List<FileItem> items = upload.parseRequest(request);
                    if (items != null && !items.isEmpty()) {
                        Iterator<FileItem> iter = items.iterator();
                        while (iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();

                            if (!item.isFormField()) {
                                String fieldName = item.getFieldName();
                                if (PARAM_USER_PIC.equals(fieldName)) {
                                    String filePath = Constants.PICTURE_USERPIC_PATH + File.separator
                                            + user.getUserId() + File.separator;
                                    logger.info("User pic file path: " + filePath);

                                    String userPicName = "userpic.jpg";
                                    File resizedFile = new File(filePath + userPicName);
                                    ImageUtil.toJPG(item.getInputStream(), resizedFile, 120, 120);

                                    user.setPictureFileName(userPicName);
                                    UserManager.updateUser(user);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error uploading user pic", e);
                    e.printStackTrace();
                }
            } else if (RequestUtils.getParameterAsBoolean(request, "userInfoFormSubmit", false)) {
                user.setUserName(RequestUtils.getParameterAsString(request, "username", null));
                user.setEmail(RequestUtils.getParameterAsString(request, "email", null));
                user.setHometown(RequestUtils.getParameterAsString(request, "hometown", null));
                user.setLocation(RequestUtils.getParameterAsString(request, "location", null));
                user.setHighSchool(RequestUtils.getParameterAsString(request, "highSchool", null));
                user.setCollege(RequestUtils.getParameterAsString(request, "college", null));
                user.setRealName(RequestUtils.getCleanedParameter(request, "real_name"));
                user.setZipCode(RequestUtils.getCleanedParameter(request, "zip_code"));

                // Set gender
                String gender = request.getParameter("gender");
                if (gender != null)
                    if (gender.equalsIgnoreCase("M"))
                        user.setGender("M");
                    else if (gender.equalsIgnoreCase("F"))
                        user.setGender("F");

                // Set birthday
                String birthday = RequestUtils.getCleanedParameter(request, "birthday");
                if (!StringUtils.isEmpty(birthday)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        user.setBirthday(sdf.parse(birthday));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String error = validateUserInfo(request);
                if (StringUtils.isEmpty(error)) {
                    UserManager.updateUser(user);
                    view.setMessage("Updated Successfully!");
                } else {
                    view.setError(error);
                }
            } else if (RequestUtils.getParameterAsBoolean(request, "changePasswordFormSubmit", false)) {
                String oldPassword = PasswordEncryption.encrypt(RequestUtils.getCleanedParameter(request,
                        "old_password"));
                User testUser = LoginUtils.getUserFromDB(user.getEmail(), oldPassword);
                if (testUser != null) {
                    String newPassword1 = RequestUtils.getCleanedParameter(request, "new_password1");
                    String newPassword2 = RequestUtils.getCleanedParameter(request, "new_password2");
                    if (newPassword1.equals(newPassword2)) {
                        if (newPassword1.length() >= 6) {
                            user.setEncryptedPassword(PasswordEncryption.encrypt(newPassword1));
                            boolean success = UserManager.updateUser(user);
                            if (success)
                                view.setMessage("Password changed.");
                            else
                                view.setError("There was an unknown error changing your password. Please try again.");
                        } else {
                            view.setError("Your new password must be at least 6 characters long.");
                        }
                    } else {
                        view.setError("The new passwords you entered do not match.");
                    }
                } else {
                    view.setError("The password you provided is not valid.");
                }
            }
        } else {
            StoryListParameters parameters = new StoryListParameters();
            parameters.setPopulateCategories(true);
            parameters.setPopulateTeams(true);
            parameters.setCountComments(true);
            parameters.setStartPosition(RequestUtils.getParameterAsInt(request, "start", 0));
            parameters.setSortBy(StoryListParameters.SORT_BY_NEWEST);

            String showStoriesType = RequestUtils.getParameterAsString(request, PARAM_STORIES_TO_SHOW, null);
            if (StringUtils.isEmpty(showStoriesType))
                showStoriesType = Constants.USER_ACTION_BROKEN;
            if (Constants.USER_ACTION_BROKEN.equals(showStoriesType))
                parameters.setBrokenByUserId(user.getUserId());
            else if (Constants.USER_ACTION_VOTED_ON.equals(showStoriesType))
                parameters.setUserIdVotedOn(user.getUserId());
            else if (Constants.USER_ACTION_THUMBS_UP.equals(showStoriesType))
                parameters.setUserIdThumbsUp(user.getUserId());
            else if (Constants.USER_ACTION_THUMBS_DOWN.equals(showStoriesType))
                parameters.setUserIdThumbsDown(user.getUserId());

            int startPosit = RequestUtils.getParameterAsInt(request, "start", 0);

            List<StoryBean> stories = StoryListFactory.getStoryList(parameters);
            logger.debug("Num stories: " + stories.size());

            String benchingSince = StringUtils.formatDate(user.getCreationDate());

            int numStoriesBroken = StoryListFactory.countStoriesByUserId(user.getUserId());
            String numStoriesBrokenStr = numStoriesBroken > 0 ? String.valueOf(numStoriesBroken) : "None";

            view.setBenchingSince(benchingSince);
            view.setNumStoriesBroken(numStoriesBrokenStr);
            view.setShowStoriesType(showStoriesType);
            view.setStories(stories);

            // Build link to RSS feed
            StringBuilder rssLink = new StringBuilder("/rss/");
            StringUtils.appendParameter(rssLink, Constants.PARAM_USER_ID, user.getUserId());
            StringUtils.appendParameter(rssLink, Constants.PARAM_USER_ACTION, showStoriesType);
            StringUtils.appendParameter(rssLink, Constants.PARAM_SORT_BY, parameters.getSortBy());
            StringUtils.appendParameter(rssLink, Constants.PARAM_SHOW_DATES, parameters.getShowDates());
            view.setRssLink(rssLink.toString());
            view.setRssFeedTitle(RSS.makePageTitle(parameters));

            String baseUrl = "profile.jsp?tab=info&userId=" + user.getUserId() + "&show=" + showStoriesType;
            int storiesCount = StoryListFactory.getStoryListCount(parameters);
            view.setNavList(getNavigation(startPosit, storiesCount, baseUrl));
        }

        StringBuilder baseUrl = new StringBuilder("/profile.jsp?tab=");
        baseUrl.append(tab);
        if (viewingSomeoneElseProfile) {
            baseUrl.append("&");
            baseUrl.append(Constants.PARAM_USER_ID);
            baseUrl.append("=");
            baseUrl.append(user.getUserId());
        }
        view.setBaseUrl(baseUrl.toString());
        view.setTab(tab);
        view.setUser(user);
        view.setMyProfile(!viewingSomeoneElseProfile);

        request.setAttribute("ProfileView", view);
        return null;
    }

    private MVCRedirector handleTeamTabSubmit(HttpServletRequest request, User user, ProfileView view) {
        if (!StringUtils.isEmpty(request.getParameter(FORM_SUBMIT_MANAGE_TEAM))) {
            int rename = RequestUtils.getParameterAsInt(request, PARAM_RENAME, 0);
            int removeTeamId = RequestUtils.getParameterAsInt(request, "remove", 0);
            int moveUpTeamId = RequestUtils.getParameterAsInt(request, "move_up", 0);
            int moveDownTeamId = RequestUtils.getParameterAsInt(request, "move_down", 0);
            if (rename > 0)
                view.setTeamIdToRename(rename);
            else if (removeTeamId > 0)
                TeamTabsDBUtils.removeTeam(user.getUserId(), removeTeamId);
            else if (moveUpTeamId > 0)
                TeamTabsDBUtils.moveTeam(user.getUserId(), moveUpTeamId, -1);
            else if (moveDownTeamId > 0)
                TeamTabsDBUtils.moveTeam(user.getUserId(), moveDownTeamId, 1);
            else if (!StringUtils.isEmpty(request.getParameter(FORM_SUBMIT_RENAME_TEAM))) {
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String name = parameterNames.nextElement();
                    if (name.startsWith("renameTeamId")) {
                        int teamIdToRename = StringUtils.toInt(name.substring(12), 0);
                        String newName = request.getParameter(name);
                        if (!StringUtils.isEmpty(newName)) {
                            TeamTabBean teamTab = TeamTabsDBUtils.find(user.getUserId(), teamIdToRename);
                            teamTab.setName(newName);
                            TeamTabsDBUtils.saveOrUpdate(teamTab);
                        }
                        break;
                    }
                }
            }
        } else if (!StringUtils.isEmpty(request.getParameter(FORM_SUBMIT_ADD_TEAM))) {
            int newTeamId = RequestUtils.getParameterAsInt(request, "new_team", 0);
            String tabName = RequestUtils.getParameterAsString(request, "newTeamName", "");
            if (newTeamId > 0) {
                TeamTabsDBUtils.insertNewTeam(user.getUserId(), newTeamId, tabName);
            }
        } else if (RequestUtils.getParameterAsBoolean(request, PARAM_ADD_TEAM_SELECT_SPORT, true)) {
            int categoryIdSelected = RequestUtils.getParameterAsInt(request, PARAM_NEW_SPORT, 0);
            view.setCategoryIdSelected(categoryIdSelected);
            int teamIdSelected = RequestUtils.getParameterAsInt(request, PARAM_NEW_TEAM, 0);
            view.setTeamIdSelected(teamIdSelected);
            TeamBean team = TeamDBUtils.getTeamById(teamIdSelected);
            if (team != null) {
                String newTabName = team.getDisplayName();
                view.setNewTabName(newTabName);
            }
            view.setTeams(TeamDBUtils.getTeamListByCategory(categoryIdSelected));
        }

        return null;
    }

    private List<NavItemBean> getNavigation(int currentPosition, int totalNumElements, String baseURL) {
        NavBuilder navBuilder = new NavBuilder();
        navBuilder.setBaseURL(baseURL);
        navBuilder.setCurrentPosition(currentPosition);
        navBuilder.setItemsPerPage(numStoriesPerPage);
        navBuilder.setTotalNumElements(totalNumElements);
        return navBuilder.buidNavList();
    }

    private String validateUserInfo(HttpServletRequest request) {
        StringBuilder err = new StringBuilder();
        String email = RequestUtils.getParameterAsString(request, "email", "");
        if (!StringUtils.verifyEmailFormat(email))
            StringUtils.appendHTMLMessage(err, "The email you provided is not valid.");

        String username = RequestUtils.getParameterAsString(request, "username", "");
        if (StringUtils.isEmpty(username))
            StringUtils.appendHTMLMessage(err, "You must provide a user name.");

        return err.toString();
    }

}
