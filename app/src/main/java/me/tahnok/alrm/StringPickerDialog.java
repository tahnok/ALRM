package me.tahnok.alrm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class StringPickerDialog extends DialogFragment {

    public static String TAG = StringPickerDialog.class.getSimpleName();

    private EditText numberEditText;
    private Delegate delegate;
    private String oldValue;
    @StringRes private int titleId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_string_picker, null);
        numberEditText = (EditText) view.findViewById(R.id.string_edit_text);
        numberEditText.setText(oldValue);
        builder.setView(view);
        builder.setMessage(titleId)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    delegate.onValueSelected(numberEditText.getText().toString());
                    getDialog().cancel();
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    getDialog().cancel();
                }
            });
        return builder.create();
    }

    public void show(@StringRes int titleId, String oldValue, FragmentManager fragmentManager, Delegate delegate) {
        this.titleId = titleId;
        this.oldValue = oldValue;
        this.delegate = delegate;
        show(fragmentManager, TAG);
    }


    public interface Delegate {
        void onValueSelected(String string);
    }
}
