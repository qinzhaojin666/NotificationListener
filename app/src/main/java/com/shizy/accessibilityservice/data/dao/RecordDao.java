package com.shizy.accessibilityservice.data.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.shizy.accessibilityservice.data.entity.Record;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecord(Record record);

    @Delete
    void deleteRecord(Record record);

    @Query("DELETE FROM record")
    void deleteAll();

    @Query("SELECT * FROM record ORDER BY id COLLATE NOCASE ASC")
    DataSource.Factory<Integer, Record> allRecords();

}
