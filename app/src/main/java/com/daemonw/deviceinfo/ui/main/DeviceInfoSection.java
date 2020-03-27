package com.daemonw.deviceinfo.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daemonw.deviceinfo.R;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class DeviceInfoSection extends Section {
    private List<String> section = new ArrayList<>();

    public DeviceInfoSection() {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(android.R.layout.simple_list_item_1)
                //.headerResourceId(R.layout.section_header)
                .build());
    }


    @Override
    public int getContentItemsTotal() {
        return section.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new DeviceInfoHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        DeviceInfoHolder holder = (DeviceInfoHolder) viewHolder;

    }

    class DeviceInfoHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvValue;

        public DeviceInfoHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvValue = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
