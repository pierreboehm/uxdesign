package org.pb.android.uxdesign.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.pb.android.uxdesign.R;

@SuppressLint("NonConstantResourceId")
@EViewGroup(R.layout.view_content_dialog_warning)
public class WarningDialog extends LinearLayout {

    @ViewById(R.id.dialogContentContainer)
    ViewGroup dialogContentContainer;

    @ViewById(R.id.tvDialogSubText)
    TextView tvDialogSubText;

    @ViewById(R.id.tvDialogTitleId)
    TextView tvDialogTitleId;

    @ViewById(R.id.tvDialogTitle)
    TextView tvDialogTitle;

    private Dialog dialog;

    public WarningDialog(Context context) {
        super(context);
    }

    public void show() {
        if (dialog == null) {

            dialog = new AlertDialog.Builder(getContext()).setView(this).create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            final Window window = dialog.getWindow();
            if (window != null) {
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        // without onShowListener this line will crash on Android 6 devices
                        window.getDecorView().setBackgroundResource(android.R.color.transparent);
                    }
                });
            }

            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dismiss();
                }
            });
        }

        dialog.show();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static class Builder {
        private WarningDialog warningDialog;

        public Builder(Context context) {
            warningDialog = WarningDialog_.build(context);
        }

        public WarningDialog build() {
            return warningDialog;
        }

        public WarningDialog.Builder setContent(View content) {
            warningDialog.dialogContentContainer.removeAllViews();
            warningDialog.dialogContentContainer.addView(content);
            return this;
        }

        public WarningDialog.Builder setTitle(String titleText) {
            warningDialog.tvDialogTitle.setText(titleText);
            return this;
        }

        public WarningDialog.Builder setSubText(String subText) {
            warningDialog.tvDialogSubText.setText(subText);
            return this;
        }
    }
}
