package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);

        Intent intent = getIntent();
        final boolean isLogin = intent.getBooleanExtra("Login", false);

        if(isLogin) {
            submit.setText("Login");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("password", password.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
