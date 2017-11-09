package tracker.workout.workouttracker.dataContainers;

import java.io.Serializable;

public class WorkoutExercise implements Serializable {

	private long id; // id that is stored in workout_exercise and NOT in workout table.
	private String name;
	private int sets;
	private int reps;

	public WorkoutExercise(long id, String name, int sets, int reps) {
		this.id = id;
		this.name = name;
		this.sets = sets;
		this.reps = reps;
	}

	public long getId() {
		return id;
	}

	public int getSets() {
		return sets;
	}

	public int getReps() {
		return reps;
	}

	@Override
	public String toString() {
		return name;
	}
}