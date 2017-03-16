package com.devices1.com.myseries.addSeries;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.devices1.com.myseries.R;

public class AskSeriesConfirmationDialog extends DialogFragment{

    public IConfirmedListener confirmedListener;
    public TextView confirmTitle;
    public TextView confirmAid;
    public TextView confirmDate;
    public TextView confirmSum;
    public TextView confirmSummary;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.ask_confirmation_layout, null);
        confirmTitle = (TextView) view.findViewById(R.id.confirmTitle);
        confirmAid = (TextView) view.findViewById(R.id.confirmAid);
        confirmDate = (TextView) view.findViewById(R.id.confirmDate);
        confirmSum = (TextView) view.findViewById(R.id.confirmSum);
        confirmSummary = (TextView) view.findViewById(R.id.confirmSummary);

        builder.setView(view);
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int button){
                        if(confirmedListener != null){
                           // confirmedListener.onConfirmed(seriesNameEdit.getText().toString());
                        }
                    }});
        builder.setNegativeButton("Forget it", null);



        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        confirmedListener = (IConfirmedListener)context;
    }

    public interface IConfirmedListener {

        void onConfirmed();

    }

}
