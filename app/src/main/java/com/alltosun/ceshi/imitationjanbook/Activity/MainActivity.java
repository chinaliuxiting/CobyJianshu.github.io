package com.alltosun.ceshi.imitationjanbook.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alltosun.ceshi.imitationjanbook.Activity.Activity.WriteTextActivity;
import com.alltosun.ceshi.imitationjanbook.Activity.Fragment.FindFragment;
import com.alltosun.ceshi.imitationjanbook.Activity.Fragment.FocusonFragment;
import com.alltosun.ceshi.imitationjanbook.Activity.Fragment.MeFragment;
import com.alltosun.ceshi.imitationjanbook.Activity.Fragment.MessagerFragment;
import com.alltosun.ceshi.imitationjanbook.R;

import Base.BaseActivity;
import Utlis.BarUtils;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private  Toolbar toolbar;
    private  long limit=0;
    private FrameLayout fg;
    FragmentManager manager;
    FragmentTransaction transaction;
    private FindFragment findFragment;//发现
    private FocusonFragment focusonFragment;//关注
    private MeFragment meFragment;//我的
    private MessagerFragment messagerFragment;//消息
    Fragment showfragment;//当前页，即将要隐藏的页面
    private RadioGroup radioGroup;
    private RadioButton write;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BarUtils.setColor(this, Color.parseColor("#FFFFFF"), 0);
        BarUtils.StatusBarLightMode(this);
        manager=getSupportFragmentManager();
        initview();
        initdate();
    }



    private void initdate() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.focus://关注
                       if (focusonFragment==null){
                           focusonFragment=new FocusonFragment();
                           addFragment(focusonFragment,"focus");
                       }else {
                           switchFragment(showfragment,focusonFragment);
                       }
                        break;
                    case R.id.find://发现
                        if (findFragment==null){
                            findFragment=new FindFragment();
                            addFragment(findFragment,"find");
                        }else {
                            switchFragment(showfragment,findFragment);
                        }

                        break;
                    case  R.id.write://写文章

                        break;
                    case R.id.message://消息
                        if (messagerFragment==null){
                            messagerFragment=new MessagerFragment();
                            addFragment(messagerFragment,"message");
                        }else {
                            switchFragment(showfragment,messagerFragment);
                        }
                        break;
                    case R.id.mine://我的
                        if (meFragment==null){
                            meFragment=new MeFragment();
                            addFragment(meFragment,"mine");
                        }else {
                            switchFragment(showfragment,meFragment);
                        }
                        break;
                }
            }
        });
    }

    private void initview() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        fg= (FrameLayout) findViewById(R.id.fg);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        write= (RadioButton) findViewById(R.id.write);
        write.setOnClickListener(this);
        focusonFragment=new FocusonFragment();
        addFragment(focusonFragment,"focus");
    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis()-limit>2000){
            limit=System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }
    }

    public  void addFragment(Fragment fragment,String tag){
        transaction =manager.beginTransaction();
        if (showfragment==null){
        }else {
            transaction.hide(showfragment);
        }
        transaction.add(R.id.fg,fragment);
        transaction.commit();
        showfragment=null;
        showfragment=fragment;
        Log.i("当前的fragment", showfragment.toString() + "/" + tag);
    }
    //切换页面的重载，优化了fragment 的切换
    public void switchFragment(Fragment from,Fragment to){
        if(from==null||to==null){
            return ;
        }else {
            FragmentTransaction transaction=manager.beginTransaction();
            if (!to.isAdded()){
                //隐藏当前的fragment，
                transaction.hide(from).add(R.id.fg,to).commit();
            }else {
                transaction.hide(from).show(to).commit();
            }
            showfragment=to;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.write:
                Toast.makeText(MainActivity.this,"点击成功",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,WriteTextActivity.class));
                break;
        }

    }


}
