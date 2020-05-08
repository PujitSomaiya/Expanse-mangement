package com.tatvasoft.expansemangement.ui.category.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.category.model.DataModel;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    private final Context context;
    private final ArrayList<DataModel> dataList;
    private DataModel details;
    private boolean isGridView = false;
    private int positionOfData;
    private int title;
    private SharedPreferences sharedpreferences;
    private String writerName;
    private Context rootContext;


    public DetailsAdapter(Context context, ArrayList<DataModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        rootContext = context;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailsViewHolder holder, final int position) {
        details=dataList.get(position);
        {
            holder.tvCategory.setText(details.getCategory());
            holder.tvRemark.setText(details.getRemark());
            holder.tvSpend.setText(details.getMoneySpend());
        }
        }




    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCategory,tvSpend,tvRemark;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            tvRemark=itemView.findViewById(R.id.tvRemark);
            tvSpend=itemView.findViewById(R.id.tvSpend);
            tvCategory=itemView.findViewById(R.id.tvCategory);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }
}
