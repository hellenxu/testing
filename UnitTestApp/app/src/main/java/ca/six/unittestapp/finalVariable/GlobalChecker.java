package ca.six.unittestapp.finalVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2018-04-17.
 */

public class GlobalChecker {
    public final List<GlobalCheckerListener> listenerList;

    public GlobalChecker() {
        listenerList = new ArrayList<>();
    }
}

interface GlobalCheckerListener{
    void onGlobalStatusChanged();
}
