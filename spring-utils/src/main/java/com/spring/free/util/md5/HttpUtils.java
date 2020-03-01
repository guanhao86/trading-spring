package com.spring.free.util.md5;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Memory
 * @date 2018/3/2
 * @Inc 恒谱科技
 */
public class HttpUtils {

    private static String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)){
                return true;
            }
        }
        return false;
    }

}
