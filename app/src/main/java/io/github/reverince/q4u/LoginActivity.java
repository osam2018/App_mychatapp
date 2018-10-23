package io.github.reverince.q4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static EditText mUsername;
    private static EditText mPassword;
    private static TextView mAttempt;
    private int cnt_attempt = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.editText_username);
        mPassword = findViewById(R.id.editText_password);
        mAttempt = findViewById(R.id.text_attempt_count);
    }

    public void loginButton(View view) {
        if (mUsername.getText().toString().equals("tester") && mPassword.getText().toString().equals("pw")) {
            Toast.makeText(LoginActivity.this,"Username and password is correct", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("io.github.reverince.q4u.UserActivity");
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this,"Username or password is wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
