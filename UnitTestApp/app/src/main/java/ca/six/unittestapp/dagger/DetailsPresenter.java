package ca.six.unittestapp.dagger;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-05-30.
 */
public class DetailsPresenter implements IPresenter {
        private IView view;

    public DetailsPresenter(IView view) {
        this.view = view;
    }

    @Override
    public void init() {
        System.out.println("xxl-DetailsPresenter-init()");
        view.onViewReady();
    }
}
