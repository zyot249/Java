package com.zyot.shyn.utils;

import java.util.Map;

public class PageRouter {
    public static final String INDEX_PAGE = "index";
    public static final String LOGIN_PAGE = "login";
    public static final String VIEW_PAGE = "viewpage";
    public static final String DETAIL_PAGE = "detail";

    private PageRouter() {

    }

    public static String getRedirect(String destination, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("redirect:");
        sb.append(destination);

        if (params != null && params.size() > 0) {
            sb.append("?");

            int i = 0;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());

                if (i++ < params.size() - 1)
                    sb.append("&");
            }
        }

        return sb.toString();
    }
}
