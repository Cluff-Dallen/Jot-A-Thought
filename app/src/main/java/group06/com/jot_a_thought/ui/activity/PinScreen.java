package group06.com.jot_a_thought.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AtomicFile;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import group06.com.jot_a_thought.R;

public class PinScreen extends AppCompatActivity{

    private static final int ATTEMPT_LIMIT = 4;
    String pin = "";
    String checkPin = "";
    int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_journal);

        // block of code to change actionBar color
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#3F51B5"));
        actionBar.setBackgroundDrawable(colorDrawable);

        System.out.println("pin Screen");
    }

    protected void onResume(){
        super.onResume();
        attempts = 0;
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
        if (pin.length() == 4 && attempts <= ATTEMPT_LIMIT) {
            attempts += 1;
            System.out.println(attempts);
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
                if (attempts <= ATTEMPT_LIMIT) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "PIN Incorrect\nAttempts Left: " + (ATTEMPT_LIMIT - (attempts - 1)),
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    pin = "";
                }
            }
        }
        if(attempts > ATTEMPT_LIMIT){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "PIN Incorrect\nClosing Application",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();System.exit(0);
        }
    }

    public <View> void pinButton(android.view.View v) {
        pin += v.getTag().toString();
        checkPin();
    }

}
