package com.example.android.notepad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {

    private EditText userName, password;
    private Button btn_login;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        userName = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_mima);
        btn_login = (Button) findViewById(R.id.btn_login);




        btn_login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();

                if(userNameValue.equals("123")&&passwordValue.equals("123"))
                {
                    Toast.makeText(Login.this,"登录成功", Toast.LENGTH_SHORT).show();
                    //跳转界面
                    Intent intent = new Intent(Login.this,NotesList.class);
                    Login.this.startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(Login.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                }

            }
        });





//
//        btnQuit.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

    }
}
