package com.breaker.webapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is an interface that all actions classes in my cheesy, custom MVC model must implement.
 * 
 * @author michael
 */
public interface MVCActionInterface
{

    public abstract MVCRedirector doAction(HttpServletRequest request, HttpServletResponse response);
}