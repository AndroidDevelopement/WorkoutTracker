package tracker.workout.workouttracker.dataContainers;

import java.io.Serializable;

public class Workout implements Serializable {

	private long id;
	private String name;
	private WorkoutExercise[] exercises;

	public Workout(long id, String name, WorkoutExercise[] exercises) {
		this.id = id;
		this.name = name;
		this.exercises = exercises;
	}

	public long getId() {
		return id;
	}

	public WorkoutExercise[] getExercises() {
		return exercises;
	}

	@Override
	public String toString() {
		return name;
	}

}