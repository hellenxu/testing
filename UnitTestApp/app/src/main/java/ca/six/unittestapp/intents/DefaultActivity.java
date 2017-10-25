package ca.six.unittestapp.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import ca.six.unittestapp.Constants;
import ca.six.unittestapp.R;

import static ca.six.unittestapp.Constants.INTENTS_ACTION;
import static ca.six.unittestapp.Constants.INTENTS_CATEGORY;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-25.
 */

public class DefaultActivity extends Activity {
    private EditText etInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        etInput = (EditText) findViewById(R.id.et_input);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(createIntent(), Constants.REQUEST_CODE_DATA);
            }
        });
    }

    private Intent createIntent(){
        Intent it = new Intent(this, DataHandleActivity.class);
        it.addCategory(INTENTS_CATEGORY);
        it.setAction(INTENTS_ACTION);
        it.setData(Uri.parse("input:" + etInput.getText().toString()));
        return it;
    }
}
