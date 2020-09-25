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
import com.daemonw.deviceinfo.model.ListInfo;

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
        if (viewType == ItemInfo.TYPE_ITEM_INFO) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_info, parent, false);
            return new InfoHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            return new InfoHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        InfoHolder infoHolder = (InfoHolder) holder;
        ItemInfo info = infoList.get(position);
        if (info == null) {
            return;
        }
        int type = info.getItemType();
        switch (type) {
            case ItemInfo.TYPE_ITEM_HEADER:
                infoHolder.tvKey.setText(info.getKey());
                break;
            case ItemInfo.TYPE_ITEM_INFO:
                infoHolder.tvKey.setText(info.getKey());
                infoHolder.tvVal.setText(info.getVal());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ItemInfo info = infoList.get(position);
        return info.getItemType();
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
