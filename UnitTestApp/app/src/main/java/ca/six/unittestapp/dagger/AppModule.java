package ca.six.unittestapp.dagger;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */

@Module
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides public Context provideAppCtx() {
        return app;
    }

    @Provides public Resources provideRes() {
        return app.getResources();
    }
}
