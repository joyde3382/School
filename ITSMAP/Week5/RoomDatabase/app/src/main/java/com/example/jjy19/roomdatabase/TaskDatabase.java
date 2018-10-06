package com.example.jjy19.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {TaskData.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDAO taskDao();
}
