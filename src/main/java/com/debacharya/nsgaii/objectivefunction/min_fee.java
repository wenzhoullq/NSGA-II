package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.debacharya.nsgaii.loadData.*;

public class min_fee extends AbstractObjectiveFunction  {

    @Override
    public double getValue(Chromosome chromosome) {
        float ans=0;
        List<AbstractAllele> gene=chromosome.getGeneticCode();
        CityAllele hinge_cityAllele=(CityAllele)gene.get(0);
        String[] codes=hinge_cityAllele.getGene();
        String hinge_city_code=codes[0],no_hinge_city_code=codes[1];
        HashSet<Integer> m=new HashSet<>();
        //建立hashMap用于判断是否为枢纽城市
        String[] hinge_citys=hinge_city.split("_"),
                no_hinge_citys=no_hinge_city_code.split("_");
        for(int i=0;i<hinge_citys.length;i++){
            int cityname=Integer.parseInt(hinge_citys[i]);
            int code=hinge_city_code.charAt(i)-'0';
//            System.out.println(code+" ");
            if(code==1) m.add(cityname);
            else m.add(cityname);
        }
        for(int i=0;i<citynum;i++){//从i出发到j,需要注意的是，从j出发到i不同的，因为运输需求不一样
//            System.out.println(i);
            for(int j=0;j<citynum;j++){
                if(i==j) continue;
                float all_transport=city_transport[i][j]+city_transport[j][i];
                if(!m.contains(i)){//如果i不是枢纽节点
                    int no_hign_index1=no_hinge_map2.get(i);
                    int target1=Integer.parseInt(no_hinge_citys[no_hign_index1]);
                    if(!m.contains(j)){//如果j不是枢纽节点
                        int no_bign_index2=no_hinge_map2.get(j);
                        int target2=Integer.parseInt(no_hinge_citys[no_bign_index2]);
                        ans+=((all_transport+airplane1-1)/airplane1)*
                                (city_distance[i][target1]+city_distance[target2][j])*2*fee1;//从j到i和从i到j，路程需要算两次
                        if(target1!=target2){//如果不是同一个枢纽机场和枢纽机场,还存在一段枢纽机场->枢纽机场的运输
                            ans+=discount*((all_transport+airplane2-1)/airplane2)*
                                    city_distance[target1][target2]*2*fee2;
                        }
                    }
                    else{//如果j是枢纽节点
                        ans+=((all_transport+airplane1-1)/airplane1)*
                                city_distance[i][target1]*2*fee1;
                        if(target1!=j){//如果不是同一个枢纽机场和枢纽机场,还存在一段枢纽机场->枢纽机场的运输
                            ans+=discount*((all_transport+airplane2-1)/airplane2)*
                                    city_distance[target1][j]*2*fee2;
                        }
                    }
                }
                else {//如果i是枢纽节点
                    if(!m.contains(j)){//如果j不是枢纽节点
//                        System.out.println(j);
                        int no_hign_index1=no_hinge_map2.get(j);
                        int target1=Integer.parseInt(no_hinge_citys[no_hign_index1]);
                        ans+=((all_transport+airplane1-1)/airplane1)*
                                (city_distance[j][target1])*2*fee1;
                        if(target1!=i){
                            ans+=discount*((all_transport+airplane2-1)/airplane2)*
                                    city_distance[target1][i]*2*fee2;
                        }
                    }
                    else{//j是枢纽节点
                        ans+=discount*((all_transport+airplane2-1)/airplane2)*
                                city_distance[j][i]*2*fee2;
                    }
                }
            }
        }
        return -1*ans;
    }
}
