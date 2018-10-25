package io.github.reverince.q4u;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class ChatApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("https://levnode.run.goorm.io");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public ChatApplication() { mSocket.connect(); }

    public Socket getSocket() { return mSocket; }
}
