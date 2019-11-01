package com.app.smtask;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.app.smtask.databinding.DetailsViewLayoutBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Created by Yash on 13/6/18.
 */

@Param(id = R.layout.details_view_layout)
public class DetailsView extends BaseActivity<DetailsViewLayoutBinding,BaseViewModel> implements View.OnClickListener
{


    @Override//Click listener on backBtn
    @OnClick({R.id.backBtn})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.backBtn:
                moveToPreviousScreen();
                break;
        }
    }

    @Override
    protected void initViews() {

        /* Intialization of ButterKnife */
        ButterKnife.bind(this);

        // Getting data in bundle form Previous Screen.
      /*  Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {*/
            //String htmlTxtParams = bundle.getString(getString(R.string.htmlTxtParams));

            //if (!TextUtils.isEmpty(htmlTxtParams))
                mBinding.setLoadData("fwfwfwf减肥我就佛文件发我金卧佛fwfwff");
       // }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initObservers() {

    }

    @Override
    public void onBackPressed()
    {
        moveToPreviousScreen();
    }

    private void moveToPreviousScreen()
    {
        finish();
    }
}
