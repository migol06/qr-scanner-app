package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView signup, forgotpassword;
    private EditText username, password;
    private Button button;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(MainActivity.this);


        initViews();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, signupActivity.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                alertDialog.setTitle("Reset Password");
                alertDialog.setMessage("Enter your email");
                alertDialog.setView(resetMail);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.sendPasswordResetEmail(resetMail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset link is sent, Check your email", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error! Reset link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alertDialog.show();



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCredentials()){
                    mLoadingBar.setTitle("Login");
                    mLoadingBar.setMessage("Please wait, checking your credentials");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();

                    mAuth.signInWithEmailAndPassword(username.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        mLoadingBar.dismiss();
                                    }else{
                                        Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        mLoadingBar.dismiss();
                                    }
                                }
                            });
                }

            }
        });


    }


    private boolean checkCredentials() {
        String name=username.getText().toString().trim();
        String pass1=password.getText().toString().trim();

        if(name.equals("")){
            showError(username, "Please input an email");
            return false;
        }


        if (pass1.equals("")){
            showError(password, "Input Password");
            return false;
        }

        return true;

    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


    private void initViews() {
        signup=findViewById(R.id.Signup);
        username=findViewById(R.id.username);
        password=findViewById(R.id.editTextPassword);
        button=findViewById(R.id.button);
        forgotpassword=findViewById(R.id.forgotpassword);

    }
}