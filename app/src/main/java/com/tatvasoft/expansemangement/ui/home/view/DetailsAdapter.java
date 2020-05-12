package com.tatvasoft.expansemangement.ui.home.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tatvasoft.expansemangement.R;
import com.tatvasoft.expansemangement.ui.intro.model.DetailsModel;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {

    private final Context context;
    private final ArrayList<DetailsModel> dataList;
    private DetailsModel details;
    private boolean isGridView = false;
    private int positionOfData;
    private int title;
    private SharedPreferences sharedpreferences;
    private String writerName;
    private Context rootContext;


    public DetailsAdapter(Context context, ArrayList<DetailsModel> dataList) {
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
            tvSpend=itemView.findViewById(R.id.tvSpendView);
            tvCategory=itemView.findViewById(R.id.tvCategory);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }
}
