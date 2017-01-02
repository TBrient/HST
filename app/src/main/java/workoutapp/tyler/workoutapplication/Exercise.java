package workoutapp.tyler.workoutapplication;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    public void addSet(int weight, int sets, int reps, Calendar cal){ //TODO: Add a date insert option
        Date date = cal.getTime();
        if (sets < numberOfSets || reps < numberOfReps) {
            incompleteWeights.add(new Object[]{weight, sets, reps, date});
            Collections.sort(incompleteWeights, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] objects, Object[] t1) {
                    Date d1 = (Date)objects[1];
                    Date d2 = (Date)t1[1];
                    if (d1.getTime() < d2.getTime()) {
                        return -1;
                    } else if (d1.getTime() == d2.getTime()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else {
            completedWeights.add(new Object[]{weight, date});
            Collections.sort(completedWeights, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] objects, Object[] t1) {
                    Date d1 = (Date)objects[1];
                    Date d2 = (Date)t1[1];
                    if (d1.getTime() < d2.getTime()) {
                        return -1;
                    } else if (d1.getTime() == d2.getTime()) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
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
