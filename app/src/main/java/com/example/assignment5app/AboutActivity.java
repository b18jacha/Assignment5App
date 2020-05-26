package com.example.assignment5app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView myTextView = findViewById(R.id.aboutTextView);

        String showTargetGroup = "Denna app lämpar sig för alla campingsugna svenskar denna sommar. " +
                "Nu när coronan är som störst kan det vara skönt att veta vart i Sverige det är säkert att åka till!";

        myTextView.setText(showTargetGroup);

        Button myButton = findViewById(R.id.closeAboutButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}