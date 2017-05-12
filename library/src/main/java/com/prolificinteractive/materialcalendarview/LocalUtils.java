package com.prolificinteractive.materialcalendarview;

import java.util.Locale;

/**
 * Created by Anas AlaaEldin on 5/12/2017.
 */

public class LocalUtils {
    public static boolean isRTL() {
        return isRTL(Locale.getDefault());
    }

    private static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
}
