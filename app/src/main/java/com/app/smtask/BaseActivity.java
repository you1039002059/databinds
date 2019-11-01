package com.app.smtask;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.smtask.databinding.DetailsViewLayoutBinding;


/**
 * activity基类
 *
 * @author chenzhaojie
 * @date 2019-07-31
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {
    private static final String TAG ="BaseActivity";
    private static final long MILLISECONDS = 2000;
    private long mClickTime;
    private View mDateView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mDateViewParam;
    protected GestureDetector mGestureDetector;

    protected static MutableLiveData<Integer> mNetworkAndDeviceState = new MutableLiveData<>();
    private static BroadcastReceiver mDeviceStateBroadcastReceiver;
    protected V mBinding;
    protected VM mViewModel;


    protected abstract void initViews();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initObservers();

    private void initViewDataBinding(Bundle savedInstanceState) {
        Log.e(TAG, "R.layout.id:" +this+","+ this.getClass().getSimpleName() +", "+this.getClass().getAnnotation(Param.class).id());
        mBinding = DataBindingUtil.setContentView(this,
              this.getClass().getAnnotation(Param.class).id());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding(savedInstanceState);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        getLifecycle().removeObserver(mViewModel);
        super.onDestroy();
    }



}
