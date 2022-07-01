package com.debacharya.nsgaii;

import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.HashMap;

public class loadData {
    //城市之间的距离
    public static int[][] city_distance;
    //城市之间的运输量
    public static int[][] city_transport;
    //当为非枢纽城市之间的运输时，路径设置为最大值
    public static int INF=Integer.MAX_VALUE/2;
    //城市的数量
    public static int citynum;
    //枢纽城市的数量
    public static int hinge_citynum;
    //非枢纽城市的数量
    public static int no_hinge_citynum;
    //单架飞机的最大运力
    public static  int max_transport;
    //已产生的基因型记录
    public static HashMap<Chromosome, Boolean> historyRecord;
}
