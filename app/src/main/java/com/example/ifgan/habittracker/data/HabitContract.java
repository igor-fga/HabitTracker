package com.example.ifgan.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by ifgan on 26/08/2017.
 */

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract(){}


    /**
     * Inner class that defines constant values for the habits database table.
     * Each entry in the table represents a single habit.
     */
    public static class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_USER_ID = "userid";
        public final static String COLUMN_HABIT = "habit";

    }
}
