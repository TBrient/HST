package workoutapp.tyler.workoutapplication;

import java.util.ArrayList;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class UserData {

    private ArrayList<Exercise> exercises;
    private int workoutNum; //1-18 Number of Days worked out in that session so far

    public UserData(int workoutNumber){
        exercises = new ArrayList<>();
        workoutNum = workoutNumber;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addWorkout(Exercise exercise) {
        this.exercises.add(exercise);
    }

    public int getWorkoutNum() {
        return workoutNum;
    }

    public void setWorkoutNum(int workoutNum) {
        this.workoutNum = workoutNum;
    }

    public void setMainWorkout(Exercise exercise) {
        this.exercises.add(0, exercise);
    }

    public Exercise getWorkout(int index) {
        return exercises.get(index);
    }
}
