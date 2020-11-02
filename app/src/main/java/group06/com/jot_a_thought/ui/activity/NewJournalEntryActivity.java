package group06.com.jot_a_thought.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import group06.com.jot_a_thought.R;
import group06.com.jot_a_thought.dao.JournalDAO;
import group06.com.jot_a_thought.model.Journal;

public class NewJournalEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal_entry);

        setTitle("New Journal Entry");

        JournalDAO dao = new JournalDAO();

        final EditText newJournalEntry = findViewById(R.id.activity_journal_list_entry);
        final EditText newTitle = findViewById(R.id.activity_journal_list_title);

        Button saveButton = findViewById(R.id.activity_journal_list_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NewJournalEntryActivity.this, "Button save Clicked", Toast.LENGTH_SHORT).show();
                String journalEntry = newJournalEntry.getText().toString();
                String title = newTitle.getText().toString();

                Journal journal = new Journal(journalEntry, title);
                //Toast.makeText(NewJournalEntryActivity.this, journal.getJournal(), Toast.LENGTH_SHORT).show();
                dao.save(journal);

                finish();
            }
        });
    }
}