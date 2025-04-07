package com.example.memolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MemoSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo_settings);
        initListButton();
        initSettingsButton();
        initMemoSettings();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initListButton() {
        ImageButton listButton = findViewById(R.id.imageButtonList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoSettingsActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton listButton = findViewById(R.id.imageButtonSettings);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemoSettingsActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initMemoSettings() {

        String sortMemosBy = getSharedPreferences("MemoSettings", MODE_PRIVATE).getString("sortMemosBy", "priority");
        String sortOrder = getSharedPreferences("MemoSettings", MODE_PRIVATE).getString("sortOrder", "ascending");

        RadioButton radioButtonPriority = findViewById(R.id.radioButtonPriority);
        RadioButton radioButtonDate = findViewById(R.id.radioButtonDate);
        RadioButton radioButtonSubject = findViewById(R.id.radioButtonSubject);

        if (sortMemosBy.equals("priority")) {
            radioButtonPriority.setChecked(true);
        } else if (sortMemosBy.equals("date")) {
            radioButtonDate.setChecked(true);
        } else {
            radioButtonSubject.setChecked(true);
        }

        RadioButton radioAscending = findViewById(R.id.radioAscending);
        RadioButton radioDescending = findViewById(R.id.radioDescending);

        if (sortOrder.equals("ascending")) {
            radioAscending.setChecked(true);
        } else {
            radioDescending.setChecked(true);
        }
    }
}
