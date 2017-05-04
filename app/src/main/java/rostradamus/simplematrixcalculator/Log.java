package rostradamus.simplematrixcalculator;

import java.sql.Timestamp;

/**
 * Created by rolee on 2017-05-03.
 */

public class Log {
    private static final String DEFAULT_INFO_PREFIX = " Log Info -> ";
    private static final String DEFAULT_CATCH_PREFIX = " Expected Error/Exception caught -> ";
    private static final String DEFAULT_ERROR_PREFIX = " Unexpected Error/Exception caught -> ";

    // log for system information
    public static void i(String msg) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts + DEFAULT_INFO_PREFIX + msg);
    }

    // log for unexpected error/exception
    public static void e(String msg) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts + DEFAULT_ERROR_PREFIX + msg);
    }

    // log for expected error/exception caught
    public static void c(String msg) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts + DEFAULT_CATCH_PREFIX + msg);
    }
}
