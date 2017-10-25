package ca.six.unittestapp.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import static ca.six.unittestapp.Constants.DATA_HANDLE_RESULT;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-25.
 */

public class DataHandleActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tvActName = new TextView(this);
        tvActName.setText(DataHandleActivity.class.getSimpleName());
        setContentView(tvActName);

        setResult(RESULT_OK, getDataResult());
        finish();
    }

    private Intent getDataResult() {
        Intent result = new Intent();
        result.putExtra(DATA_HANDLE_RESULT, "success");
        return result;
    }
}
