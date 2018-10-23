package io.github.reverince.q4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "io.github.reverince.extra.REPLY";
    private EditText mEditTextReply;
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mTextMessage = findViewById(R.id.text_message);
        mTextMessage.setText(message);

        mEditTextReply = findViewById(R.id.editText_reply);
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent(this, SecondActivity.class);
        String reply = mEditTextReply.getText().toString();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}
