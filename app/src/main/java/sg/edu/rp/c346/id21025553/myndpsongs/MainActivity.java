package sg.edu.rp.c346.id21025553.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTitle, etSinger, etYear;
    RadioGroup rgStars;
    Button btnInsert, btnShowList;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking variables to UI elements
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataTitle = etTitle.getText().toString();
                String dataSinger = etSinger.getText().toString();
                String dataYear = etYear.getText().toString();
                int checkedRadioId = rgStars.getCheckedRadioButtonId();
                RadioButton selectedRb = findViewById(checkedRadioId);

                String dataStars = selectedRb.getText().toString();

                DBHelper db = new DBHelper(MainActivity.this);
                db.insertSong(dataTitle, dataSinger, dataYear, dataStars);

                al.addAll(db.getAllSongs());
                aa.notifyDataSetChanged();

                etTitle.setText("");
                etSinger.setText("");
                etYear.setText("");
                rgStars.clearCheck();

                Toast.makeText(MainActivity.this, "Insert successful",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });
    }
}