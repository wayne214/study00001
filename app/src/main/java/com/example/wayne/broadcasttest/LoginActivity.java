package com.example.wayne.broadcasttest;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private EditText inputName,inputPwd;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputName = (EditText) findViewById(R.id.inputName);
        inputPwd = (EditText) findViewById(R.id.inputPwd);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            inputName.setText(account);
            inputPwd.setText(password);
            rememberPass.setChecked(true);
        }
    }

    public void login(View view) {
        String account = inputName.getText().toString();
        String password = inputPwd.getText().toString();
        if (account.equals("admin") && password.equals("123456")){
            editor = pref.edit();
            if (rememberPass.isChecked()){
                editor.putBoolean("remember_password",true);
                editor.putString("account",account);
                editor.putString("password",password);
            }else {
                editor.clear();
            }
            editor.apply();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "accout or password is invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
