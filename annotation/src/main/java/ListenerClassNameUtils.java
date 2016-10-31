/**
 * Author: hzlishang
 * Data: 16/10/31 下午4:10
 * Des:
 * version:
 */
public class ListenerClassNameUtils {

    public static String getRemixClassName(Object object, String[] strings) {

        return object.getClass().getSimpleName() + strings.hashCode() + "$$Listener";
    }
}
