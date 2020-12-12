package com.daemonw.deviceinfo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ProgressDialog extends Dialog {
    private TextView mDlgTitle;
    private ProgressBar mDlgProgress;
    private TextView mDlgMessage;


    private ProgressDialog(Context context) {
        super(context);
    }

    public void setProgress(int progress) {
        if (isShowing()) {
            if (mDlgProgress != null) {
                mDlgProgress.setProgress(progress);
            }
        }
    }

    public void setMessage(String message) {
        if (isShowing()) {
            if (mDlgMessage != null) {
                mDlgMessage.setVisibility(View.VISIBLE);
                mDlgMessage.setText(message);
            }
        }
    }


    public static class Builder {
        private Context context;
        private View mLayout;
        private ProgressDialog mDialog;
        private String message;
        private String title;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(@NonNull String message) {
            this.message = message;
            return this;
        }

        public ProgressDialog create() {
            mDialog = new ProgressDialog(context);
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mLayout = inflater.inflate(R.layout.dialog_progress, null, false);
            mDialog.addContentView(mLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mDialog.mDlgTitle = mLayout.findViewById(R.id.dlg_title);
            if (title != null) {
                mDialog.mDlgTitle.setVisibility(View.VISIBLE);
                mDialog.mDlgTitle.setText(title);
            }
            mDialog.mDlgProgress = mLayout.findViewById(R.id.dlg_progress);
            mDialog.mDlgProgress.setMax(100);
            mDialog.mDlgMessage = mLayout.findViewById(R.id.dlg_message);
            if (message != null) {
                mDialog.mDlgMessage.setVisibility(View.VISIBLE);
                mDialog.mDlgMessage.setText(message);
            }

            //mDialog.setContentView(mLayout);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            return mDialog;
        }
    }
}