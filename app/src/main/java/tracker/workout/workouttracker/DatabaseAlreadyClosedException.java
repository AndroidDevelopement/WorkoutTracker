package tracker.workout.workouttracker;

public class DatabaseAlreadyClosedException extends Exception {

	public DatabaseAlreadyClosedException(String message) {
		super(message);
	}

	public DatabaseAlreadyClosedException() {
		super();
	}

}
