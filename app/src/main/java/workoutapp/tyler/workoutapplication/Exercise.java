package workoutapp.tyler.workoutapplication;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class Exercise {

    private String exerciseName;

    private int numberOfSets, numberOfReps;

    private ArrayList<int[]> completedWeights = new ArrayList<>(); //First Entry: Weight, Second Entry: Date

    private ArrayList<int[]> incompleteWeights = new ArrayList<>(); //First entry: Weight, Second Entry: Set count, Third Entry: Rep Count, Fourth Entry: Date


    public Exercise(String Name, int setNum, int repNum){
        this.exerciseName = Name;
        this.numberOfReps = repNum;
        this.numberOfSets = setNum;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public void setNumberOfReps(int numberOfReps) {
        this.numberOfReps = numberOfReps;
    }

    public void addCompleteSet(int weight){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Date date = c.getTime();
        int dateInt = Integer.valueOf(simpleDateFormat.format(date));

        System.out.println(dateInt);

        completedWeights.add(new int[]{});
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

    public int getNumberOfReps() {
        return numberOfReps;
    }

    public ArrayList<int[]> getCompletedWeights() {
        return completedWeights;
    }

    public ArrayList<int[]> getIncompleteWeights() {
        return incompleteWeights;
    }
}
