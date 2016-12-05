package workoutapp.tyler.workoutapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tyler on 11/30/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

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
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.exerciseName.setText(exercises.get(i).getExerciseName());
        personViewHolder.sets.setText(exercises.get(i).getNumberOfSets());
        personViewHolder.reps.setImageResource(exercises.get(i).getNumberOfReps());
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView exerciseName;
        public TextView sets;
        public ImageView reps;

        PersonViewHolder(View cardView) {
            super(cardView);
            cv = (CardView) cardView.findViewById(R.id.cv);
            exerciseName = (TextView) cardView.findViewById(R.id.person_name);
            sets = (TextView) cardView.findViewById(R.id.person_age);
            reps = (ImageView) cardView.findViewById(R.id.person_photo);
        }
    }
}
