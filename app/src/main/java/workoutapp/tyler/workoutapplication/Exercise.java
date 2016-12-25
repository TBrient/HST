package workoutapp.tyler.workoutapplication;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class Exercise implements Serializable{

    private String exerciseName;

    private int numberOfSets, numberOfReps;

    private ArrayList<Object[]> completedWeights = new ArrayList<>(); //First Entry: Weight, Second Entry: Date

    private ArrayList<Object[]> incompleteWeights = new ArrayList<>(); //First entry: Weight, Second Entry: Set count, Third Entry: Rep Count, Fourth Entry: Date


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

    public void addSet(int weight, int sets, int reps){ //TODO: Add a date insert option
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        if (sets < numberOfSets || reps < numberOfReps) {
            incompleteWeights.add(new Object[]{weight, sets, reps, date});
        } else {
            completedWeights.add(new Object[]{weight, date});
        }
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

    public ArrayList<Object[]> getCompletedWeights() {
        return completedWeights;
    }

    public ArrayList<Object[]> getIncompleteWeights() {
        return incompleteWeights;
    }
}
