package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import group06.com.jot_a_thought.R;
import group06.com.jot_a_thought.dao.JournalDAO;


//testing
public class JournalList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journal_list);

        // block of code to change actionBar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#3F51B5"));
        actionBar.setBackgroundDrawable(colorDrawable);

        FloatingActionButton buttonNewJournal = findViewById(R.id.activity_journal_list_fab_new_journal);
        buttonNewJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JournalList.this, NewJournalEntryActivity.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_journal_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_journal_list_menu_dark_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        if (itemId == R.id.activity_journal_list_menu_white_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (itemId == R.id.activity_journal_list_menu_change_PIN) {

            File file = new File(getExternalFilesDir(null),"PIN");

            try {
                FileWriter writer = new FileWriter(file);
                writer.write("");
                writer.flush();
                writer.close();
                Log.i("Saved in PIN", "PIN Saved");
            } catch (IOException f) {
                Log.i("Saving in PIN", "PIN saved");
                f.printStackTrace();
            }

            Intent intent = new Intent(JournalList.this, PinScreen.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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
                String title = selectedJournal.split("\n")[0];
                Log.i("On ListView click", title);
                Intent intent = new Intent(JournalList.this, JournalEntryActivity.class);
                intent.putExtra("JournalTitle", title);
                startActivity(intent);
            }
        });

    }
}
