package workoutapp.tyler.workoutapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class mainFragment extends Fragment {


    public mainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Creates a view
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //Gets Text View
        TextView dateTextView;
        dateTextView = (TextView)view.findViewById(R.id.dateTextView);

        //Sets Text View to correct date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM d", Locale.US);
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        dateTextView.setText(simpleDateFormat.format(date));

        //Gets tableview for workout
        TableLayout tableLayout;
        tableLayout = (TableLayout)view.findViewById(R.id.workoutLayout);

        //Get description text

        TextView descriptionText = (TextView)view.findViewById(R.id.todayDiscText);


        // Inflate the layout for this fragment
        return view;
    }


}
