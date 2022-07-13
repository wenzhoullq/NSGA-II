package com.debacharya.nsgaii;

import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.HashMap;

import static com.debacharya.nsgaii.plugin.DefaultPluginProvider.Create_city;

public class loadData {
    //实验重复次数
    public static int experimentTimes;
    //最大个体数目
    public static int MaxRecord ;
    //种群的最大的代数
    public static int MaxGeneration;
    //最大种群数
    public  static  int MaxPopulationSize;
    //变异的概率
    public static float mutationProb;
    //交叉的概率
    public static float crossoverProb;
    //种群初始化的数量
    public static int start_num;
    //城市之间的距离
    public static int[][] city_distance;
    //城市之间的运输量
    public static float[][] city_transport;
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
    //已产生的基因型记录
    public static HashMap<String, Boolean> historyRecord;//String是:枢纽城市code+非枢纽城市code
    //交叉时选择基因的概率
    public static  float p= 0.5F;
    //选择为待用中枢机场概率表
    public static float[] zx_zd;
    // 中枢机场概率表——归一化处理————因为枢纽机场基因表确定下来，它的概率表也会确定下来
    public static float[] hinge_city_zx_zd_choose;
    //中枢机场概率选择表——根据权重来随机选择的表
    public static float[] hinge_city_zx_zd_quanzhong;
    //中枢机场基因下标和中枢机场的名称的映射，下标在前
    public static  HashMap<Integer,Integer> hinge_map1=new HashMap<>();
    //中枢机场基因下标和中枢机场的名称的映射，下标在后
    public static  HashMap<Integer,Integer> hinge_map2=new HashMap<>();
    //非中枢机场基因下标和非中枢机场的名称的映射，下标在前
    public static  HashMap<Integer,Integer> no_hinge_map1=new HashMap<>();
    //非中枢机场基因下标和非中枢机场的名称的映射，下标在前
    public static  HashMap<Integer,Integer> no_hinge_map2=new HashMap<>();
    //飞机A单位飞机每公里的费用,用于支线—>中枢或者中枢——>支线
    public static int fee1;
    //飞机B单位飞机每公里的费用,用于中枢—>中枢
    public static int fee2;
    //飞机A单位飞机的容量,用于支线—>中枢或者中枢——>支线
    public static int airplane1;
    //飞机B单位飞机的容量,用于中枢——>中枢
    public static int airplane2;
    //折扣系数，用于中枢机场和中枢机场之间成本的打折
    public static float discount;
    //飞机A单位飞机每公里的费用,用于支线—>中枢或者中枢——>支线
    public static int co2_1;
    //飞机B单位飞机每公里的费用,用于中枢—>中枢
    public static int co2_2;
    //飞机A起飞降落的油耗
    public static int up_down1;
    //飞机B起飞降落的油耗
    public static int up_down2;
    public static void setExperimentTimes(int experimentTimes) {
        loadData.experimentTimes = experimentTimes;
    }

    public static void setMaxRecord(int maxRecord) {
        MaxRecord = maxRecord;
    }

    public static void setMaxGeneration(int maxGeneration) {
        MaxGeneration = maxGeneration;
    }

    public static void setMaxPopulationSize(int maxPopulationSize) {
        MaxPopulationSize = maxPopulationSize;
    }

    public static void setMutationProb(float mutationProb) {
        loadData.mutationProb = mutationProb;
    }

    public static void setCrossoverProb(float crossoverProb) {
        loadData.crossoverProb = crossoverProb;
    }

    public static void setStart_num(int start_num) {
        loadData.start_num = start_num;
    }

    public static void setCity_distance(int[][] city_distance) {
        loadData.city_distance = city_distance;
    }

    public static void setCity_transport(float[][] city_transport) {
        loadData.city_transport = city_transport;
    }

    public static void setINF(int INF) {
        loadData.INF = INF;
    }

    public static void setCitynum(int citynum) {
        loadData.citynum = citynum;
    }

    public static void setHinge_city(String hinge_city) {
        loadData.hinge_city = hinge_city;
    }

    public static void setNo_hinge_city(String no_hinge_city) {
        loadData.no_hinge_city = no_hinge_city;
    }

    public static void setHinge_citynum(int hinge_citynum) {
        loadData.hinge_citynum = hinge_citynum;
    }

    public static void setNo_hinge_citynum(int no_hinge_citynum) {
        loadData.no_hinge_citynum = no_hinge_citynum;
    }

    public static void setHistoryRecord(HashMap<String, Boolean> historyRecord) {
        loadData.historyRecord = historyRecord;
    }

    public static void setP(float p) {
        loadData.p = p;
    }

    public static void setZx_zd(float[] zx_zd) {
        loadData.zx_zd = zx_zd;
    }

    public static void setHinge_city_zx_zd_choose(float[] hinge_city_zx_zd_choose) {
        loadData.hinge_city_zx_zd_choose = hinge_city_zx_zd_choose;
    }

    public static void setHinge_city_zx_zd_quanzhong(int len) {
        loadData.hinge_city_zx_zd_quanzhong = new float[len];
    }


    public static void setFee1(int fee1) {
        loadData.fee1 = fee1;
    }

    public static void setFee2(int fee2) {
        loadData.fee2 = fee2;
    }

    public static void setAirplane1(int airplane1) {
        loadData.airplane1 = airplane1;
    }

    public static void setAirplane2(int airplane2) {
        loadData.airplane2 = airplane2;
    }

