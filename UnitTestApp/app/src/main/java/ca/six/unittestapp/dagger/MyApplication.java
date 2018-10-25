package ca.six.unittestapp.dagger;

import android.app.Application;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */
public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
