package workoutapp.tyler.workoutapplication;

/**
 * Created by tyler_brient on 11/22/16.
 */

public class Workout {

    private String workoutName;

    private String[] exercises;

    private int[] highRMs, medRMs, lowRMs;

    private int setNum;

    public Workout(String Name, String[] exercises, int[] highRMs, int[] medRMs, int[] lowRMs){
        this.workoutName = Name;
        this.exercises = exercises;
        this.highRMs = highRMs;
        this.lowRMs = lowRMs;
        this.medRMs = medRMs;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String[] getExercises() {
        return exercises;
    }

    public int[] getHighRMs() {
        return highRMs;
    }

    public int[] getMedRMs() {
        return medRMs;
    }

    public int[] getLowRMs() {
        return lowRMs;
    }

    public int getSetNum() {
        return setNum;
    }

}
