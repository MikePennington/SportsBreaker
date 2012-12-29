package com.breaker.db;

public class SQLBuilder
{
    private StringBuilder columns    = new StringBuilder();
    private StringBuilder from       = new StringBuilder();
    private StringBuilder where      = new StringBuilder();
    private StringBuilder orderBy    = new StringBuilder();
    private StringBuilder extraAtEnd = new StringBuilder();

    public String toString()
    {
        if (columns.length() == 0)
            columns.append(" * ");
        return "select " + columns + from + where + orderBy + extraAtEnd;
    }

    public void appendColumn(String column)
    {
        if (columns.length() > 0)
            columns.append(" , ");
        columns.append(" ");
        columns.append(column);
        columns.append(" ");
    }

    public void appendFrom(String fromStatement)
    {
        if (from.length() == 0)
            from.append(" from ");
        else
            from.append(" , ");
        from.append(" ");
        from.append(fromStatement);
        from.append(" ");
    }

    public void appendWhere(String whereStatement)
    {
        if (where.length() == 0)
            where.append(" where ");
        else
            where.append(" and ");
        where.append(" ");
        where.append(whereStatement);
        where.append(" ");
    }

    public void appendOrderBy(String orderStatement)
    {
        if (orderBy.length() == 0)
            orderBy.append(" order by ");
        else
            orderBy.append(" , ");
        orderBy.append(" ");
        orderBy.append(orderStatement);
        orderBy.append(" ");
    }

    public void appendExtraAtEnd(String extra)
    {
        extraAtEnd.append(" ");
        extraAtEnd.append(extra);
        extraAtEnd.append(" ");
    }
}
