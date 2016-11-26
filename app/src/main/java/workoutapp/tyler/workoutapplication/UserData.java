package workoutapp.tyler.workoutapplication;

import java.util.ArrayList;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class UserData {

    private ArrayList<Workout> workouts;
    private int workoutNum; //1-18 Number of Days worked out in that session so far

    public UserData(int workoutNumber){
        workouts = new ArrayList<>();
        workoutNum = workoutNumber;
    }

    public ArrayList<Workout> getWorkouts() {
        return workouts;
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
    }

    public int getWorkoutNum() {
        return workoutNum;
    }

    public void setWorkoutNum(int workoutNum) {
        this.workoutNum = workoutNum;
    }

    public void setMainWorkout(Workout workout) {
        this.workouts.add(0, workout);
    }

    public Workout getWorkout(int index) {
        return workouts.get(index);
    }
}
