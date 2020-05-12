package com.example.userlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedIn extends AppCompatActivity {

    private TextView msgLabel;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        msgLabel = findViewById(R.id.msgLabel);
        logout = findViewById(R.id.logoutBtn);

        Intent i = getIntent();
        final String token = i.getStringExtra("token");
        final String message = i.getStringExtra("message");

        msgLabel.setText(message);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("token", token);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
