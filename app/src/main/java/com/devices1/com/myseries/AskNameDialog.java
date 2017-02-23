package com.devices1.com.myseries;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AskNameDialog extends DialogFragment{


    public EditText seriesNameEdit;
    public INameListener nameListener;

    public void setNameListener(INameListener nameListener) {
        this.nameListener = nameListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.ask_name_layout, null);
        seriesNameEdit = (EditText) view.findViewById(R.id.etSeriesName);

        builder.setView(view);
        builder.setPositiveButton("Search",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int button){
                        if(nameListener != null){
                            nameListener.onNameChosen(seriesNameEdit.getText().toString());
                        }
                    }});
        builder.setNegativeButton("Forget it", null);



        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        nameListener = (INameListener)context;
    }

    public interface INameListener {

        void onNameChosen(String name);

    }

}
