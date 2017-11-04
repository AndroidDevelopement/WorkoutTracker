package tracker.workout.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class AddExerciseActivity extends AppCompatActivity {

    private ListView gridView;
	public DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exercise);
        gridView = (ListView) findViewById(R.id.categoryListView);
		dbHelper = new DatabaseHelper(this);
		String[] categories = this.getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.list_item, categories);
        gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String categoryName = parent.getItemAtPosition(position).toString();
				String[] categoryExercises = dbHelper.getExercisesForCategory(categoryName);
				Bundle extras = getIntent().getExtras();
				ArrayList<String> workoutExercises = (ArrayList<String>) extras.get("workoutExercises");
				Intent intent = new Intent(AddExerciseActivity.this, ExercisesForCategory.class);
				intent.putExtra("exercises", categoryExercises);
				intent.putExtra("workoutExercises", workoutExercises);
				startActivity(intent);
			}
		});
    }
}