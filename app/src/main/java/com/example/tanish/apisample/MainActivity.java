package com.example.tanish.apisample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jokeButton = findViewById(R.id.jokeButton);
        final TextView jokeTextView = findViewById(R.id.jokeTextView);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String joke = NetworkUtils.getJokes();
                jokeTextView.setText(joke);
            }
        });

    }
}
