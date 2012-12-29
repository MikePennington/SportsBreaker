package com.breaker.utils;

import javax.servlet.http.HttpServletRequest;

public class BrowserDetector {

    private boolean firefoxAny;
    private boolean firefox2;
    private boolean firefox3;
    private boolean ieAny;
    private boolean ie6;
    private boolean ie7;
    private boolean chromeAny;
    private boolean safariAny;
    private boolean safari3;
    private boolean safari4;
    boolean         unknown;

    public BrowserDetector(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        populate(userAgent);
    }

    public BrowserDetector(String userAgent) {
        populate(userAgent);
    }

    private void populate(String userAgent) {
        userAgent = userAgent.toLowerCase();
        if (userAgent.indexOf("firefox/") > -1) {
            firefoxAny = true;
            if (userAgent.indexOf("firefox/2") > -1)
                firefox2 = true;
            else if (userAgent.indexOf("firefox/3") > -1)
                firefox3 = true;
        } else if (userAgent.indexOf("msie") > -1) {
            ieAny = true;
            if (userAgent.indexOf("msie 6") > -1)
                ie6 = true;
            else if (userAgent.indexOf("msie 7") > -1)
                ie7 = true;
        } else if (userAgent.indexOf("chrome") > -1) {
            chromeAny = true;
        } else if (userAgent.indexOf("safari") > -1) {
            safariAny = true;
            if (userAgent.indexOf("version/3") > -1)
                safari3 = true;
            else if (userAgent.indexOf("version/4") > -1)
                safari4 = true;
        } else {
            unknown = true;
        }
    }

    public boolean isFirefoxAny() {
        return firefoxAny;
    }

    public boolean isFirefox2() {
        return firefox2;
    }

    public boolean isFirefox3() {
        return firefox3;
    }

    public boolean isIeAny() {
        return ieAny;
    }

    public boolean isIe6() {
        return ie6;
    }

    public boolean isIe7() {
        return ie7;
    }

    public boolean isChromeAny() {
        return chromeAny;
    }

    public boolean isSafariAny() {
        return safariAny;
    }

    public boolean isSafari3() {
        return safari3;
    }

    public boolean isSafari4() {
        return safari4;
    }

    public boolean isUnknown() {
        return unknown;
    }

}
