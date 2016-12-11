package workoutapp.tyler.workoutapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(ExerciseViewHolder exerciseViewHolder, int i) {
        exerciseViewHolder.exerciseName.setText(exercises.get(i).getExerciseName());
        exerciseViewHolder.sets.setText(Integer.toString(exercises.get(i).getNumberOfSets()) + " Sets");
        exerciseViewHolder.reps.setText(Integer.toString(exercises.get(i).getNumberOfReps()) + " Reps");
        exerciseViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newExerciseFragment exerciseFragment = new newExerciseFragment();
                MainActivity activity = (MainActivity) view.getContext();
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top);
                fragmentTransaction.replace(R.id.fragmentContainer, exerciseFragment);
                fragmentTransaction.commit();
            }
        });
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView exerciseName;
        public TextView sets;
        public TextView reps;

        ExerciseViewHolder(View cardView) {
            super(cardView);
            cv = (CardView) cardView.findViewById(R.id.cv);
            exerciseName = (TextView) cardView.findViewById(R.id.exercise_name);
            sets = (TextView) cardView.findViewById(R.id.sets);
            reps = (TextView) cardView.findViewById(R.id.reps);
        }

    }
}
