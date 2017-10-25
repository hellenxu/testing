package ca.six.unittestapp.multiprocesses;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import ca.six.unittestapp.R;
import ca.six.unittestapp.Utils;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-24.
 */

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_processes);
        TextView tvProcessName = (TextView) findViewById(R.id.tv_process_name);
        Utils.setCurrentProcessName(tvProcessName, this);
    }
}
