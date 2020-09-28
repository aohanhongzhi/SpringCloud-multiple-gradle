package hxy.dream.app.util;

/**
 * @author eric
 * @program eric-dream
 * @description
 * @date 2020/9/28
 */
public class AppVersion {
    public static String getVersion() {
        Package pkg = AppVersion.class.getPackage();
        return (pkg != null ? pkg.getImplementationVersion() : null);
    }
}
