package com.guimatec.suaclinicaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText input_email, input_password;
    TextView btnSignup,btnForgotPass;

    RelativeLayout activity_main;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Visão
        btnLogin = findViewById(R.id.login_btn_login);
        input_email = findViewById(R.id.login_email);
        input_password = findViewById(R.id.login_password);
        btnSignup = findViewById(R.id.login_btn_signup);
        btnForgotPass = findViewById(R.id.login_btn_forgot_password);
        activity_main = findViewById(R.id.activity_main);

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Iniciar Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Verifique se a sessão está ok -> Dasboard
       if (auth.getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this,DashBoard.class));



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn_forgot_password)
        {
            startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            finish();
        }
        else if (view.getId() == R.id.login_btn_signup)
        {
            startActivity(new Intent(MainActivity.this,SignUp.class));
            finish();
        }
        else if (view.getId() == R.id.login_btn_login)
        {
            loginUser(input_email.getText().toString(),input_password.getText().toString());

        }

    }
    private void loginUser(String email, final String password)
    {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            if (password.length() < 6)
                            {
                                Snackbar snackbar = Snackbar.make(activity_main,"O tamanho da senha deve ser maior que 6 digitos",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }
                        else{
                            startActivity(new Intent(MainActivity.this,DashBoard.class));
                        }
                    }
                });
    }

}
























