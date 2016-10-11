package com.example.sushant.simpletodo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by sushant on 10/10/16.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private Context context;
    private ArrayList<String> mDataSet;
    static final int TIME_OUT = 5000;
    private AlertDialog mAlertDialog;
    private Handler mHandler;
    static final int MSG_DISMISS_DIALOG = 0;
    private int section_no;
    MainActivity.PlaceholderFragment placeholderFragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;


        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.text_item_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }


    public Adapter(ArrayList<String> dataSet, Context con, int sec ) {
        mDataSet = dataSet;
        context = con;
        section_no=sec;

    }
    public View v;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
         v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(mDataSet.get(position));

        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSwap = mDataSet.get(position);

            }
        });

        viewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.promt_delete, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptView);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton(R.string.del, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mDataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}