package com.excilys.mviegas.computerdatabaseandroid;

import android.os.Looper;

/**
 * Created by excilys on 08/11/16.
 */

public final class TestUtils {

    public static void loop() {
        Looper.prepare();
        Looper.loop();
    }
}
