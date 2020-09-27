package com.daemonw.deviceinfo.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daemonw.deviceinfo.MainActivity;
import com.daemonw.deviceinfo.R;
import com.daemonw.deviceinfo.model.ListInfo;

public class ListInfoFragment<T> extends Fragment {
    private RecyclerView list;
    private InfoAdapter adapter;
    private BaseViewModel<T> viewModel;

    public static ListInfoFragment newInstance(Bundle bundle) {
        ListInfoFragment fragment = new ListInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setViewModel(BaseViewModel<T> viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_info_fragment, container, false);
        Context context = getContext();
        list = root.findViewById(R.id.list_info);
        adapter = new InfoAdapter(context, null);
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(adapter);
        if (viewModel != null) {
            viewModel.get().observe(getViewLifecycleOwner(), (v) -> {
                if (v == null) {
                    return;
                }
                if (v instanceof ListInfo) {
                    ListInfo info = (ListInfo) v;
                    adapter.setData(info.toList());
                    adapter.notifyDataSetChanged();
                }
            });
        }
        return root;
    }
}
