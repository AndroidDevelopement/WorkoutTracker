package tracker.workout.workouttracker.database;

public class DatabaseAlreadyOpenException extends Exception {

	public DatabaseAlreadyOpenException(String message) {
		super(message);
	}

}