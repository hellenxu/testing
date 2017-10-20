package ca.six.unittestapp.idlingres;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-19.
 */

public class LoginIdlingResource implements IdlingResource {

    private AtomicBoolean isIdle = new AtomicBoolean(true);
    private volatile ResourceCallback resCallback;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resCallback = callback;
    }

    //why need this method?
    public void setIdleState(boolean isIdle){
        this.isIdle.set(isIdle);

        if(isIdle && resCallback != null) {
            resCallback.onTransitionToIdle();
        }
    }
}
