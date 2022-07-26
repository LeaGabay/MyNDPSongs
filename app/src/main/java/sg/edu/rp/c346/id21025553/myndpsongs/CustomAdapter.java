package sg.edu.rp.c346.id21025553.myndpsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> songList;

    public CustomAdapter(Context context, int resource,
                         ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvSong = rowView.findViewById(R.id.tvSongName);
        TextView tvDate = rowView.findViewById(R.id.tvDate);
        TextView tvStars = rowView.findViewById(R.id.tvStars);
        TextView tvSinger = rowView.findViewById(R.id.tvSinger);

        // Obtain the Song information based on the position
        Song currentSong = songList.get(position);

        // Set values to the TextView to display the corresponding information
        tvSong.setText(currentSong.getTitle());
        tvDate.setText(String.valueOf(currentSong.getYear()));
        tvStars.setText(currentSong.getStarsToString());
        tvSinger.setText(currentSong.getSingers());

        return rowView;
    }

}

