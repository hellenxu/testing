package ca.six.unittestapp.multiprocesses;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.six.unittestapp.R;
import ca.six.unittestapp.Utils;

/**
 * @CopyRight six.ca
 * Created by Heavens on 2017-10-24.
 */

public class PrimaryActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_processes);
        Button btnJump = (Button) findViewById(R.id.btn_jump);
        btnJump.setVisibility(View.VISIBLE);
        btnJump.setOnClickListener(this);
        TextView tvProcessName = (TextView) findViewById(R.id.tv_process_name);
        Utils.setCurrentProcessName(tvProcessName, this);
    }

    @Override
    public void onClick(View view) {
        Intent toSecondAct = new Intent(this, SecondActivity.class);
        startActivity(toSecondAct);
    }
}
