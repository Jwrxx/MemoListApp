package com.example.memolistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
        initSortByClick();
        initSortOrderClick();
        initSearchButton();

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

        String sortMemosBy = getSharedPreferences("MemoPreferences", MODE_PRIVATE)
                .getString("sortMemosBy", "priority");
        String sortOrder = getSharedPreferences("MemoPreferences", MODE_PRIVATE)
                .getString("sortOrder", "ascending");

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

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String sortField;
                if (checkedId == R.id.radioButtonPriority) {
                    sortField = "priority";
                } else if (checkedId == R.id.radioButtonDate) {
                    sortField = "date";
                } else {
                    sortField = "subject";
                }

                getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortMemosBy", sortField)
                        .apply();
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupSortOrder);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String sortOrder;
                if (checkedId == R.id.radioAscending) {
                    sortOrder = "ascending";
                } else {
                    sortOrder = "descending";
                }

                getSharedPreferences("MemoPreferences", Context.MODE_PRIVATE).edit()
                        .putString("sortOrder", sortOrder)
                        .apply();
            }
        });
    }
    private void initSearchButton() {
        Button searchButton = findViewById(R.id.searchButton);
        EditText editSearch = findViewById(R.id.editSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = editSearch.getText().toString();
                if (!query.isEmpty()) {
                    // Pass search query to list activity
                    Intent intent = new Intent(MemoSettingsActivity.this, MemoListActivity.class);
                    intent.putExtra("SEARCH_QUERY", query);
                    startActivity(intent);
                }
            }
        });

    }

}
