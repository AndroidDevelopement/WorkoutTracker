/*
 *  Workout - Custom data type for a workout. Made up of an id,
 *  a name and an array of exercises.
 */

package tracker.workout.workouttracker.dataContainers;

import java.io.Serializable;

public class Workout implements Serializable {

	private long id;
	private String name;
	private WorkoutExercise[] exercises;

	// Constructor for Workout
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