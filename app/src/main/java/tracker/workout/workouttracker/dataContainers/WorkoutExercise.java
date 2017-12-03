/*
 *  WorkoutExercise - Custom data type for a workout exercise. Made up
 *  of an id, a name, reps and sets.
 */

package tracker.workout.workouttracker.dataContainers;

import java.io.Serializable;

public class WorkoutExercise implements Serializable {

	private long id; // id that is stored in workout_exercise and NOT in workout table.
	private String name;
	private int sets;
	private int reps;
	private int weight;

	// Constructor for WorkoutExercise
	public WorkoutExercise(long id, String name, int sets, int reps, int weight) {
		this.id = id;
		this.name = name;
		this.sets = sets;
		this.reps = reps;
		this.weight = weight;
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

	public int getWeight() { return weight;  }

	public void setSets(int sets) {
		this.sets = sets;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public void setWeight(int weight) { this.weight = weight; }

	@Override
	public String toString() {
		return name;
	}

	public String toSetsRepsString() { return name + "\n" + sets  + " Sets x " + reps + " Reps x " + weight + " kgs"; }
}