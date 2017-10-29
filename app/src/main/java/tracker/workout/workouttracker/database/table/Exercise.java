package tracker.workout.workouttracker.database.table;

public class Exercise {

	private int id;
	private String name;

	public Exercise(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}

}