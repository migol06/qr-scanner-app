package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signupActivity extends AppCompatActivity {

    private EditText name, email, password, reenterpass;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCredentials()){
                    Toast.makeText(signupActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                }
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
        name=findViewById(R.id.editTextTextName);
        email=findViewById(R.id.editTextTextEmail);
        password=findViewById(R.id.password);
        reenterpass=findViewById(R.id.editTextTextReEnterPassword);
        button=findViewById(R.id.button);
    }
}