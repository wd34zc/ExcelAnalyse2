package joe.util;

import javax.servlet.http.HttpSession;

public class UrlUtil {
    public static String getProjectPath(HttpSession session){
        return session.getServletContext().getRealPath("/WEB-INF/excel");
    }

    public static String getOriginalPath(HttpSession session){
        return UrlUtil.getProjectPath(session) + "\\original";
    }

    public static String getIntermediatePath(HttpSession session){
        return UrlUtil.getProjectPath(session) + "\\intermediate";
    }

    public static String getProductPath(HttpSession session){
        return UrlUtil.getProjectPath(session) + "\\product";
    }

    public static String getTempletPath(HttpSession session){
        return UrlUtil.getProjectPath(session) + "\\templet";
    }
}
