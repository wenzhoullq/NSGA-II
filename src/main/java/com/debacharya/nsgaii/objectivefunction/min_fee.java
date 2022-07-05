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
        int ans=0;
        List<AbstractAllele> gene=chromosome.getGeneticCode();
        CityAllele hinge_cityAllele=(CityAllele)gene.get(0);
        String[] codes=hinge_cityAllele.getGene();
        String hinge_city_code=codes[0],no_hinge_city_code=codes[1];
        int hinge_city_length=hinge_city.length();
        HashMap<Integer,Boolean> m=new HashMap<>();
        //建立hashMap用于判断是否为枢纽城市
        for(int i=0;i<hinge_city.length();i++){
            int city=hinge_city.charAt(i)-'0',code=hinge_city_code.charAt(i)-'0';
            if(code=='1') m.put(city,true);
            else m.put(city,false);
        }
        for(int i=0;i<citynum;i++){
            for(int j=0;j<citynum;j++){
                if(i==j) continue;
                if(!m.get(i)){
                    int no_hign_index1=no_hinge_map.get((char)(i+'0'));
                    int target=no_hinge_city_code.charAt(no_hign_index1)-'0';
                    if(!m.get(j)){

                    }
                    else{

                    }
                }
            }
        }


        return -1*ans;
    }
}
