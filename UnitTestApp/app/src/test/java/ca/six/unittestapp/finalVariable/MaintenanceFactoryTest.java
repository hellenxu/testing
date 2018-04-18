package ca.six.unittestapp.finalVariable;

import org.junit.Assert;
import org.junit.Test;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-17.
 */

public class MaintenanceFactoryTest {
    @Test
    public void addGlobalListener () {

        MaintenanceFactory maintenanceFactory = new MaintenanceFactory();
        GlobalChecker checker = new GlobalChecker();
        maintenanceFactory.globalChecker = checker;

        GlobalCheckerListener listener = new GlobalCheckerListener() {
            @Override
            public void onGlobalStatusChanged() {

            }
        };

        Assert.assertEquals(0, checker.listenerList.size());
        Assert.assertFalse(checker.listenerList.contains(listener));

        maintenanceFactory.addGlobalListener(listener);

        Assert.assertEquals(1, checker.listenerList.size());
        Assert.assertTrue(checker.listenerList.contains(listener));
    }
}
