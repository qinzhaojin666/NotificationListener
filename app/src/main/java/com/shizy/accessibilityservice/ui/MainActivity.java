package com.shizy.accessibilityservice.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shizy.accessibilityservice.R;
import com.shizy.accessibilityservice.Utils;
import com.shizy.accessibilityservice.data.entity.Record;
import com.shizy.accessibilityservice.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final int RC_PERMISSION = 0x10;

    private MainViewModel mViewModel;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != RC_PERMISSION) {
            return;
        }
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MainAdapter adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = new ViewModelProvider(this, new MainViewModel.Factory(getApplication()))
                .get(MainViewModel.class);
        mViewModel.getRecords().observe(this, new Observer<PagedList<Record>>() {
            @Override
            public void onChanged(PagedList<Record> records) {
                adapter.submitList(records);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    RC_PERMISSION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(Utils.isNotificationListenerEnabled(this) ? R.string.service_enabled : R.string.service_disabled);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Utils.openNotificationListenSettings(this);
                break;
            case R.id.clear:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.confirm_clear_all)
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.clear, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mViewModel.deleteAll();
                            }
                        })
                        .show();
                break;
        }
        return true;
    }
}
