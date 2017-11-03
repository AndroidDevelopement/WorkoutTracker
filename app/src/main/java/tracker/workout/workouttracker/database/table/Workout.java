package tracker.workout.workouttracker.database.table;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Workout implements Serializable {

    private int id;
    private String name;
    private ArrayList<Exercise> exercises;

    public Workout(int id, String name, ArrayList<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Exercise> getExercises() { return exercises; }

    @Override
    public String toString() {
        return name;
    }
}