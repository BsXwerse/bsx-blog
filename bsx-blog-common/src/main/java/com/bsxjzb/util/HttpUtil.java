package com.bsxjzb.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtil {
    public static void writeString(HttpServletResponse response, String body) {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
