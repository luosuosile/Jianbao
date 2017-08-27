package com.developer.rimon.zhihudaily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.developer.rimon.zhihudaily.R;
import com.developer.rimon.zhihudaily.adapter.CommentRecyclerAdapter;
import com.developer.rimon.zhihudaily.entity.Comment;
import com.developer.rimon.zhihudaily.entity.CommentList;
import com.developer.rimon.zhihudaily.listener.OnGetListener;
import com.developer.rimon.zhihudaily.utils.HttpUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.comment_recyclerview)
    RecyclerView commentRecyclerView;

    private String id;
    public static int shortCommentsCount;
    public static int longCommentsCount;
    private boolean canLoadShortComment = true;//使得一开始点击事件可以成立

    private ArrayList<Comment> comments = new ArrayList<>();
    private CommentRecyclerAdapter commentAdapter;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();//获取当前的
        id = intent.getStringExtra("id");//获取每条的id，那么每条的id哪里来的
        longCommentsCount = intent.getIntExtra("long_comments", 0);//这是个去键值对的函数，后面的0是没有值得时候用的
        shortCommentsCount = intent.getIntExtra("short_comments", 0);
        int commentsCount = intent.getIntExtra("comments", 0);

        toolbar.setTitle(commentsCount + "条点评");//设置工具栏的题目
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });//放置回退按钮

        commentAdapter = new CommentRecyclerAdapter(this, comments);//一个评论适配器
        layoutManager = new LinearLayoutManager(this);//布局管理器
        commentRecyclerView.setAdapter(commentAdapter);//放置布局适配器
        commentRecyclerView.setLayoutManager(layoutManager);//放置布局管理器

        HttpUtil.getLongComment(id, new OnGetListener() {//1个自己写的listener，这个listener什么也不监听但纯就是个方法？
            @Override
            public void onNext(Object object) {
                CommentList commentList = (CommentList) object;//获得一个评论列表对象
                commentAdapter.setFooterViewPosition(commentList.comments.size() + 1);//
                comments.addAll(commentList.comments);//将评论放入
                commentAdapter.notifyDataSetChanged();//更新
            }
        });//自动加载长评


        commentAdapter.setOnCommentLayoutClickListener(new CommentRecyclerAdapter.OnCommentLayoutClickListener() {
            @Override
            public void onClick() {//点击事件（点击查看短评）
                if (canLoadShortComment) {
                    canLoadShortComment = false;
                    HttpUtil.getShortComment(id, new OnGetListener() {
                        @Override
                        public void onNext(Object object) {
                            CommentList commentList = (CommentList) object;
                            commentAdapter.setHeaderAndFooterCount(1);
                            comments.add(new Comment());
                            comments.addAll(commentList.comments);
                            commentAdapter.notifyDataSetChanged();
                            commentRecyclerView.scrollBy(0, layoutManager.findViewByPosition((longCommentsCount + 1))
                                    .getTop());
                        }
                    });
                }
            }
        });
    }
}
