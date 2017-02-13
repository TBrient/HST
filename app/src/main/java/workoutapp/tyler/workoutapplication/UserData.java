package workoutapp.tyler.workoutapplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class UserData {

    private ArrayList<ArrayList<Exercise>> exercises;
    private ArrayList<String> categoryTitles;
    private ArrayList<Object[]> bodyWeight; //weight, nothing, date
    private Exercise cardViewExercisePressed;
    //private int workoutNum; //1-18 Number of Days worked out in that session so far

    public UserData(){
        exercises = new ArrayList<>();
        bodyWeight = new ArrayList<>();
        //workoutNum = workoutNumber;
    }

    public ArrayList<Exercise> getExercises(String category) {
        int index = categoryTitles.indexOf(category);
        return exercises.get(index);
    }

    public void addWorkout(Exercise exercise) {
        int category = 0;
        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).contains(exercise)) {
                category = i;
                break;
            }
        }
        this.exercises.get(category).add(exercise);
    }

//    public int getWorkoutNum() {
//        return workoutNum;
//    }
//
//    public void setWorkoutNum(int workoutNum) {
//        this.workoutNum = workoutNum;
//    }


    public Exercise getExercise(String category, int index) {
        return exercises.get(categoryTitles.indexOf(category)).get(index);
    }

    public Exercise getCardViewExercisePressed() {
        return cardViewExercisePressed;
    }

    public void setCardViewExercisePressed(Exercise cardViewExercisePressed) {
        this.cardViewExercisePressed = cardViewExercisePressed;
    }

    public void setExercises(ArrayList<Exercise> exercises, String category) {
        this.exercises.set(categoryTitles.indexOf(category), exercises);
    }

    public ArrayList<Object[]> getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(ArrayList<Object[]> bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public void addBodyWeight(int weight, Calendar cal) {
        Date date = cal.getTime();
            bodyWeight.add(new Object[]{weight, null, date});
            Collections.sort(bodyWeight, new Comparator<Object[]>() {
                @Override
                public int compare(Object[] objects, Object[] t1) {
                    Date d1 = (Date)objects[2];
                    Date d2 = (Date)t1[2];
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

    public ArrayList[] getData(){
        ArrayList[] returnList = new ArrayList[2];
        returnList[0] = exercises;
        returnList[1] = bodyWeight;
        return returnList;
    }
}
