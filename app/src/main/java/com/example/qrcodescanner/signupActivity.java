package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupActivity extends AppCompatActivity {

    private EditText name, email, password, reenterpass;
    private Button button;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();


        //firebase
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(signupActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                if(checkCredentials()){
                    mLoadingBar.setTitle("Registration");
                    mLoadingBar.setMessage("Please wait, checking your credentials");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();

                    //Firebase Auth
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(signupActivity.this, "Registered", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(signupActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(signupActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                mLoadingBar.dismiss();
                            }
                        }
                    });



                } //if bracket
            }
        });



    }

    private boolean checkCredentials() {

        String username=name.getText().toString();
        String useremail=email.getText().toString();
        String pass1=password.getText().toString();
        String pass2=reenterpass.getText().toString();

        if(username.equals("")){
            showError(name, "Please input a username");
            return false;
        }

        if (useremail.equals("") || !useremail.contains("@")){
            showError(email, "Email is not Valid");
            return false;
        }

        if (pass1.equals("")){
            showError(password, "Input Password");
            return false;
        }

        if (pass1.length() < 6){
            showError(password, "Weak Password");
            return false;
        }

        if (pass2.equals("") || !pass2.equals(pass1)){
            showError(reenterpass, "Password not match");
            return false;
        }
        return true;




    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }


    private void initViews() {
        name=findViewById(R.id.editTxtUsername);
        email=findViewById(R.id.editTextTextEmail);
        password=findViewById(R.id.password);
        reenterpass=findViewById(R.id.editTextTextReEnterPassword);
        button=findViewById(R.id.button);

    }
}