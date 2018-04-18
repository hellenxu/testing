package ca.six.unittestapp.finalVariable;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-17.
 */

public class MaintenanceFactory {
    GlobalChecker globalChecker;

    public MaintenanceFactory() {
        globalChecker = new GlobalChecker();
    }

    public void addGlobalListener(GlobalCheckerListener listener) {
        globalChecker.listenerList.add(listener);
    }
}
