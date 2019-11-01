package com.app.smtask;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.app.adapter.DataListAdapter;
import com.app.extras.AlertMsgCallback;
import com.app.extras.RcylcVItemClick;
import com.app.model.DataManager;
import com.app.viewmodel.DataVM;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RcylcVItemClick,AlertMsgCallback<String>
{
    static
    {   // To provide support of vector icon below sdk version 21.
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    // UI Widgets.
    @BindView(R.id.backBtn) ImageView backBtn;
    @BindView(R.id.dataRcylv) RecyclerView dataRcylv;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    // Labels
    private DataListAdapter dataListAdapter;
    private ArrayList<DataManager> dataArrayList;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Intialization of ButterKnife */
        ButterKnife.bind(this);

        /* Intialization of ProgressDialog */
        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.loading));
        progress.setCancelable(false);

        backBtn.setVisibility(View.GONE);

        /*  Intialization of ArrayList */
        dataArrayList = new ArrayList<>();

        /*  Intialization of RecyclerView Adapter */
        dataListAdapter = new DataListAdapter(dataArrayList);

        /*We used LinearLayoutManager to show Data in List Form */
        dataRcylv.setLayoutManager(new LinearLayoutManager(this));
        dataListAdapter.setOnItemClickListener(this);
        dataRcylv.setAdapter(dataListAdapter);

        getData();
    }

    private void getData()
    {
        progress.show();

        // Intialize ViewModel
        // Basic Functionality of ViewModel is to observer the data change and lifecycle in conscious way and survive configuration
        // changes on screen rotations.
        final DataVM viewModel = ViewModelProviders.of(this).get(DataVM.class);
        viewModel.getServerData(this).observe(this, new Observer<DataManager>()
        {
            @Override
            public void onChanged(@Nullable DataManager dataManager)
            {
                // Getting all the data from ViewModel Observer
                if(dataManager != null)
                {
                    dataArrayList.add(dataManager);

                    // Update the UI.
                    if(dataListAdapter != null)
                        dataListAdapter.notifyDataSetChanged();

                    dimissProgress();
                }
            }
        });
    }

    private void dimissProgress()
    {
        if(progress != null)
        {
            progress.dismiss();
            progress.cancel();
        }
    }

    @Override//Item Click listener for recyclerview item
    public void onItemClick(View view, int position)
    {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this,DetailsView.class);
        //Add the bundle to the intent
        bundle.putString(getString(R.string.htmlTxtParams), dataArrayList.get(position).getHtmlText());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override// Getting callback message if some error occur on api request
    public void alertMsgCallback(String ret)
    {
        dimissProgress();
        Toast.makeText(this.getApplication(),R.string.socketTimeOut,Toast.LENGTH_SHORT).show();
    }
}
