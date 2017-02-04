package workoutapp.tyler.workoutapplication;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tyler on 11/30/2016.
 */

public class ShareRVAdapter extends RecyclerView.Adapter<ShareRVAdapter.ExerciseViewHolder> {

    ArrayList<Exercise> exercises;

    @NonNull
    private OnItemCheckListener onItemCheckListener;

    interface OnItemCheckListener {
        void onItemCheck(Exercise item);
        void onItemUncheck(Exercise item);
    }


    public ShareRVAdapter(ArrayList<Exercise> exercises, @NonNull OnItemCheckListener onItemCheckListener){
        this.exercises = exercises;
        this.onItemCheckListener = onItemCheckListener;
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.share_check_view, viewGroup, false);
        ExerciseViewHolder evh = new ExerciseViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder exerciseViewHolder, int i) {
        String ExerciseName = exercises.get(i).getExerciseName();
        String RepNum = "";
        String setNum = "";
        if (exercises.get(i).getNumberOfSets() != -1) {
            setNum = Integer.toString(exercises.get(i).getNumberOfSets()) + " Sets";
            int[] repRange = exercises.get(i).getRepRange();
            if (repRange[0] == repRange[1]) {
                RepNum = String.valueOf(repRange[0]) + " Reps";
            } else {
                RepNum = repRange[0] + "-" + repRange[1] + " Reps";
            }
        }
        String weightNum = "";
        String dateString = "";
        if (exercises.get(i).getCompletedWeights().size() - 1 > -1) {
            weightNum = String.valueOf((int) exercises.get(i).getCompletedWeights().get(exercises.get(i).getCompletedWeights().size() - 1)[0]) + " lbs";
            Date date = (Date) (exercises.get(i).getCompletedWeights().get(exercises.get(i).getCompletedWeights().size() - 1)[2]);
            dateString = new SimpleDateFormat("MM/dd").format(date);
        }
        final int index = i;
        exerciseViewHolder.mainCheckBox.setText(ExerciseName);
        exerciseViewHolder.mainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    onItemCheckListener.onItemCheck(exercises.get(index));
                } else {
                    onItemCheckListener.onItemUncheck(exercises.get(index));
                }
            }
        });
        exerciseViewHolder.repsTextView.setText(RepNum);
        exerciseViewHolder.setsTextView.setText(setNum);
        exerciseViewHolder.weightTextView.setText(weightNum);
        exerciseViewHolder.dateTextView.setText(dateString);
    }




    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mainCheckBox;
        public TextView setsTextView;
        public TextView repsTextView;
        public TextView dateTextView;
        public TextView weightTextView;

        ExerciseViewHolder(View mainView) {
            super(mainView);
            mainCheckBox = (CheckBox) mainView.findViewById(R.id.exerciseCheckBox);
            setsTextView = (TextView) mainView.findViewById(R.id.setsText);
            repsTextView = (TextView) mainView.findViewById(R.id.repsText);
            dateTextView = (TextView) mainView.findViewById(R.id.dateText);
            weightTextView = (TextView) mainView.findViewById(R.id.weightText);

        }

    }
}
