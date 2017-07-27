package com.ramadan.twitterclient.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ramadan.twitterclient.R;

import butterknife.ButterKnife;

/**
 * Created by Mahmoud Ramadan on 7/24/17.
 */

public  abstract  class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //setupToolbar();
        ButterKnife.bind(this);
    }


  protected  abstract int  getLayoutId();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


//    private Toolbar setupToolbar(){
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if(toolbar!=null)
//            setSupportActionBar(toolbar);
//        return toolbar;
//    }


}
