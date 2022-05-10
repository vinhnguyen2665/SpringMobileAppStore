package com.vinhnq.common;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

public class NetUtils {
    public static String getURL(HttpServletRequest request) {
        String urlRequest = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        urlRequest = urlRequest.replaceAll(request.getRequestURI(), "") + contextPath;
        return urlRequest;
    }

    public static String getHttpsURL(HttpServletRequest request) {
        try {
            String scheme = "https"; //request.getScheme()
            String serverName = request.getServerName();
            int serverPort = request.getServerPort() == 80 ? -1 : request.getServerPort();
            String contextPath = request.getContextPath();
            URL urlRequest = new URL(scheme, serverName, serverPort,contextPath);
            return urlRequest.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
