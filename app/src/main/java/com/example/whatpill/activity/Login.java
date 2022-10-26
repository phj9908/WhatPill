package com.example.whatpill.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import com.example.whatpill.R;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText edtID,edtPASS;
    private FirebaseAuth firebaseAuth;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edtID = findViewById(R.id.edtID);
        edtPASS = findViewById(R.id.edtPASS);
        tvRegister = findViewById(R.id.tvRegister);

        firebaseAuth =  FirebaseAuth.getInstance();

        // 회원가입
        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login.this,Register.class));
            }
        });

        // 로그인
        btnLogin.setOnClickListener(new View.OnClickListener() {
            //            @Override
//            public void onClick(View view) {
//
//                String id = edtID.getText().toString().trim();
//                String pw = edtPASS.getText().toString().trim();
//                if(id.equals("")|pw.equals("")) {
//                    Toast.makeText(Login.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    firebaseAuth.signInWithEmailAndPassword(id,pw).addOnCompleteListener(
//                            Login.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()){
//                                        Log.d("Register", "로그인 성공:: " + id + " , " + pw);
//                                        Intent intent = new Intent(getApplicationContext(), Menu.class);
//                                        startActivity(intent);
//
//                                    }else{
//                                        Log.d("Register", "로그인 실패:: " + id + " , " + pw);
//                                        Toast.makeText(Login.this,"아이디 또는 비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                    );
//                }
//            }
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
}