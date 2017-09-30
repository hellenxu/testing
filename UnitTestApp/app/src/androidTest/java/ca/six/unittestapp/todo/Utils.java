package ca.six.unittestapp.todo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-09-30.
 */

public class Utils {

    private static void setPortraitOrientation(Activity act) {
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private static void setLandscapeOrientation(Activity act) {
        act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void changeOrientation(Activity act) {
        int currentOrientation = act.getResources().getConfiguration().orientation;

        switch (currentOrientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setPortraitOrientation(act);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setLandscapeOrientation(act);
                break;
        }
    }
}
