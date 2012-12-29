package com.breaker.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Session;

import com.breaker.newsstory.CategoryBean;
import com.breaker.team.TeamBean;
import com.breaker.utils.HibernateUtil;

public class HibernateTester
{
    public static void main(String args[])
    {
        List<TeamBean> teams = new ArrayList<TeamBean>();
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            List<TeamBean> result = session.createCriteria(TeamBean.class).list();
            ListIterator<TeamBean> iterator = result.listIterator();
            while (iterator.hasNext())
            {
                TeamBean bean = iterator.next();
                teams.add(bean);
            }
        }
        finally
        {
            session.getTransaction().commit();
        }
    }
}
