package group06.com.jot_a_thought.dao;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JournalDAO extends AppCompatActivity {


    public ArrayList<String> allJournals(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        ArrayList<String> journalsNames = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().equals("PIN")){
                    continue;
                }
                journalsNames.add(files[i].getName() + "\n"
                        + sdf.format(files[i].lastModified()));
            }
        }
        return journalsNames;
    }
}