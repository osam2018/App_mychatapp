package io.github.reverince.q4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "name";
    private static EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mName = findViewById(R.id.editText_name);
    }

    public void loginButton(View view) {
        String name = mName.getText().toString();
        if (!name.isEmpty()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_NAME, name);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this,"Username or password is wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
