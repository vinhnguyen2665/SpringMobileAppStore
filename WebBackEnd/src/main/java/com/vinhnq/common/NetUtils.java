package com.vinhnq.common;

import javax.servlet.http.HttpServletRequest;

public class NetUtils {
    public static String getURL(HttpServletRequest request) {
        String urlRequest = request.getRequestURL().toString();
        urlRequest = urlRequest.replaceAll(request.getRequestURI(), "");
        return urlRequest;
    }
}
