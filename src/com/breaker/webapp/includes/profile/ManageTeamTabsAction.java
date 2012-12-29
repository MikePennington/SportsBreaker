package com.breaker.webapp.includes.profile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.breaker.newsstory.CategoryBean;
import com.breaker.newsstory.CategoryFactory;
import com.breaker.webapp.MVCActionInterface;
import com.breaker.webapp.MVCRedirector;

public class ManageTeamTabsAction implements MVCActionInterface {

    public MVCRedirector doAction(HttpServletRequest httpservletrequest,
            HttpServletResponse httpservletresponse) {
        CategoryFactory catFact = new CategoryFactory();
        List<CategoryBean> categories = catFact
                .getDisplayCategories(CategoryFactory.ORDER_BY_SORT_ORDER);

        return null;
    }

}
