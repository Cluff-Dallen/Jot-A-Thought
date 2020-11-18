package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import group06.com.jot_a_thought.R;
import group06.com.jot_a_thought.dao.JournalDAO;


//testing
public class JournalList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this, "Oi Jales", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_journal_list);

        FloatingActionButton buttonNewJournal = findViewById(R.id.activity_journal_list_fab_new_journal);
        buttonNewJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JournalList.this, NewJournalEntryActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        JournalDAO dao = new JournalDAO();

        String path = getExternalFilesDir(null).toString();

        ListView journalEntries = findViewById(R.id.activity_journal_list_listview);

        journalEntries.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                dao.allJournals(path)));

        journalEntries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedJournal = (String) parent.getItemAtPosition(position);
                Log.i("On ListView click", selectedJournal);
                Intent intent = new Intent(JournalList.this, JournalEntryActivity.class);
                intent.putExtra("JournalTitle", selectedJournal);
                startActivity(intent);
            }
        });

    }
}
