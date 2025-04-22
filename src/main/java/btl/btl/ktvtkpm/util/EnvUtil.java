package btl.btl.ktvtkpm.util;

import jakarta.servlet.ServletContext;
import btl.btl.ktvtkpm.listener.AppContextListener;

public class EnvUtil {
    public static String get(String key) {
        ServletContext context = AppContextListener.getServletContext();
        if (context != null) {
            return context.getInitParameter(key);
        }
        return null;
    }
}
