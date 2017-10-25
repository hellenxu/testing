package ca.six.unittestapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.widget.TextView;

import static android.os.Process.myPid;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-24.
 */

public class Utils {

    public static void setCurrentProcessName(TextView textView, Context actCtx) {
        if (!(actCtx instanceof Activity)) {
            return;
        }

        ActivityManager activityManager = (ActivityManager) actCtx.getSystemService(Context.ACTIVITY_SERVICE);
        for(RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
            if(processInfo.pid == myPid()) {
                System.out.println("xxl: process = " + processInfo.processName);
                textView.setText(processInfo.processName);
                break;
            }
        }
    }
}
