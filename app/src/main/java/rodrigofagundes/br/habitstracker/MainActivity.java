package rodrigofagundes.br.habitstracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rodrigofagundes.br.habitstracker.data.HabitsContract.HabitsEntry;
import rodrigofagundes.br.habitstracker.data.HabitsDbHelper;

public class MainActivity extends AppCompatActivity {
    private HabitsDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitsDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = null;
        String limit = null;

        Cursor cursor = db.query(HabitsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                orderBy,
                limit);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(HabitsEntry._ID + " - " +
                    HabitsEntry.COLUMN_HABIT_NAME + " - " +
                    HabitsEntry.COLUMN_HABIT_DURATION_MINUTES + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitsEntry.COLUMN_HABIT_NAME);
            int durationMinutesColumnIndex = cursor.getColumnIndex(HabitsEntry.COLUMN_HABIT_DURATION_MINUTES);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDurationMinutes = cursor.getString(durationMinutesColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDurationMinutes));
            }
        } finally {
            cursor.close();
        }
    }

    public void insertDummies(View view) {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Dummy 1
        ContentValues values = new ContentValues();
        values.put(HabitsEntry.COLUMN_HABIT_NAME, "Play PS4");
        values.put(HabitsEntry.COLUMN_HABIT_DURATION_MINUTES, "30");

        long newRowId = db.insert(HabitsEntry.TABLE_NAME, null, values);

        // Dummy 2
        values = new ContentValues();
        values.put(HabitsEntry.COLUMN_HABIT_NAME, "Gardening");
        values.put(HabitsEntry.COLUMN_HABIT_DURATION_MINUTES, "30");

        newRowId = db.insert(HabitsEntry.TABLE_NAME, null, values);

        // Update display
        displayDatabaseInfo();
    }

}
