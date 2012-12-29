package com.breaker.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.breaker.recaptcha.ReCaptchaImpl;
import com.breaker.recaptcha.ReCaptchaResponse;
import com.breaker.security.PasswordEncryption;
import com.breaker.user.*;
import com.breaker.utils.Constants;
import com.breaker.utils.RequestUtils;
import com.breaker.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Action class for singup.jsp
 * 
 * @author Mike Pennington
 */
public class SignupAction implements MVCActionInterface {

    private static Logger       log                 = Logger.getLogger(SignupAction.class);

    private static final String SUBMIT_BUTTON_NAME  = "signup";
    private static final String SUBMIT_BUTTON_VALUE = "Sign Up!";

    public MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response) {
        SignupView view = new SignupView();
        String errorMessage = null;
        if ("Sign Up!".equals(request.getParameter("signup"))) {

            populateUserInfo(request, view);

            if (StringUtils.isEmpty(view.getEmail()) || StringUtils.isEmpty(view.getVerifyEmail())
                    || StringUtils.isEmpty(view.getPassword()) || StringUtils.isEmpty(view.getVerifyPassword())
                    || StringUtils.isEmpty(view.getUsername()))
                errorMessage = StringUtils.appendHTMLMessage(errorMessage, "All fields marked with a * are required.");
            if (UserManager.getUserByEmail(view.getEmail()) != null)
                errorMessage = StringUtils.appendHTMLMessage(errorMessage,
                        "The email addresses you provided is already in use.");
            if (!StringUtils.verifyEmailFormat(view.getEmail()))
                errorMessage = StringUtils.appendHTMLMessage(errorMessage,
                        "You did not seem to provide a valid email address.");
            if (!view.getEmail().equalsIgnoreCase(view.getVerifyEmail()))
                errorMessage = StringUtils
                        .appendHTMLMessage(errorMessage, "The email addresses provided do not match.");
            if (UserManager.getUserByUsername(view.getUsername()) != null)
                errorMessage = StringUtils.appendHTMLMessage(errorMessage,
                        "The username you provided is already taken. Please choose another.");
            if (!view.getPassword().equals(view.getVerifyPassword()))
                errorMessage = StringUtils.appendHTMLMessage(errorMessage, "The passwords you provided do not match.");
            if (view.getPassword().length() < 6)
                errorMessage = StringUtils.appendHTMLMessage(errorMessage,
                        "Your password must be at least 6 characters long.");
            if (!verifyCaptcha(request))
                errorMessage = StringUtils.appendHTMLMessage(errorMessage,
                        "You did not answer the securty question correctly.");
            if (StringUtils.isEmpty(errorMessage)) {
                String encryptedPassword = PasswordEncryption.encrypt(view.getPassword());
                User user = view.getUser();
                user.setEmail(view.getEmail());
                user.setEncryptedPassword(encryptedPassword);
                user.setUserName(view.getUsername());
                user.setCreationDate(new Date());
                boolean created = UserManager.createUser(user);
                if (created) {
                    UserLoginManager loginManager = new UserLoginManager(view.getEmail(), view.getPassword(), request);
                    loginManager.loginUser(request, response, false);
                    MVCRedirector redirector = new MVCRedirector();
                    redirector.setRedirectUrl("index.jsp");
                    return redirector;
                }
                errorMessage = "There was a problem creating your account. Please try again.";
            }
        }

        view.setSubmitButtonName(SUBMIT_BUTTON_NAME);
        view.setSubmitButtonValue(SUBMIT_BUTTON_VALUE);
        view.setErrorMessage(errorMessage);
        request.setAttribute("SignupView", view);
        return null;
    }

    private boolean verifyCaptcha(HttpServletRequest request) {
        ReCaptchaImpl captcha = new ReCaptchaImpl();
        captcha.setPrivateKey(Constants.CAPTCHA_PRIVATE_KEY);
        captcha.setPublicKey(Constants.CAPTCHA_PUBLIC_KEY);
        String challenge = RequestUtils.getParameterAsString(request, "recaptcha_challenge_field", "");
        String answer = RequestUtils.getParameterAsString(request, "recaptcha_response_field", "");
        ReCaptchaResponse response = captcha.checkAnswer(request.getRemoteAddr(), challenge, answer);
        return response.isValid();
    }

    private void populateUserInfo(HttpServletRequest request, SignupView view) {

        // Set required info
        view.setUsername(RequestUtils.getCleanedParameter(request, "username"));
        view.setEmail(RequestUtils.getCleanedParameter(request, "email"));
        view.setVerifyEmail(RequestUtils.getCleanedParameter(request, "verify_email"));
        view.setPassword(RequestUtils.getCleanedParameter(request, "password"));
        view.setVerifyPassword(RequestUtils.getCleanedParameter(request, "verify_password"));

        // Set optional info
        view.getUser().setRealName(RequestUtils.getCleanedParameter(request, "real_name"));
        view.getUser().setHometown(RequestUtils.getCleanedParameter(request, "hometown"));
        view.getUser().setLocation(RequestUtils.getCleanedParameter(request, "location"));
        view.getUser().setCollege(RequestUtils.getCleanedParameter(request, "college"));
        view.getUser().setHighSchool(RequestUtils.getCleanedParameter(request, "high_school"));
        view.getUser().setZipCode(RequestUtils.getCleanedParameter(request, "zip_code"));

        // Set gender
        String gender = request.getParameter("gender");
        if (gender != null)
            if (gender.equalsIgnoreCase("M"))
                view.getUser().setGender("M");
            else if (gender.equalsIgnoreCase("F"))
                view.getUser().setGender("F");

        // Set birthday
        String birthday = RequestUtils.getCleanedParameter(request, "birthday");
        if (!StringUtils.isEmpty(birthday)) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            try {
                view.getUser().setBirthday(sdf.parse(birthday));
            } catch (Exception e) {
                log.error("Could not parse birthday", e);
            }
        }
    }
}
