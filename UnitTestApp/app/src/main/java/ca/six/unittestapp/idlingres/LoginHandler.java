package ca.six.unittestapp.idlingres;

import android.os.Handler;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-19.
 */

class LoginHandler {

    interface LoginHandlerCallback {
        void onResult(int code);
    }

    static final int RESULT_SUCCESS = 1;
    private static final int DELAY_MILLIS = 3000;

    static void login(final String name, final String pwd, final LoginHandlerCallback callback, final LoginIdlingResource idlingRes){
        if(idlingRes != null) {
            idlingRes.setIdleState(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(callback != null && Injection.checkLoginInfo(name, pwd)) {
                    callback.onResult(RESULT_SUCCESS);
                    if(idlingRes != null) {
                        idlingRes.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
