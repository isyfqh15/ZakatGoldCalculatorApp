package com.example.zakatgoldcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView tvGithubLink;
    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //  title untuk App Bar
        getSupportActionBar().setTitle("About");
        // tmbh "back" arrow to return to the main page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvGithubLink = findViewById(R.id.tvGithubLink);

        tvGithubLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "GitHub link clicked.");
                String url = "https://github.com/isyfqh15/ZakatGoldCalculatorApp";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
