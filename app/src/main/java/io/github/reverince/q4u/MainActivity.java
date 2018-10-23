package io.github.reverince.q4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "io.github.reverince.q4u.extra.MESSAGE";
    public static final int LOGIN_REQUEST = 1;
    public List<Message> messageList;
    public RecyclerView recyclerView;
    public ChatBoxAdapter chatBoxAdapter;

    private EditText mEditTextMessage;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://levnode.run.goorm.io");
        } catch (URISyntaxException e) {}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextMessage = findViewById(R.id.editText_message);

        messageList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_messages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mSocket.connect();

        Intent intent = getIntent();
        String name = intent.getStringExtra(LoginActivity. EXTRA_NAME);
        mSocket.emit("join", name);

        mSocket.on("toast", new Emitter.Listener() {
           @Override
           public void call(final Object... args) {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       String msg = (String) args[0];
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
                            Message m = new Message(name, message);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    public void sendMessage(View view) {
        String message = mEditTextMessage.getText().toString();
        if (!message.isEmpty()) {
            mSocket.emit("chat message", message);
            mEditTextMessage.setText("");
        }
    }
}
