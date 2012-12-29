package com.breaker.newsstory;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.breaker.utils.HibernateUtil;

/**
 * Class for retrieving List's of categories from the database.
 * 
 * @author Mike
 */
public class CategoryFactory {

    private static final Logger logger              = Logger.getLogger(CategoryFactory.class);
    public static final int     ORDER_BY_SORT_ORDER = 0;
    public static final int     ORDER_BY_NAME       = 1;

    private boolean             returnNationalCategory;
    private int                 maxCategoriesToReturn;

    public static CategoryBean getCategoryById(int categoryId) {
        CategoryBean categoryBean = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            categoryBean = (CategoryBean) session.get(CategoryBean.class, categoryId);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }
        return categoryBean;
    }

    public CategoryFactory() {
        returnNationalCategory = true;
        maxCategoriesToReturn = -1;
    }

    public List<CategoryBean> getDisplayCategories(int orderBy) {
        List<CategoryBean> result = null;

        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(CategoryBean.class);
            criteria.add(Restrictions.eq("showInHeader", 1));
            if (!returnNationalCategory)
                criteria.add(Restrictions.ne("id", 1));
            if (orderBy == ORDER_BY_NAME)
                criteria.addOrder(Order.asc("name"));
            else
                criteria.addOrder(Order.asc("sortOrder"));
            result = criteria.list();
        } finally {
            if (session != null)
                session.getTransaction().commit();
        }

        return result;
    }

    public boolean isReturnNationalCategory() {
        return returnNationalCategory;
    }

    public void setReturnNationalCategory(boolean returnNationalCategory) {
        this.returnNationalCategory = returnNationalCategory;
    }

    public int getMaxCategoriesToReturn() {
        return maxCategoriesToReturn;
    }

    public void setMaxCategoriesToReturn(int maxCategoriesToReturn) {
        this.maxCategoriesToReturn = maxCategoriesToReturn;
    }

}