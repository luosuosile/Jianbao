package com.developer.rimon.zhihudaily.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.rimon.zhihudaily.Constants;
import com.developer.rimon.zhihudaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity implements View.OnClickListener {//这里实现了监听器的接口下面就不用重新接口

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.blog_address)
    TextView blogAddress;
    @BindView(R.id.github_address)
    TextView githubAddress;
    @BindView(R.id.project_address)
    TextView projectAddress;
    @BindView(R.id.activity_about)
    LinearLayout activityAbout;
    /*以上分别使得控件和布局被绑定*/

    private String versionName;//这里是版本名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);//绑定布局
        ButterKnife.bind(this);//绑定的最后一步

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;//那么长只是为了获取版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }//当你要读取什么的时候一定要用try catch

        if (versionName !=null){
            String title = getResources().getString(R.string.app_name )+ versionName;
            toolbar.setTitle(title);
        }else {
            toolbar.setTitle(R.string.app_name);
        }
        toolbar.setTitleTextColor(Color.WHITE);//设置颜色
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//设定回退按钮

        blogAddress.setOnClickListener(this);//点击按钮（链接）进入链接，要传入this
        githubAddress.setOnClickListener(this);
        projectAddress.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){//获取界面设置的id，有学到了
            case R.id.blog_address:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ABOUT_BLOG_ADDRESS)));//有一个content类存放这些公开的数据
                break;
            case R.id.github_address:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ABOUT_GITHUB_ADDRESS)));
                break;
            case R.id.project_address:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ABOUT_PROJECT_ADDRESS)));
                break;
            //ACTION_VIEW很有用
        }
    }
}
