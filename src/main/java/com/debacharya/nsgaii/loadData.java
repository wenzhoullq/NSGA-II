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
    //选择为待用中枢机场概率表
    public static double[] zx_zd=new double[citynum];
    // 中枢机场概率表——归一化处理————因为枢纽机场基因表确定下来，它的概率表也会确定下来
    public static double[] hinge_city_zx_zd_choose=new double[hinge_citynum];
    //中枢机场概率选择表——根据权重来随机选择的表
    public static double[] hinge_city_zx_zd_quanzhong=new double[hinge_citynum+1];
    //中枢机场的名称和下标的映射
    public static  HashMap<Character,Integer> hinge_map=new HashMap<>();
    //非中枢机场的名称和下标的映射
    public static  HashMap<Character,Integer> no_hinge_map=new HashMap<>();
}
