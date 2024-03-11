package com.developer.qrapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.qrapp.DBHelper;
import com.developer.qrapp.Model.HistoryModel;
import com.developer.qrapp.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryVH> {
    Context context;
    boolean isDeleted;
    ArrayList<HistoryModel> historyModels = new ArrayList<>();
    DBHelper dbHelper;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModels, DBHelper dbHelper) {
        this.context = context;
        this.historyModels = historyModels;
        this.dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public HistoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_history_layout, parent, false);
        return new HistoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryVH holder, int position) {
        holder.tv_data.setText(historyModels.get(position).getData());
        holder.tv_time.setText(historyModels.get(position).getTime());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (position >= 0 && position < historyModels.size()) {
                            String data = historyModels.get(position).getData();
                            isDeleted = dbHelper.deleteHistory(data);
                        }
                        if (isDeleted) {
                            Toast.makeText(context, "History Deleted", Toast.LENGTH_SHORT).show();
                            historyModels.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class HistoryVH extends RecyclerView.ViewHolder{
        TextView tv_data, tv_time;
        ImageView iv_delete;
        public HistoryVH(@NonNull View itemView) {
            super(itemView);
            tv_data = itemView.findViewById(R.id.tv_data);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

}
