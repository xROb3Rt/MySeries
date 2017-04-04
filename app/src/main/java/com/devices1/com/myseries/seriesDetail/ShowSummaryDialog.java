package com.devices1.com.myseries.seriesDetail;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.devices1.com.myseries.R;

public class ShowSummaryDialog extends DialogFragment {

    static TextView summary;

    public static void showSummary(Context context, String summaryString){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_summary_layout, null);
        summary = (TextView) view.findViewById(R.id.textShowSummary);
        summary.setText(summaryString);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {}
                }).create();
        builder.show();
    }

}
