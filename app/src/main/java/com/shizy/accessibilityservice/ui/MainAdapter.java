package com.shizy.accessibilityservice.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shizy.accessibilityservice.R;
import com.shizy.accessibilityservice.data.entity.Record;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainAdapter extends PagedListAdapter<Record, MainAdapter.RecordViewHolder> {

    private static DiffUtil.ItemCallback<Record> callback = new DiffUtil.ItemCallback<Record>() {

        @Override
        public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
            return oldItem.equals(newItem);
        }
    };

    public MainAdapter() {
        super(callback);
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        if (record != null) {
            holder.bindData(record);
        }
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);

        private TextView titleView;
        private TextView contentView;
        private TextView dateView;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            contentView = itemView.findViewById(R.id.content);
            dateView = itemView.findViewById(R.id.date);
        }

        void bindData(@NonNull Record record) {
            titleView.setText(record.getTitle());
            contentView.setText(record.getText());
            dateView.setText(FORMAT.format(record.getCreateAt()));
        }
    }

}
