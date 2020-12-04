package group06.com.jot_a_thought.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import group06.com.jot_a_thought.R;

public class NewJournalEntryActivity extends AppCompatActivity {

    EditText newJournalEntry;
    EditText newTitle;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal_entry);
        setTitle("New Journal Entry");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new_journal_entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_journal_list_menu_save) {
            try {
                finishJournal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishJournal() throws IOException {
        newJournalEntry = findViewById(R.id.activity_journal_list_entry);
        newTitle = findViewById(R.id.activity_journal_list_title);

        String journalEntry = newJournalEntry.getText().toString();
        title = newTitle.getText().toString();

        File file = new File(getExternalFilesDir(null),title);
        file.createNewFile();

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(journalEntry);
            writer.flush();
            writer.close();
            Log.i("Saving in New journal", "File Saved");
        } catch (IOException e) {
            Log.i("Saving in New Journal", "Error file not saved");
            e.printStackTrace();
        }

        finish();
    }

   /*public void onClick(View v) throws IOException {
            newJournalEntry = findViewById(R.id.activity_journal_list_entry);
            newTitle = findViewById(R.id.activity_journal_list_title);

            String journalEntry = newJournalEntry.getText().toString();
            title = newTitle.getText().toString();

            File file = new File(getExternalFilesDir(null),title);
            file.createNewFile();

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(journalEntry);
                writer.flush();
                writer.close();
                Log.i("Saving in New journal", "File Saved");
            } catch (IOException e) {
                Log.i("Saving in New Journal", "Error file not saved");
                e.printStackTrace();
            }

            finish();
        }*/
        public void Delete(View view){
            finish();
            /*
            EditText journal = (EditText) findViewById(R.id.activity_journal_entry);
            EditText journalTitle = (EditText) findViewById(R.id.activity_journal_title);
            journal.getText().clear();
             */
        }
}
