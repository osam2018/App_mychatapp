package io.github.reverince.q4u;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MemoActivity extends AppCompatActivity {
    public static final String MEMO_PREF = "io.github.reverince.q4u.memo";
    private SharedPreferences memoPref;
    private EditText mEditTextMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memoPref = getSharedPreferences(MEMO_PREF, MODE_PRIVATE);
        mEditTextMemo = findViewById(R.id.editText_memo);
        mEditTextMemo.setText(getMemo());
    }

    private String getMemo() {
        return memoPref.getString(MEMO_PREF, "");
    }

    public void saveMemo(View view) {
        SharedPreferences.Editor editor = memoPref.edit();
        String memo = mEditTextMemo.getText().toString();
        editor.putString(MEMO_PREF, memo);
        editor.commit();
        Toast.makeText(MemoActivity.this, getString(R.string.saved_memo), Toast.LENGTH_SHORT).show();
    }
}
