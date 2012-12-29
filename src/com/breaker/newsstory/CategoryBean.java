package com.breaker.newsstory;

/**
 * A bean class for holding information about a category. A category is usually a type of sport or sports league.
 * 
 * @author Mike
 */
public class CategoryBean
{

    private int    id;
    private String name;
    private int    active;
    private int    parentCategoryId;
    private int    sortOrder;
    private int    showInHeader;

    public int getActive()
    {
        return active;
    }

    public void setActive(int active)
    {
        this.active = active;
    }

    public int getParentCategoryId()
    {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId)
    {
        this.parentCategoryId = parentCategoryId;
    }

    public int getShowInHeader()
    {
        return showInHeader;
    }

    public void setShowInHeader(int showInHeader)
    {
        this.showInHeader = showInHeader;
    }

    public int getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}