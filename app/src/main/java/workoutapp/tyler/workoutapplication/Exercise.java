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

    private int numberOfSets;
    private int[] numberOfReps;

    private ArrayList<Object[]> completedWeights = new ArrayList<>(); //First Entry: Weight, Second Entry: Rep Count, Third Entry: Date

    private ArrayList<Object[]> incompleteWeights = new ArrayList<>(); //First entry: Weight, Second Entry: Set count, Third Entry: Rep Count, Fourth Entry: Date


    public Exercise(String Name, int setNum, String repNum){
        this.exerciseName = Name;
        this.numberOfSets = setNum;
        int firstRep;
        int secondRep;
        if (repNum.contains("-")) {
            firstRep = Integer.valueOf(repNum.split("-")[0]);
            secondRep = Integer.valueOf(repNum.split("-")[1]);
            if (firstRep > secondRep) {
                int place = firstRep;
                firstRep = secondRep;
                secondRep = place;
            }
        } else {
            firstRep = Integer.valueOf(repNum);
            secondRep = Integer.valueOf(repNum);
        }
        numberOfReps = new int[]{firstRep, secondRep};
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setNumberOfSets(int numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public void setNumberOfReps(String repNum) {
        int secondRep;
        int firstRep;
        if (repNum.contains("-")) {
            firstRep = Integer.valueOf(repNum.split("-")[0]);
            secondRep = Integer.valueOf(repNum.split("-")[1]);
            if (firstRep > secondRep) {
                int place = firstRep;
                firstRep = secondRep;
                secondRep = place;
            }
        } else {
            firstRep = Integer.valueOf(repNum);
            secondRep = Integer.valueOf(repNum);
        }
        numberOfReps = new int[]{firstRep, secondRep};
    }

    public void addSet(int weight, int sets, int reps, Calendar cal){
        Date date = cal.getTime();
        if (sets < numberOfSets || reps < numberOfReps[0] || reps > numberOfReps[1]) {
            incompleteWeights.add(new Object[]{weight, sets, reps, date});
            Collections.sort(incompleteWeights, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] objects, Object[] t1) {
                    int d1 = (int)objects[0];
                    int d2 = (int)t1[0];
                    if (d1 < d2) {
                        return -1;
                    } else if (d1 == d2) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            });
        } else {
            completedWeights.add(new Object[]{weight, reps,date});
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

    public int[] getRepRange () {
        return numberOfReps;
    }

    public ArrayList<Object[]> getCompletedWeights() {
        return completedWeights;
    }

    public ArrayList<Object[]> getIncompleteWeights() {
        return incompleteWeights;
    }
}
