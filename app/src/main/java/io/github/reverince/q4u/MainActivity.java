package io.github.reverince.q4u;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String NAME_PREF = "io.github.reverince.q4u.name";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "io.github.reverince.q4u.extra.MESSAGE";
    public List<Message> messageList;
    public RecyclerView recyclerView;
    public ChatBoxAdapter chatBoxAdapter;
    private EditText mEditTextMessage;
    private SharedPreferences namePref;
    private ChatApplication app;
    private Socket mSocket;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namePref = getSharedPreferences(NAME_PREF, MODE_PRIVATE);
        mEditTextMessage = findViewById(R.id.editText_message);

        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        app = (ChatApplication) getApplication();
        mSocket = app.getSocket();

        name = loadName();
        mSocket.emit("join", name);

        mSocket.on("toast join", new Emitter.Listener() {
           @Override
           public void call(final Object... args) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       String msg = (String) args[0];
                       msg += "님이 채팅에 참가했습니다";
                       Log.d(LOG_TAG, msg);
                       Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                   }
               });
           }
        });
        mSocket.on("toast disconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String msg = (String) args[0];
                        msg += "님이 채팅에서 떠났습니다";
                        Log.d(LOG_TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSocket.on("chat message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            String name = data.getString("name");
                            String message = data.getString("message");
                            String color = data.getString("color");
                            Log.d(LOG_TAG, message);
                            Message m = new Message(name, message, color);
                            messageList.add(m);
                            chatBoxAdapter = new ChatBoxAdapter(messageList);
                            chatBoxAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(chatBoxAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void sendMessage(View view) {
        String message = mEditTextMessage.getText().toString();
        if (!message.isEmpty()) {
            mSocket.emit("chat message", message);
            mEditTextMessage.setText("");
        }
    }

    public void openMemo(View view) {
        Intent intent = new Intent(this, MemoActivity.class);
        saveName(name);
        startActivity(intent);
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
