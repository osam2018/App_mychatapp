package io.github.reverince.q4u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "io.github.reverince.q4u.extra.MESSAGE";
    public static final int REPLY_REQUEST = 1;
    private int mCounter = 0;
    private TextView mTextCounter;
    private TextView mTextReply;
    private EditText mEditTextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextCounter = findViewById(R.id.text_counter);
        mTextReply = findViewById(R.id.text_reply);
        mEditTextIntent = findViewById(R.id.editText_intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REPLY_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mTextReply.setText(reply);
                mTextReply.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        mCounter++;
        if (mTextCounter != null) {
            mTextCounter.setText(Integer.toString(mCounter));
        }
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String message = mEditTextIntent.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, REPLY_REQUEST);
    }
}
