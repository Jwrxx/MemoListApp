package com.example.memolistapp;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.SaveDateListener {
    private Memo currentMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        String note = intent.getStringExtra("note");
        String priority = intent.getStringExtra("priority");
        String date = intent.getStringExtra("date");


        EditText subjectEditText = findViewById(R.id.editMemo);
        EditText noteEditText = findViewById(R.id.editNote);
        RadioGroup priorityRadioGroup = findViewById(R.id.radioGroup);
        TextView dateTextView = findViewById(R.id.editDate);

        subjectEditText.setText(subject);
        noteEditText.setText(note);

        if (currentMemo == null) {
            currentMemo = new Memo(); // Or retrieve an existing memo if needed
        }

        initListButton();
        initSettingsButton();
        initSelectDateButton();
        initSaveButton();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        initListButton();
//        initSettingsButton();
    }

    private void initListButton() {
        ImageButton listButton = findViewById(R.id.imageButtonList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the MemoListActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, MemoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton settingsButton = findViewById(R.id.imageButtonSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the MemoSettingsActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, MemoSettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSelectDateButton() {
        Button selectDate = findViewById(R.id.selectButton);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DatePickerDialog datePickerDialog = new DatePickerDialog();
                datePickerDialog.show(fm, "DatePick");
            }
        });
    }


    @Override
    public void didFinishDatePickerDialog(Calendar selectedTime) {
        TextView theDate = findViewById(R.id.editDate);
        theDate.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
        currentMemo.setDate(selectedTime);
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                hideKeyboard();

                EditText editSubject = findViewById(R.id.editMemo);
                EditText editNote = findViewById(R.id.editNote);

                currentMemo.setSubject(editSubject.getText().toString());
                currentMemo.setNote(editNote.getText().toString());
                currentMemo.setPriority(getPriority());

                boolean wasSuccessful;
                MemoDataSource ds = new MemoDataSource(MainActivity.this);
                try {
                    ds.open();
                    if (currentMemo.getMemoID() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);

                        if (wasSuccessful) {
                            int newId = ds.getLastMemoID();
                            currentMemo.setMemoID(newId);
                        }
                    } else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                    ds.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }
                // Provide feedback to the user
                if (wasSuccessful) {
                    Toast.makeText(MainActivity.this, "Memo saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save memo.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText editSubject = findViewById(R.id.editMemo);
        imm.hideSoftInputFromWindow(editSubject.getWindowToken(), 0);

        EditText editNote = findViewById(R.id.editNote);
        imm.hideSoftInputFromWindow(editNote.getWindowToken(), 0);

    }

    private String getPriority() {
        // Get the RadioGroup from the layout
        RadioGroup priorityGroup = findViewById(R.id.radioGroup);

        // Get the selected RadioButton's ID
        int selectedId = priorityGroup.getCheckedRadioButtonId();

        // Check which RadioButton is selected and return the corresponding priority
        if (selectedId == R.id.radioButtonHigh) {
            return "High";
        } else if (selectedId == R.id.radioButtonMedium) {
            return "Medium";
        } else if (selectedId == R.id.radioButtonLow) {
            return "Low";
        } else {
            return "Low"; // Default priority if nothing is selected
        }
    }
}