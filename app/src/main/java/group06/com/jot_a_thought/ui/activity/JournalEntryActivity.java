package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import group06.com.jot_a_thought.R;

public class JournalEntryActivity extends AppCompatActivity {

    String journalEntry = "";
    String title;
    EditText sJournalEntry;
    EditText sTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);

        title = getIntent().getStringExtra("JournalTitle");
        try {
            readFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void readFile() throws FileNotFoundException {
        TextView screenTitle = findViewById(R.id.activity_journal_title);
        screenTitle.setText(title);
        screenTitle.setFocusable(false);
        screenTitle.setInputType(InputType.TYPE_NULL);

        FileReader fr = new FileReader(new File(getExternalFilesDir(null), title));

        try {
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                journalEntry += line;
            }
            br.close();
        }
        catch (IOException e) {
            Log.i("Reading Error", "Reading Error");
        }


        TextView screenJournalEntry = findViewById(R.id.activity_journal_entry);
        screenJournalEntry.setText(journalEntry);
    }

    public void onClick(View v) throws IOException {
        sJournalEntry = findViewById(R.id.activity_journal_entry);
        sTitle = findViewById(R.id.activity_journal_title);

        String journalEntry = sJournalEntry.getText().toString();
        String title = sTitle.getText().toString();

        File file = new File(getExternalFilesDir(null),title);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(journalEntry);
            writer.flush();
            writer.close();
            Log.i("Saved in Journal Entry", "File Saved");
        } catch (IOException e) {
            Log.i("Saving in Journal Entry", "Error file not saved");
            e.printStackTrace();
        }

        finish();
    }
}

