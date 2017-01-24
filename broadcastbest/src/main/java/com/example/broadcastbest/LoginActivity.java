package com.example.broadcastbest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivty{
   private EditText email;
    private EditText password;
    private Button email_sign_in_button;
    private CheckBox rembberpa;
    private boolean isrember;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        email_sign_in_button= (Button) findViewById(R.id.email_sign_in_button);
        rembberpa= (CheckBox) findViewById(R.id.rembberpa);
        isrember=mSharedPreferences.getBoolean("is_rember",false);
        if(isrember){
            String user=mSharedPreferences.getString("user","");
            String password1=mSharedPreferences.getString("password","");
            email.setText(user);
            password.setText(password1);
            rembberpa.setChecked(true);
        }
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=email.getText().toString();
                String password1=password.getText().toString();
                if(user.equals("admin")&&password1.equals("123456")){
                    mEditor=mSharedPreferences.edit();
                    if(rembberpa.isChecked()){
                    mEditor.putBoolean("is_rember",true);
                    mEditor.putString("user",user);
                    mEditor.putString("password",password1);
                    }else{
                        mEditor.clear();
                    }
                    mEditor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this,"密码或帐号错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

