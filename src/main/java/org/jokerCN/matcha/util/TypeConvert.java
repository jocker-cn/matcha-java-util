package org.jokerCN.matcha.util;

/**
 * @author jokerCN <a href="https://github.com/jocker-cn">
 */
public interface TypeConvert {


    static <T> T cast(Object obj, Class<T> al) {
        return al.cast(obj);
    }

    @SuppressWarnings("unchecked")
    static <T> T cast(Object obj) {
        return (T) obj;
    }


    static int castInt(Object obj) {
        return cast(obj, Integer.class);
    }

    static String castString(Object obj) {
        return cast(obj, String.class);
    }

    static double castDouble(Object obj) {
        return cast(obj, Double.class);
    }

    static long castLong(Object obj) {
        return cast(obj, Long.class);
    }

    static float castFloat(Object obj) {
        return cast(obj, Float.class);
    }

    static short castShort(Object obj) {
        return cast(obj, Short.class);
    }

    static byte castByte(Object obj) {
        return cast(obj, Byte.class);
    }

    static boolean castBoolean(Object obj) {
        return cast(obj, Boolean.class);
    }

    static char castChar(Object obj) {
        return cast(obj, Character.class);
    }

    static Void castVoid(Object obj) {
        return cast(obj, Void.class);
    }

    <T> T convert(Object original, Class<T> target);


}
