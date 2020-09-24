package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daemonw.deviceinfo.R;
import com.daemonw.deviceinfo.model.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter {
    private List<ItemInfo> infoList;
    private Context context;

    public InfoAdapter(Context context, List<ItemInfo> data) {
        this.context = context;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.infoList = data;
    }

    public void setData(List<ItemInfo> data) {
        if (data != null) {
            this.infoList.clear();
            this.infoList.addAll(data);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_info, parent, false);
        return new InfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InfoHolder infoHolder = (InfoHolder) holder;
        ItemInfo info = infoList.get(position);
        if (info != null) {
            infoHolder.tvKey.setText(info.getKey());
            infoHolder.tvVal.setText(info.getVal());
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class InfoHolder extends RecyclerView.ViewHolder {
        private TextView tvKey;
        private TextView tvVal;

        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(R.id.title);
            tvVal = itemView.findViewById(R.id.value);
        }
    }
}
