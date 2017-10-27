package tracker.workout.workouttracker;

public class DatabaseAlreadyOpenException extends Exception {

	public DatabaseAlreadyOpenException(String message) {
		super(message);
	}

	public DatabaseAlreadyOpenException() {
		super();
	}

}
