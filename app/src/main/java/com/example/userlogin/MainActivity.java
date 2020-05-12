package com.example.userlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button signup;
    private Button login;
    private String url;
    private String endpoint;
    private TextView label;
    private RequestQueue queue;
    private static final int SIGNUP_CODE = 1;
    private static final int SIGNIN_CODE = 2;
    private static final int LOGOUT_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = findViewById(R.id.signupBtn);
        login = findViewById(R.id.loginBtn);
        label = findViewById(R.id.msg);
        queue = Volley.newRequestQueue(this);

        url = getString(R.string.HEROKU_SERVER_URL);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup.class);
                startActivityForResult(i, SIGNUP_CODE);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup.class);
                i.putExtra("Login", true);
                startActivityForResult(i, SIGNIN_CODE);
            }
        });

        processRequest(url, Request.Method.GET, null);
    }

    private void processRequest(String url, int method, JSONObject body) {
        final JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                        label.setText(response.toString());
                        res[0] =  response;
                        if(response.has("token")) {
                            Intent intent = new Intent(MainActivity.this, LoggedIn.class);
                            try {
                                intent.putExtra("token", response.get("token").toString());
                                intent.putExtra("message", response.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivityForResult(intent, LOGOUT_CODE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error: " + error.toString());
                        label.setText(error.toString());
                    }
                });

        queue.add(jsonObjectRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            String email = data.getStringExtra("email");
            String password = data.getStringExtra("password");
            String newurl = "";
            JSONObject body = new JSONObject();
            int code = 0;
            if(requestCode==SIGNUP_CODE) {
                endpoint = "api/account/signup/";
                newurl = url + endpoint;
                code = Request.Method.POST;
                try {
                    body.put("email", email);
                    body.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(requestCode==SIGNIN_CODE) {
                endpoint = "api/account/signin/";
                newurl = url + endpoint;
                code = Request.Method.POST;
                try {
                    body.put("email", email);
                    body.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(requestCode==LOGOUT_CODE) {
                endpoint = "api/account/logout?token=";
                newurl = url + endpoint;
                String token = data.getStringExtra("token");
                newurl += token;
                code = Request.Method.GET;
            }
            processRequest(newurl, code, body);
        }


    }
}