    public static void setDiscount(float discount) {
        loadData.discount = discount;
    }

    public static void setCo2_1(int co2_1) {
        loadData.co2_1 = co2_1;
    }

    public static void setCo2_2(int co2_2) {
        loadData.co2_2 = co2_2;
    }

    public static void setUp_down1(int up_down1) {
        loadData.up_down1 = up_down1;
    }

    public static void setUp_down2(int up_down2) {
        loadData.up_down2 = up_down2;
    }
    public static  void init(){
        int[][] city_distance={{0,1446,1697,1967,2493,1200,2266,981,1178,730,1133,2842,1774,1034,690},
                               {1446,0,940,620,1100,805,1116,799,964,2191,658,3261,873,955,828},
                               {1697,940,0,1390,1757,1699,711,1618,1782,2346,1047,2258,1911,647,1039},
                               {1967,620,1390,0,548,1099,1357,1255,1308,2672,873,3836,567,1528,1389},
                               {2493,1100,1757,548,0,1606,1046,1990,1762,3142,1379,4400,1100,1860,1873},
                               {1200,805,1699,1099,1606,0,2089,240,176,1849,656,3653,717,1215,841},
                               {2266,1116,711,1357,1046,2089,0,1870,2042,2935,1364,2920,1680,1228,1570},
                               {981,799,1618,1255,1990,240,1870,0,273,1630,503,3412,929,1104,630},
                               {1178,964,1782,1308,1762,176,2042,273,0,1364,761,3649,878,1351,887},
                               {730,2191,2346,2672,3142,1849,2935,1630,1364,0,1859,3230,2242,1683,1339},
                               {1133,658,1047,873,1379,656,1364,503,761,1859,0,3061,910,735,530},
                               {2842,3261,2258,3836,4400,3653,2920,3412,3649,3230,3061,0,4238,2306,2747},
                               {1774,873,1911,567,1100,717,1680,929,878,2242,910,4238,0,1932,1312},
                               {1034,955,647,1528,1860,1215,1228,1104,1351,1683,735,2306,1932,0,474},
                               {690,828,1039,1389,1873,841,1570,630,887,1339,530,2747,1312,474,0}};
        //城市之间的运输量
        float[][] city_transport={{0,58.6f,160.8f,213.2f,73f,108f,110.8f,91.6f,386.9f,96.4f,56.1f,71.2f,60.8f,104.8f,41.2f},
                                   {47.5f,0,44.5f,27.7f,30,18.7f,47.2f,30.5f,40.2f,5,0f,9.9f,13.2f,8.9f,4.8f},
                                   {111.2f,34.2f,0,94.2f,12.2f,26.4f,82,26.1f,88.3f,22.8f,26.2f,26.4f,7.1f,38.6f,18},
                                   {151.5f,20.9f,72.2f,0,119.5f,106.9f,68.5f,51,170.6f,9.3f,46.6f,18.3f,41.1f,49.1f,27.3f},
                                   {60.6f,25.8f,17.4f,100.5f,0,21.8f,26.9f,23.6f,71.5f,5.5f,22.6f,4.3f,18,21,21.7f},
                                   {74.5f,14.8f,16.5f,78.2f,15.8f,0,11.1f,3.4f,0,12.2f,18.2f,4.2f,38.8f,18.5f,15.7f},
                                   {78.7f,34.7f,65.3f,51.8f,19.3f,5,0,9.7f,53.2f,7.5f,9.8f,5.1f,13.4f,30,27.3f},
                                   {68.9f,19.9f,18,45.1f,13.4f,3,5.2f,0,0,5.2f,8.9f,5.6f,19.7f,11.8f,18.1f},
                                   {284.5f,27.1f,64.5f,128.2f,56.9f,0,42.6f,0,0,57.3f,66.9f,30.2f,90.8f,62.8f,36.4f},
                                   {71.5f,4,15,7.6f,4.2f,10.8f,5,4,39.5f,0,7.4f,4,12.6f,7,11.3f},
                                   {44.4f,0,22.3f,37.3f,17.2f,15,8.4f,8,53,4,0,5.3f,11.7f,12.4f,9.2f},
                                   {46.1f,7.7f,22.6f,13.2f,3.2f,3,4,4,20.5f,3,4,0,3.2f,31.7f,11.7f},
                                   {43.8f,11.3f,6f,35.4f,15.5f,34.7f,10.2f,17.6f,61.7f,8.2f,10.2f,2,0,6.1f,8.6f},
                                   {68.7f,7,31.6f,35.6f,16.4f,12.7f,21.8f,9,43.2f,5,11.8f,22.7f,4,0,6.2f},
                                   {28.8f,3.5f,13.5f,21.8f,12,12.2f,19.5f,10,26.2f,9.6f,4,9.2f,7,2,0}};
        //初始化城市距离
        setCity_distance(city_distance);
        //初始化运输量
        setCity_transport(city_transport);
        //初始化城市数量
        setCitynum(15);
        //初始化中枢城市数量
        setHinge_citynum(5);
        //初始化非中枢城市数量
        setNo_hinge_citynum(10);
        //初始化中枢城市流量-距离比
        hinge_city_zx_zd_choose=new float[hinge_citynum];
        //初始化所有城市流量-距离比
        zx_zd=new float[citynum];
        setHinge_city_zx_zd_quanzhong(hinge_citynum+1);
        //初始化编码
        Create_city();
    }
}
