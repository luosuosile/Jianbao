package com.developer.rimon.zhihudaily.entity;

import java.util.ArrayList;

/**
 * Created by Rimon on 2016/8/26.
 */
public class Story {//这里放的其实就是从知乎日报接口获得的主键

    public String title;
    public String ga_prefix;// 供 Google Analytics 使用
    public int type;
    public String id;
    public ArrayList<String> images ;
    public String multipic;//是否含有多张图片
    public String date;


}
