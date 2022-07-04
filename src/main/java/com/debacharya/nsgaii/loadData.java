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
    //备选枢纽城市基因表
    public static String hinge_city;
    //非枢纽城市基因表
    public static String no_hinge_city;
    //备选枢纽城市的数量(第一条等位基因的长度)
    public static int hinge_citynum;
    //非枢纽城市的数量（第二条等位基因的长度）
    public static int no_hinge_citynum;
    //单架飞机的最大运力
    public static  int max_transport;
    //已产生的基因型记录
    public static HashMap<Chromosome, Boolean> historyRecord;
    //交叉时选择基因的概率
    public static  double p=0.5;
}
