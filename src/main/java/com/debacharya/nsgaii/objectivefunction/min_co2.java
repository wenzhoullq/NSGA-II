package com.debacharya.nsgaii.objectivefunction;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import java.util.HashMap;
import java.util.List;
import static com.debacharya.nsgaii.loadData.*;

public class min_co2 extends AbstractObjectiveFunction{
    @Override
    public double getValue(Chromosome chromosome) {
        double ans=0;
        List<AbstractAllele> gene=chromosome.getGeneticCode();
        CityAllele hinge_cityAllele=(CityAllele)gene.get(0);
        String[] codes=hinge_cityAllele.getGene();
        String hinge_city_code=codes[0],no_hinge_city_code=codes[1];
        HashMap<Integer,Boolean> m=new HashMap<>();
        //建立hashMap用于判断是否为枢纽城市
        for(int i=0;i<hinge_city.length();i++){
            int city=hinge_city.charAt(i)-'0',code=hinge_city_code.charAt(i)-'0';
            if(code=='1') m.put(city,true);
            else m.put(city,false);
        }
        for(int i=0;i<citynum;i++){//从i出发到j,需要注意的是，从j出发到i不同的，因为运输需求不一样
            for(int j=0;j<citynum;j++){
                if(i==j) continue;
                if(!m.get(i)){//如果i不是枢纽节点
                    int no_hign_index1=no_hinge_map.get((char)(i+'0'));
                    int target1=no_hinge_city_code.charAt(no_hign_index1)-'0';
                    if(!m.get(j)){//如果j不是枢纽节点
                        int no_bign_index2=no_hinge_map.get((char)(j+'0'));
                        int target2=no_hinge_city_code.charAt(no_bign_index2)-'0';
                        ans+=(city_transport[i][j]/airplane1+city_transport[i][j]%airplane1)*
                                (city_distance[i][target1]+city_distance[target2][j])*co2_1+up_down1*2;
                        if(target1!=target2){//如果不是同一个枢纽机场和枢纽机场,还存在一段枢纽机场->枢纽机场的运输
                            ans+=discount*(city_transport[i][j]/airplane2+city_transport[i][j]%airplane2)*
                                    city_distance[target1][target2]*co2_2+up_down2;
                        }
                    }
                    else{//如果j是枢纽节点
                        ans+=(city_transport[i][j]/airplane2+city_transport[i][j]%airplane2)*(city_distance[j][target1])*co2_1
                                +up_down1;
                        if(target1!=j)//如果j部是和i直连的
                            ans+=(city_transport[i][j]/airplane1+city_transport[i][j]%airplane1)*
                                (city_distance[j][target1])*co2_2+up_down2;
                    }
                }
                else {//如果i是枢纽节点
                    int hign_index1=hinge_map.get((char)(i+'0'));
                    int target1=hinge_city_code.charAt(hign_index1)-'0';
                    if(!m.get(j)){//如果j不是枢纽节点
                        ans+=(city_transport[i][j]/airplane1+city_transport[i][j]%airplane1)*
                                (city_distance[i][target1])*co2_1;
                        if(target1!=j){
                            ans+=(city_transport[i][j]/airplane2+city_transport[i][j]%airplane2)*
                                    city_distance[i][target1]*co2_1;
                        }
                    }
                    else{//j是枢纽节点
                        ans+=(city_transport[i][j]/airplane2+city_transport[i][j]%airplane2)*
                                city_distance[i][j]*co2_2+up_down2;
                    }
                }
            }
        }
        return -1*ans;
    }
}
