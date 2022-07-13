import com.debacharya.nsgaii.loadData;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;

import java.math.BigDecimal;
import java.util.*;

import static com.debacharya.nsgaii.loadData.*;
import static com.debacharya.nsgaii.plugin.DefaultPluginProvider.Create_hinge_city_code;

public class test {
    public static String Create_no_hinge_city_code(String hinge_city_code){
        System.out.println(hinge_city_code);
        String ans="";
        //通过与该中枢的流量和距离的比值，来确定选择的概率，zx是距离的流量比值，zd是距离的比值
        float[][] zx=new float[no_hinge_citynum][hinge_citynum];
        float[][] zd=new float[no_hinge_citynum][hinge_citynum];
        float[][] zx_zd=new float[no_hinge_citynum][hinge_citynum];
        Random random=new Random();
        for(int i=0;i<no_hinge_citynum;i++){
            int i_sumdistance=0;
            float i_sumtransport=0;
            //计算zx_zd
            for(int j=0;j<hinge_citynum;j++){
                if(hinge_city_code.charAt(j)=='0') continue;
//                System.out.println(i+" "+j+" "+no_hinge_map1.get(i)+" "+hinge_map1.get(j));
                i_sumdistance+=city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)];
                i_sumtransport+=city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]
                                +city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)];
            }
//            System.out.println(i_sumdistance+" "+i_sumtransport);
            float sum=0;
            int city_choose_num=0;
            for(int j=0;j<hinge_citynum;j++){
                if(hinge_city_code.charAt(j)=='0') continue;
//                System.out.println((city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]+city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)])/i_sumtransport);
                zx[i][j]=(city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]+city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)])/i_sumtransport;
//                System.out.println((float)city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)]/i_sumdistance+"sadsad");
                zd[i][j]=(float) (city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)])/i_sumdistance;
                zx_zd[i][j]=zx[i][j]/zd[i][j];
//                System.out.println(zx_zd[i][j]);
                sum+=zx_zd[i][j];
                city_choose_num++;
            }
            //zx_zd归一化和制作被选择中枢城市表
            double[] zx_zd_city=new double[city_choose_num];
            HashMap<Integer,Integer> m=new HashMap<>();//被选择的枢纽城市的映射表
            int index=0;
            for(int j=0;j<hinge_citynum;j++){
                if(hinge_city_code.charAt(j)=='0') continue;
                zx_zd[i][j]=zx_zd[i][j]/sum;
                zx_zd_city[index]=zx_zd[i][j];
                m.put(index++,j);
//                System.out.println(zx_zd[i][j]);
            }
            //根据权重选择中枢城市
            double[] no_hinge_city_zx_zd_quanzhong=new double[city_choose_num+1];
            for(int j=1;j<=city_choose_num;j++){
                no_hinge_city_zx_zd_quanzhong[j]=no_hinge_city_zx_zd_quanzhong[j-1]+zx_zd_city[j-1];
            }
            int left=1,right=city_choose_num;
            double target=random.nextDouble();
            while(left<right){
                int mid=(left+right)/2;
                if(no_hinge_city_zx_zd_quanzhong[mid]<target) left=mid+1;
                else right=mid;
            }
            ans+=hinge_map1.get(m.get(left-1))+"_";
        }
        return ans.substring(0,ans.length()-1);
    }
    public static void main(String[] args) {
            loadData.init();
            System.out.println(hinge_city);
            System.out.println(no_hinge_city);
            System.out.println(Create_no_hinge_city_code(Create_hinge_city_code()));
    }
}
