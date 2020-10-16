package com.daemonw.deviceinfo.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daemonw.deviceinfo.R;
import com.daemonw.deviceinfo.model.IdentifierInfo;
import com.daemonw.deviceinfo.model.ItemInfo;
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
        adapter.setOnItemClickListener(new InfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ItemInfo info, InfoAdapter.InfoHolder holder, int position) {

            }

            @Override
            public void onItemLongClicked(ItemInfo item, InfoAdapter.InfoHolder holder, int position) {
                if (!(viewModel instanceof IdentifierViewModel)) {
                    return;
                }
                final IdentifierViewModel iv = (IdentifierViewModel) viewModel;
                IdentifierInfo info = iv.getValue();
                Context c = getActivity();
                final EditText et = new EditText(c);
                et.setText(item.getVal());
                AlertDialog dialog = new AlertDialog.Builder(c).setTitle("更改" + item.getKey())
                        .setIcon(R.mipmap.edit_unlock)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String tag = item.getTag();
                                if (tag != null && !tag.isEmpty()) {
                                    try {
                                        String val = et.getText().toString();
                                        info.getClass().getField(tag).set(info, val);
                                        iv.setValue(info);
                                        //adapter.notifyDataSetChanged();
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).setNegativeButton("取消", null).create();
                dialog.show();
            }
        });
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
