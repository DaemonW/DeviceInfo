package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private OnItemClickListener listener;

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
                infoHolder.tvVal.setText(info.getVal());
                break;
            case ItemInfo.TYPE_ITEM_INFO:
                infoHolder.tvKey.setText(info.getKey());
                infoHolder.tvVal.setText(info.getVal());
                break;
            default:
                break;
        }
        if (info.getItemType() == ItemInfo.TYPE_ITEM_HEADER) {
            return;
        }
        infoHolder.root.setOnClickListener((v -> {
            if (listener != null) {
                listener.onItemClicked(info, infoHolder, position);
            }
        }));
        infoHolder.root.setOnLongClickListener((v) -> {
            if (listener != null) {
                listener.onItemLongClicked(info, infoHolder, position);
            }
            return true;
        });
//        boolean isChecked = info.isChecked();
//        if (isChecked) {
//            infoHolder.checkBox.setImageResource(R.mipmap.edit_unlock);
//        } else {
//            infoHolder.checkBox.setImageResource(R.mipmap.edit_lock);
//        }
//        infoHolder.checkBox.setOnClickListener((v) -> {
//            boolean checked = !isChecked;
//            info.setChecked(checked);
//            if (checked) {
//                infoHolder.checkBox.setImageResource(R.mipmap.edit_unlock);
//            } else {
//                infoHolder.checkBox.setImageResource(R.mipmap.edit_lock);
//            }
//            notifyItemChanged(position);
//        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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

    static class InfoHolder extends RecyclerView.ViewHolder {
        private View root;
        private TextView tvKey;
        private TextView tvVal;
        private ImageView checkBox;

        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);;
            tvKey = itemView.findViewById(R.id.title);
            tvVal = itemView.findViewById(R.id.value);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    interface OnItemClickListener {
        void onItemClicked(ItemInfo info, InfoHolder holder, int position);
        void onItemLongClicked(ItemInfo info, InfoHolder holder, int position);
    }
}
