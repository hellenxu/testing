package ca.six.unittestapp.idlingres;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.six.unittestapp.R;

/**
 * @copyright six.ca
 * Created by Xiaolin on 2017-10-19.
 */

public class MainActivity extends Activity implements LoginHandler.LoginHandlerCallback,
        View.OnClickListener{

    @VisibleForTesting
    private LoginIdlingResource loginIdlingRes;
    private TextView tvResult, tvName, tvPwd;
    private EditText etName, etPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_res);

        tvResult = (TextView) findViewById(R.id.tv_result);
        tvName = (TextView) findViewById(R.id.tv_label_name);
        tvPwd = (TextView) findViewById(R.id.tv_label_pwd);
        etName = (EditText) findViewById(R.id.et_name);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if(loginIdlingRes == null) {
            loginIdlingRes = new LoginIdlingResource();
        }
        return loginIdlingRes;
    }

    @Override
    public void onResult(int resultCode) {
        if(resultCode == LoginHandler.RESULT_SUCCESS) {
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText(String.format("Welcome, %s...", etName.getText()));
            tvName.setVisibility(View.GONE);
            tvPwd.setVisibility(View.GONE);
            etName.setVisibility(View.GONE);
            etPwd.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                LoginHandler.login(etName.getText().toString(), etPwd.getText().toString(),
                        this, loginIdlingRes);
                break;
        }
    }
}
