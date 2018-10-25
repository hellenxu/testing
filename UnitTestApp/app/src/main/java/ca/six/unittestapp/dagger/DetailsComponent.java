package ca.six.unittestapp.dagger;

import dagger.Component;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */

@Component(dependencies = AppComponent.class, modules = {DetailsModule.class})
public interface DetailsComponent {
    void inject(DetailsActivity details);
}
