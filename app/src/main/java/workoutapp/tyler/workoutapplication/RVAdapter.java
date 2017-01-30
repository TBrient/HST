package workoutapp.tyler.workoutapplication;

import android.media.Image;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tyler on 11/30/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ExerciseViewHolder> {

    ArrayList<Exercise> exercises;

    public RVAdapter(ArrayList<Exercise> exercises){
        this.exercises = exercises;
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        ExerciseViewHolder evh = new ExerciseViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.exerciseName.setText(exercises.get(i).getExerciseName());
        final int cardNum = i;
        exerciseViewHolder.sets.setText(Integer.toString(exercises.get(i).getNumberOfSets()) + " Sets");
        String repString;
        int[] repRange = exercises.get(i).getRepRange();
        if (repRange[0] == repRange[1]) {
            exerciseViewHolder.reps.setText(String.valueOf(repRange[0]) + "Reps");
        } else {
            exerciseViewHolder.reps.setText(repRange[0] + "-" + repRange[1] + "Reps");
        }
        exerciseViewHolder.weight.setText(Integer.toString((int) exercises.get(i).getCompletedWeights().get(exercises.get(i).getCompletedWeights().size() - 1)[0]) + " lbs");
        final Date date = (Date)(exercises.get(i).getCompletedWeights().get(exercises.get(i).getCompletedWeights().size()-1)[1]);
        exerciseViewHolder.date.setText(new SimpleDateFormat("MM/dd").format(date));
        checkDate(date, exerciseViewHolder);
        exerciseViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardTabView cardTabView = new CardTabView();
                MainActivity activity = (MainActivity) view.getContext();
                activity.getUserData().setCardViewExercisePressed(exercises.get(cardNum));

                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top);
                fragmentTransaction.replace(R.id.fragmentContainer, cardTabView, "toCardView");
                fragmentTransaction.addToBackStack("toCardView");
                fragmentTransaction.commit();
                checkDate(date, exerciseViewHolder);
            }
        });
    }

    private void checkDate(Date date, ExerciseViewHolder exerciseViewHolder){
        if (!DateUtils.isToday(date.getTime())) {
            exerciseViewHolder.highlightBar.setVisibility(View.VISIBLE);
        } else {
            exerciseViewHolder.highlightBar.setVisibility(View.INVISIBLE);
        }
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView exerciseName;
        public TextView sets;
        public TextView reps;
        public TextView weight;
        public TextView date;
        public ImageView highlightBar;

        ExerciseViewHolder(View cardView) {
            super(cardView);
            cv = (CardView) cardView.findViewById(R.id.cv);
            exerciseName = (TextView) cardView.findViewById(R.id.exercise_name);
            sets = (TextView) cardView.findViewById(R.id.sets);
            reps = (TextView) cardView.findViewById(R.id.reps);
            weight = (TextView) cardView.findViewById(R.id.previousWeight);
            date = (TextView) cardView.findViewById(R.id.previousDate);
            highlightBar = (ImageView) cardView.findViewById(R.id.highlightBar);
        }

    }
}
