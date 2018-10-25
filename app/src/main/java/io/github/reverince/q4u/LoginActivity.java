package io.github.reverince.q4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;

public class LoginActivity extends AppCompatActivity {
    public static final String NAME_PREF = "io.github.reverince.q4u.name";
    private SharedPreferences namePref;
    private static EditText mName;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        namePref = getSharedPreferences(NAME_PREF, MODE_PRIVATE);
        mName = findViewById(R.id.editText_name);

        ChatApplication app = (ChatApplication) getApplication();
        mSocket = app.getSocket();
        if (mSocket.connected()) {
            String name = loadName();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void loginButton(View view) {
        String name = mName.getText().toString();
        if (!name.isEmpty()) {
            saveName(name);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.please_input_name), Toast.LENGTH_SHORT).show();
        }
    }

    private String loadName() {
        return namePref.getString(NAME_PREF, "null");
    }

    public void saveName(String name) {
        SharedPreferences.Editor editor = namePref.edit();
        editor.putString(NAME_PREF, name);
        editor.commit();
    }
}
