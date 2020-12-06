package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import group06.com.jot_a_thought.R;

public class PinScreen extends AppCompatActivity{

    String pin = "";
    String checkPin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_journal);
        System.out.println("pin Screen");
    }

    protected void onResume(){
        super.onResume();
        FileReader fr = null;
        try {
            fr = new FileReader(new File(getExternalFilesDir(null), "PIN"));
        } catch (FileNotFoundException e) {
            TextView screenTitle = findViewById(R.id.pinTitle);
            screenTitle.setText(getString(R.string.createPIN));
            return;
        }

        try {
            BufferedReader br = new BufferedReader(fr);
            String line = "";

            while (true) {
                try {
                    if (!((line = br.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                checkPin += line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Check Pin: " + checkPin);

        TextView screenTitle = findViewById(R.id.pinTitle);
        if(checkPin.equals("")){
            screenTitle.setText(getString(R.string.newPIN));
        }
        else{
            screenTitle.setText(getString(R.string.enterPIN));
        }
    }

    private void checkPin() {
        System.out.println(pin);
        if (pin.length() == 4) {
            if (pin.equals(checkPin)) {
                Intent intent = new Intent(PinScreen.this, JournalList.class);
                startActivity(intent);
                finish();
            } else if (checkPin.equals("")) {
                File file = new File(getExternalFilesDir(null), "PIN");


                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(pin);
                    writer.flush();
                    writer.close();
                    Log.i("Saved in PIN", "PIN Saved");
                } catch (IOException f) {
                    Log.i("Saving in PIN", "PIN saved");
                    f.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "PIN Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PinScreen.this, JournalList.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "PIN Incorrect", Toast.LENGTH_SHORT).show();
                pin = "";
            }
        }
    }

    public <View> void pinButton(android.view.View v) {
        pin += v.getTag().toString();
        checkPin();
    }

}
