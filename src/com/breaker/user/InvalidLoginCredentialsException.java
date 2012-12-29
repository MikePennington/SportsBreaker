// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 12/16/2007 2:21:12 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   InvalidLoginCredentialsException.java

package com.breaker.user;


// Referenced classes of package com.benchd.user:
//            LoginException

public class InvalidLoginCredentialsException extends LoginException
{

    private static final long serialVersionUID = 1L;
    private String email;

    public InvalidLoginCredentialsException(String email)
    {
        this.email = email;
    }

    public String getMessage()
    {
        return (new StringBuilder("Could not login the user ")).append(email).append(" with the given password").toString();
    }
}