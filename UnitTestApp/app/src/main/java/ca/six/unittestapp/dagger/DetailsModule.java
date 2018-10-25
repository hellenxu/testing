package ca.six.unittestapp.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */
@Module
public class DetailsModule {
    private IView view;

    public DetailsModule(IView view) {
        this.view = view;
    }

    @Provides DetailsPresenter provideDetailsPresenter(){
        return new DetailsPresenter(view);
    }
}
