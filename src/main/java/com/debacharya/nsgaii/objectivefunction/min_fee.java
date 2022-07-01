package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;

import java.util.HashMap;
import java.util.List;

import static com.debacharya.nsgaii.loadData.*;

public class min_fee extends AbstractObjectiveFunction  {

    @Override
    public double getValue(Chromosome chromosome) {
        List<AbstractAllele> gene=chromosome.getGeneticCode();
        CityAllele hinge_cityAllele=(CityAllele)gene.get(0),no_hinge_cityAllele=(CityAllele) gene.get(1);
        String[] center=hinge_cityAllele.getGene(),no_center=no_hinge_cityAllele.getGene();
        String hinge_city=center[0],hinge_city_code=center[1],no_hinge_city=no_center[0],no_hinge_code=no_center[1];
        int[][] distance=(int[][]) city_distance.clone();
        int hinge_city_length=hinge_city.length();
        HashMap<Integer,Boolean> m=new HashMap<>();
        //建立hashMap用于判断是否为枢纽城市
        for(int i=0;i<hinge_city.length();i++){
            int city=hinge_city.charAt(i)-'0',code=hinge_city_code.charAt(i)-'0';
            if(code==1) m.put(city,true);
            else m.put(city,false);
        }
        //初始化,枢纽城市之间的连接
        for(int i=0;i<citynum;i++){
            for(int j=0;j<citynum;j++){
                if(!m.get(i)&&m.get(j)) distance[i][j]=INF;
            }
        }
        //初始化，非枢纽城市和枢纽城市之间的连接

        //DJ算法最优路径
        int ans=0;
        for(int i=0;i<citynum;i++){
            for(int j=0;j<citynum;j++) {
                if (i == j) continue;
                int temp = DJ_cal_fee();
                temp = temp * (city_transport[i][j] / max_transport + city_transport[i][j] % max_transport == 0 ? 0 : 1);
                ans += temp;
            }
        }
        return ans;
    }
}
