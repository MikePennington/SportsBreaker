package com.breaker.webapp;

import com.breaker.security.PasswordEncryption;
import com.breaker.user.LoginUtils;
import com.breaker.user.User;
import com.breaker.user.UserLoginManager;
import com.breaker.user.UserManager;
import com.breaker.utils.MailSender;
import com.breaker.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action class for login.jsp
 * 
 * @author Mike Pennington
 */
public class LoginAction implements MVCActionInterface {

    private static final String FORGOT_PASSWORD_SUBJ = "Forgotten Password Request";
    private static final String VAR_PASSWORD         = "@PASSWORD@";
    private static final String VAR_USERNAME         = "@USERNAME@";
    private static final String FORGOT_PASSWORD_MSG  = VAR_USERNAME + ",\n\nYour password is: " + VAR_PASSWORD;

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        LoginView view = new LoginView();
        if (!StringUtils.isEmpty(request.getParameter("loginSubmit"))) {
            LoginUtils.logout(request, response);
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean permanentLogin = "on".equals(request.getParameter("alwaysLoggedIn"));
            UserLoginManager loginManager = new UserLoginManager(email, password, request);
            loginManager.loginUser(request, response, permanentLogin);
            if (LoginUtils.isLoggedIn(request, response)) {
                String returnUrl = request.getParameter("returnUrl");
                if (StringUtils.isEmpty(returnUrl) || !StringUtils.isAbsoluteURL(returnUrl))
                    returnUrl = "/index.jsp";
                MVCRedirector redirector = new MVCRedirector();
                redirector.setRedirectUrl(returnUrl);
                return redirector;
            }
            view.setError("Your username or password was incorrect.");
        } else if (!StringUtils.isEmpty(request.getParameter("forgotPasswordSubmit"))) {
            String email = request.getParameter("forgotPasswordEmail");
            User user = UserManager.getUserByEmail(email);
            if (user != null) {
                String password = user.getEncryptedPassword();
                password = PasswordEncryption.decrypt(password);
                String message = FORGOT_PASSWORD_MSG.replaceAll(VAR_USERNAME, user.getUserName());
                message = message.replaceAll(VAR_PASSWORD, password);
                MailSender.sendMail(user.getEmail(), FORGOT_PASSWORD_SUBJ, message, false);
                view.setMessage("Your password has been sent to " + user.getEmail());
            } else {
                view.setError("We could not send your password, because the email address '" + email
                        + "' was not found in our system. Please try again.");
            }
        } else {
            view.setError(request.getParameter("msg"));
        }
        request.setAttribute("LoginView", view);
        return null;
    }
}