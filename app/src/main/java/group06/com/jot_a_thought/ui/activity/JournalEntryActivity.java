package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

import group06.com.jot_a_thought.R;

//Image feature imports here
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class JournalEntryActivity extends AppCompatActivity {

    String journalEntry = "";
    String title;
    EditText sJournalEntry;
    EditText sTitle;

    //Image feature code here
    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

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

        //Image feature code here
        button = (Button)findViewById(R.id.buttonLoadPicture);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_journal_entry_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_journal_list_menu_save) {
            //readFile();
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
        } else if (itemId == R.id.activity_journal_list_menu_delete) {
            File file = new File(getExternalFilesDir(null),title);
            if (file.delete()) {
                System.out.println("Deleted the file: " + file.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
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

    /*@RequiresApi(api = Build.VERSION_CODES.O)
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
    }*/
    public void Delete(View view){
        /*
        EditText journal = (EditText) findViewById(R.id.activity_journal_entry);
        EditText journalTitle = (EditText) findViewById(R.id.activity_journal_title);
        journal.getText().clear();
         */

        File file = new File(getExternalFilesDir(null),title);
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        finish();

    }

}

