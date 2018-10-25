package ca.six.unittestapp.dagger;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */
public class DetailsActivity extends Activity implements IView{
    @Inject Context app;
    @Inject Resources res;
    @Inject DetailsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerDetailsComponent.builder()
                .appComponent(((MyApplication)getApplication()).getAppComponent())
                .detailsModule(new DetailsModule(this))
                .build()
                .inject(this);

        System.out.println("xxl-app = " + app);
        System.out.println("xxl-res = " + res);

        presenter.init();
    }

    @Override
    public void onViewReady() {
        System.out.println("xxl-onViewReady");
    }
}
