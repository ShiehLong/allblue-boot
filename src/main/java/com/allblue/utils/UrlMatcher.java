package com.allblue.utils;

/**
 * @Description:
 * @Author Xone
 * @Date 17:11 2018/12/1
 **/
public interface UrlMatcher {
    Object compile(String paramString);

    boolean pathMatchesUrl(Object paramObject, String paramString);

    String getUniversalMatchPattern();

    boolean requiresLowerCaseUrl();
}
