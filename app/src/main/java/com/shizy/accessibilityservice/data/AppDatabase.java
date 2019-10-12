package com.shizy.accessibilityservice.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.shizy.accessibilityservice.data.converter.DateConverter;
import com.shizy.accessibilityservice.data.dao.RecordDao;
import com.shizy.accessibilityservice.data.entity.Record;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Record.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app_db";

    private static volatile AppDatabase INSTANCE;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }

                                @Override
                                public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                                    super.onDestructiveMigration(db);
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract RecordDao recordDao();

    public Executor getExecutor() {
        return mExecutor;
    }
}
