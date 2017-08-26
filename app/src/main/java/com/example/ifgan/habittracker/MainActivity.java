package com.example.ifgan.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifgan.habittracker.data.HabitContract;
import com.example.ifgan.habittracker.data.HabitDbHelper;
import com.example.ifgan.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btnIns);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;

                if (insertHabit())
                {
                    msg = "Cadastrado";
                }
                else
                {
                    msg = "UserID ou Habito em branco";
                }

                Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
                toast.show();
                displayDatabaseHabit();
            }
        });

        dbHelper = new HabitDbHelper(this);
    }

    private boolean insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText edUser = (EditText) findViewById(R.id.editUser);
        EditText edHabit = (EditText) findViewById(R.id.editHabit);

        if (edUser.getText().toString().trim().length() == 0 || edHabit.getText().toString().trim().length() == 0)
            return false;

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_USER_ID, edUser.getText().toString().trim());
        values.put(HabitEntry.COLUMN_HABIT, edHabit.getText().toString().trim());

        long newRowid = db.insert(HabitEntry.TABLE_NAME, null, values);

        return true;
    }

    private void displayDatabaseHabit()
    {
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] projection = {
                HabitEntry.COLUMN_USER_ID,
                HabitEntry.COLUMN_HABIT
        };

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try{
            displayView.setText("The habits tables contains " + cursor.getCount() + "habits. \n\n");
            displayView.append(HabitEntry.COLUMN_USER_ID + " - " +
                HabitEntry.COLUMN_HABIT + "\n"
            );

            int useridColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_USER_ID);
            int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT);

            while (cursor.moveToNext()){
                int userID = cursor.getInt(useridColumnIndex);
                String habit = cursor.getString(habitColumnIndex);

                displayView.append(("\n" + userID + " - " +
                    habit)
                );
            }
        }
        finally {
            cursor.close();
        }
    }
}
