package com.example.jjy19.roomdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("select * from task where uid in (:taskIDs)")
    List<Task> loadAllByIds(int[] taskIDs);

    @Insert
    void insertAll(Task... task);

    @Update
    void update(Task... task);

    @Delete
    void delete(Task... task);
}
