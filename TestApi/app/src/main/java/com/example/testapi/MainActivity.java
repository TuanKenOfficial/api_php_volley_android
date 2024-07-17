package com.example.testapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtPass, edtEmail;
    private Button btnRegister;
    private TextView txtLogin;
    private ProgressDialog progressDialog;
    private static  final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Log.d(TAG, "onCreate: "+SharedPrefManager.getInstance(this).isLoggedIn());
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }



        edtName =findViewById(R.id.edtName);
        edtPass = findViewById(R.id.edtPass);
        edtEmail = findViewById(R.id.edtEmail);

        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    private void RegisterUser(){
        final String username = edtName.getText().toString().trim();
        final String password = edtPass.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();

        progressDialog.setMessage("Đăng ký người dùng");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d(TAG, "onResponse: Đăng ký thành công");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, "Đăng nhập: "+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Lỗi: "+error.getMessage());
                        progressDialog.hide();
                        Toast.makeText(MainActivity.this, "Lỗi: "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("email",email);
                return params;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    @Override
    public void onClick(View v) {
        if (v == btnRegister){
            RegisterUser();
        }
    }
}
