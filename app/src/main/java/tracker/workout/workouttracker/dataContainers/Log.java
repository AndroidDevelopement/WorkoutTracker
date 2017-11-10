package tracker.workout.workouttracker.dataContainers;

public class Log {

	private long id;
	private String date;
	private Workout workout;

	public Log(long id, String date, Workout workout) {
		this.id = id;
		this.date = date;
		this.workout = workout;
	}

	public long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public Workout getWorkout() {
		return workout;
	}

}