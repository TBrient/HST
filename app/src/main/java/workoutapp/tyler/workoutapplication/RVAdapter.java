package workoutapp.tyler.workoutapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        if (exercises.get(i).getNumberOfSets() < 0 || exercises.get(i).getNumberOfReps() < 0) {
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
        } else {
            final int cardNum = i;
            exerciseViewHolder.sets.setText(Integer.toString(exercises.get(i).getNumberOfSets()) + " Sets");
            exerciseViewHolder.reps.setText(Integer.toString(exercises.get(i).getNumberOfReps()) + " Reps");
            exerciseViewHolder.weight.setText(Integer.toString(exercises.get(i).getCompletedWeights().get(exercises.get(i).getCompletedWeights().size()-1)[0]) + " lbs");
            exerciseViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CardTabView cardTabView = new CardTabView();
                    MainActivity activity = (MainActivity) view.getContext();
                    activity.setExercisePressed(exercises.get(cardNum));

                    FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_top);
                    fragmentTransaction.replace(R.id.fragmentContainer, cardTabView);
                    fragmentTransaction.commit();
                }
            });
        }
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView exerciseName;
        public TextView sets;
        public TextView reps;
        public TextView weight;

        ExerciseViewHolder(View cardView) {
            super(cardView);
            cv = (CardView) cardView.findViewById(R.id.cv);
            exerciseName = (TextView) cardView.findViewById(R.id.exercise_name);
            sets = (TextView) cardView.findViewById(R.id.sets);
            reps = (TextView) cardView.findViewById(R.id.reps);
            weight = (TextView) cardView.findViewById(R.id.previousWeight);
        }

    }
}
