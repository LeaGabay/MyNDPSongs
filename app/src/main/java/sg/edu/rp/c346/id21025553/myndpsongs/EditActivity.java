package sg.edu.rp.c346.id21025553.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    Song data;
    EditText etID, etTitle, etSinger, etYear;
    RadioGroup rgStars;
    Button btnUpdate, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);

        rgStars = findViewById(R.id.rgStars);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        DBHelper dbh = new DBHelper(EditActivity.this);

        etID.setFocusable(false);
        etID.setHint(String.valueOf(data.get_id()));

        etTitle.setText(data.getTitle());
        etYear.setText(String.valueOf(data.getYear()));
        etSinger.setText(data.getSingers());

        for (int x = 0; x < rgStars.getChildCount(); x++){
            RadioButton selectedBtn = (RadioButton)rgStars.getChildAt(x);

            if(Integer.parseInt(selectedBtn.getText().toString()) == data.getStar()){
                rgStars.check(selectedBtn.getId());
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                int checkedRadioId = rgStars.getCheckedRadioButtonId();
                RadioButton selectedRb = findViewById(checkedRadioId);
                data.setStars(Integer.parseInt(selectedRb.getText().toString()));

                dbh.updateSong(data);
                dbh.close();

                Intent i = new Intent(EditActivity.this,
                        MainActivity.class);
                startActivity(i);

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.get_id());

                Intent i = new Intent(EditActivity.this,
                        MainActivity.class);
                startActivity(i);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });
    }
}