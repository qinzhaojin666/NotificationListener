package com.shizy.accessibilityservice.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.shizy.accessibilityservice.App;
import com.shizy.accessibilityservice.data.AppDatabase;
import com.shizy.accessibilityservice.data.entity.Record;

public class MainViewModel extends AndroidViewModel {

    private LiveData<PagedList<Record>> records = null;

    public MainViewModel(@NonNull Application application) {
        super(application);

        DataSource.Factory<Integer, Record> factory = AppDatabase.getDatabase(application).recordDao().allRecords();

        records = new LivePagedListBuilder<>(factory, new PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .build())
                .build();
    }

    public LiveData<PagedList<Record>> getRecords() {
        return records;
    }

    public void deleteAll() {
        ((App)getApplication()).getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getDatabase(getApplication()).recordDao().deleteAll();
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            this.mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel(mApplication);
        }
    }
}
