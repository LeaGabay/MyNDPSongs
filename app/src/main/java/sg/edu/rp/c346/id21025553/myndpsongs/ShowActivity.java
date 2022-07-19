package sg.edu.rp.c346.id21025553.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    ArrayList<Song> al;
    ArrayList<String> dateList;
    //ArrayAdapter<Song> aa;
    ArrayAdapter<String> dateAdapter;

    CustomAdapter aa;

    ListView lv;
    Spinner spinner;
    ToggleButton fiveStarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        spinner = findViewById(R.id.spinnerDate);
        lv = findViewById(R.id.lv);
        fiveStarBtn = findViewById(R.id.btn5stars);

        al = new ArrayList<>();
        dateList = new ArrayList<String>();

        /***aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);***/

        aa = new CustomAdapter(this, R.layout.row, al);

        dateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dateList);

        DBHelper dbh = new DBHelper(ShowActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());

        dateList.clear();
        dateList.addAll(dbh.getDates());
        dateList.add(0,"Filter by year");

        lv.setAdapter(aa);
        spinner.setAdapter(dateAdapter);
        spinner.setSelection(0);

        fiveStarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fiveStarBtn.isChecked()) {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                    spinner.setSelection(0);

                } else {
                    al.clear();
                    al.addAll(dbh.get5StarSongs());
                    lv.setAdapter(aa);
                }
                aa.notifyDataSetChanged();
            }
        });


        // Spinner does not work, still a work in progress to figure it out
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    al.clear();
                    dateList.set(0,"Filter by year");
                    al.addAll(dbh.getAllSongs());
                } else {
                    dateList.set(0,"View all songs");
                    al.clear();
                    int year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    al.addAll(dbh.getAllSongs(year));
                }
                aa.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(ShowActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });
    }

}