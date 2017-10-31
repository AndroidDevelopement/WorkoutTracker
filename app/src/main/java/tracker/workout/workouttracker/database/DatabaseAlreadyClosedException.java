package tracker.workout.workouttracker.database;

public class DatabaseAlreadyClosedException extends Exception {

	public DatabaseAlreadyClosedException(String message) {
		super(message);
	}

}