package com.devices1.com.myseries.addSeries;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.devices1.com.myseries.R;

public class AskSeriesConfirmationDialog extends DialogFragment{

    public static final String TITLE = "title";
    public static final String SUMMARY = "summary";
    public static final String AIRED = "aired";
    public IConfirmedListener confirmedListener;

    public TextView confirmTitle;
    public TextView confirmDate;
    public TextView confirmSummary;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.ask_confirmation_layout, null);

        confirmTitle = (TextView) view.findViewById(R.id.confirmTitle);
        confirmDate = (TextView) view.findViewById(R.id.confirmDate);
        confirmSummary = (TextView) view.findViewById(R.id.confirmSummary);
        Bundle arguments = getArguments();
        String title = arguments.getString(TITLE);
        String aired = arguments.getString(AIRED);
        String summary = arguments.getString(SUMMARY);

        confirmTitle.setText(title);
        confirmDate.setText(aired);
        confirmSummary.setText(summary);


        builder.setView(view);
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int button){
                        if(confirmedListener != null){
                            confirmedListener.onConfirmed();
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
