package workoutapp.tyler.workoutapplication;


import android.content.Context;
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

    private UserData userData = new UserData(5); //TODO: get main workout from external source (use set/getArguments)

    public mainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userData.setMainWorkout(new Workout("a", new String[]{"A", "B", "C", "D", "E"}, new int[]{150, 120, 130, 140, 110}, new int[]{100, 90, 80, 70, 60}, new int[]{50, 40, 30, 20, 10})); //TODO: SET MAIN WORKOUT IN ANOTHER SCREEN.

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

        int[] weights = new int[userData.getWorkout(0).getExercises().length];

        String repCount = "";
        if (userData.getWorkoutNum()/3 <= 2) { //1-6 is 15 Reps
            repCount = "15 Reps, Day ";
            weights = userData.getWorkout(0).getHighRMs();
        } else if (userData.getWorkoutNum()/3 <= 4) { //6-12 is 10 Reps
            repCount = "10 Reps, Day ";
            weights = userData.getWorkout(0).getMedRMs();
        } else if (userData.getWorkoutNum()/3 <= 6) { //12-18 is 5 Reps
            repCount = "5 Reps, Day ";
            weights = userData.getWorkout(0).getLowRMs();
        }
        descriptionText.setText(userData.getWorkout(0).getWorkoutName() + ", " + repCount + userData.getWorkoutNum());

        for (int i = 0; i < userData.getWorkout(0).getExercises().length; i++) {
            LinearLayout hbox = new LinearLayout(getActivity());
            hbox.setLayoutParams(new TableLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT));

            TableRow tempTRow = new TableRow(getActivity());

            TextView exerciseName = new TextView(getActivity());
            exerciseName.setText(userData.getWorkout(0).getExercises()[i] + ":");
            exerciseName.setTextColor(ContextCompat.getColor(getActivity(), R.color.whiteText));
//            exerciseName.setPadding(10, 0, 10, 0);
            exerciseName.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));


            TextView setCount = new TextView(getActivity());
            setCount.setText(userData.getWorkout(0).getSetNum() + " Sets");
            setCount.setTextColor(ContextCompat.getColor(getActivity(), R.color.whiteText));
//            setCount.setPadding(10, 0, 10, 0);
            setCount.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));


            TextView weight = new TextView(getActivity());
            weight.setText(String.valueOf(weights[i]));
            weight.setTextColor(ContextCompat.getColor(getActivity(), R.color.whiteText));
//            weight.setPadding(10, 0, 10, 0);
            weight.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));


            hbox.addView(exerciseName);
            hbox.addView(setCount);
            hbox.addView(weight);

            tempTRow.addView(hbox);
            tableLayout.addView(tempTRow);
        }

        // Inflate the layout for this fragment
        return view;
    }


}
