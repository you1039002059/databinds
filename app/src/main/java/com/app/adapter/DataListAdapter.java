package com.app.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.smtask.R;
import com.app.extras.RcylcVItemClick;
import com.app.model.DataManager;
import com.app.smtask.databinding.AdapterLayoutBinding;

import java.util.ArrayList;

/*
 * Created by Yash on 13/6/18.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.MyViewHolder>
{
    private ArrayList<DataManager> dataArrayList;
    private RcylcVItemClick clickListener;

    public DataListAdapter(ArrayList<DataManager> dataArrayList)
    {
        /*
         * DataListAdapter Constructor to Initialize Data which we get from MainActivity
         */

        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        /*
         * LayoutInflater is used to Inflate the view from adapter_layout for showing data in RecyclerView
         */

        AdapterLayoutBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
        /*
         * onBindViewHolder is used to Binding all the respective data to Textview or Imageview form dataArrayList
         */

        DataManager dataModel = dataArrayList.get(position);
        holder.dataItemBinding.setDataManager(dataModel);
    }

    @Override
    public int getItemCount()
    {
        /*
         * getItemCount is used to get the size of respective dataArrayList
         */

        return dataArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        /*
         * Return the view type of the item at position for the purposes of view recycling.
         */

        return position;
    }


    public void setOnItemClickListener(final RcylcVItemClick mItemClickListener)
    {
        this.clickListener = mItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        AdapterLayoutBinding dataItemBinding;

        /*
         * MyViewHolder is used to Initializing the DataBinding.
         */

        MyViewHolder(AdapterLayoutBinding dataItemLayoutBinding)
        {
            super(dataItemLayoutBinding.getRoot());
            dataItemBinding = dataItemLayoutBinding;

            dataItemLayoutBinding.getRoot().setTag(dataItemLayoutBinding.getRoot());
            dataItemLayoutBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (clickListener != null)
            {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}