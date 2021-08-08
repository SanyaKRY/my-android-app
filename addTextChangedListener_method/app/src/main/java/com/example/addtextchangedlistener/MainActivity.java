package com.example.addtextchangedlistener;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final static int RED_COLOR = Color.parseColor("#fb7373");
    final static int GREEN_COLOR = Color.parseColor("#40de83");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        final TextView tvBeforeText = findViewById(R.id.tvBeforeText);
        final TextView tvBeforeNumbers = findViewById(R.id.tvBeforeNumbers);
        final TextView tvAfterText = findViewById(R.id.tvAfterText);
        final TextView tvAfterNumbers = findViewById(R.id.tvAfterNumbers);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                SpannableString spannableString = new SpannableString(s);
                BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(RED_COLOR);
                spannableString.setSpan(backgroundSpan, start, start + count, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvBeforeText.setText(spannableString);
                tvBeforeNumbers.setText("start=" + start + "  count=" + count + " after=" + after + " s=" + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SpannableString spannableString = new SpannableString(s);
                BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(GREEN_COLOR);
                spannableString.setSpan(backgroundSpan, start, start + count, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvAfterText.setText(spannableString);
                tvAfterNumbers.setText("start=" + start + " before=" + before + " count=" + count + " s=" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("TAG", "afterTextChanged: " + s);
            }
        });
    }
}