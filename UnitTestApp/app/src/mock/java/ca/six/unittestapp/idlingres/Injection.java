package ca.six.unittestapp.idlingres;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-19.
 */

public class Injection {

    static boolean checkLoginInfo(String name, String pwd) {
        return name.equals("xxl") && pwd.equals("56");
    }
}
