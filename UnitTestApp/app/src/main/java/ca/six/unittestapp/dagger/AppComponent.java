package ca.six.unittestapp.dagger;

import android.content.Context;
import android.content.res.Resources;

import dagger.Component;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getAppContext();
    Resources getResources();
}
