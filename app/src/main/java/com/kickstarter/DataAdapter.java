package com.kickstarter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> implements Filterable {

    private List<DataModel> dataModelList;
    private Context context;
    ArrayList<DataModel> mlist;
    Activity activity;
    List<DataModel> orig;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title,tv_amt_pledge,tv_blurb,tv_by,tv_country,tv_currency,tv_location;
        private TextView tv_percentage,tv_num_backers,tv_state,tv_type,tv_sno;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            tv_title        = (TextView) view.findViewById(R.id.tv_title);
            tv_sno          = (TextView) view.findViewById(R.id.tv_sno);
            tv_amt_pledge   = (TextView) view.findViewById(R.id.tv_amt_pledge);
            tv_blurb        = (TextView) view.findViewById(R.id.tv_blurb);
            tv_by           = (TextView) view.findViewById(R.id.tv_by);
            tv_country      = (TextView) view.findViewById(R.id.tv_country);
            tv_currency     = (TextView) view.findViewById(R.id.tv_currency);
            tv_location     = (TextView) view.findViewById(R.id.tv_location);
            tv_percentage   = (TextView) view.findViewById(R.id.tv_percentage);
            tv_num_backers  = (TextView) view.findViewById(R.id.tv_num_backers);
            tv_state        = (TextView) view.findViewById(R.id.tv_state);
            tv_type         = (TextView) view.findViewById(R.id.tv_type);
            cardView        = (CardView) view.findViewById(R.id.card_view);

        }
    }

    public DataAdapter(Context context, List<DataModel> dataModelList) {
        this.dataModelList   = dataModelList;
        this.context         = context;
        activity             = (Activity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final DataModel dataModel = dataModelList.get(position);

        holder.tv_title.setText(dataModel.getTitle());
        holder.tv_sno.setText("Sno: "+dataModel.getSno());
        holder.tv_amt_pledge.setText("Amt Pledge: "+dataModel.getAmt_pledge());
        holder.tv_blurb.setText("Blurb: "+dataModel.getBlurb());
        holder.tv_by.setText("By: "+dataModel.getBy());
        holder.tv_country.setText("Country: "+dataModel.getCountry());
        holder.tv_currency.setText("Currency: "+dataModel.getCurrency());
        holder.tv_location.setText("Location: "+dataModel.getLocation());
        holder.tv_percentage.setText("Percentage Funded: "+dataModel.getPercentage_funded());
        holder.tv_num_backers.setText("Num backers: "+dataModel.getNum_backers());
        holder.tv_state.setText("State: "+dataModel.getState());
        holder.tv_type.setText("Type: "+dataModel.getType());

        holder.cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.kickstarter.com"+dataModel.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });


    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<DataModel> results = new ArrayList<DataModel>();
                if (orig == null)
                    orig = dataModelList;
                if (constraint != null) {
                    if (orig != null & orig.size() > 0) {
                        for (final DataModel g : orig) {
                            if (g.getTitle().toLowerCase().contains(constraint.toString())) {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataModelList=new ArrayList<DataModel>();
                //reportModelList.clear();
                dataModelList = (ArrayList<DataModel>) results.values;
                notifyDataSetChanged();
                /*reportModelList.clear();
                reportModelList=ll;*/

            }
        };
    }

    public int item_id(int pos){
        DataModel rm=dataModelList.get(pos);
        int id= Integer.parseInt(rm.getId());
        return id;
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

}

