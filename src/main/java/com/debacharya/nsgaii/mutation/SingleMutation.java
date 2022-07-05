package com.debacharya.nsgaii.mutation;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.debacharya.nsgaii.loadData.hinge_city;
import static com.debacharya.nsgaii.loadData.hinge_citynum;
import static com.debacharya.nsgaii.loadData.no_hinge_citynum;

public class SingleMutation extends AbstractMutation{
    @Override
    public Chromosome perform(Chromosome chromosome) {
        //取出染色体的等位基因
        List<AbstractAllele> genelist=chromosome.getGeneticCode();
        CityAllele cityAllele=(CityAllele)genelist.get(0);
        String[] codes=cityAllele.getGene();
        char[] hinge_city_code=codes[0].toCharArray(),
               no_hinge_city_code=codes[1].toCharArray();
        Random random=new Random();
        int no_hinge_city_mutation= random.nextInt(no_hinge_citynum);
        int hinge_city_mutation=random.nextInt(hinge_citynum);
        while(true){
            if(hinge_city_code[hinge_city_mutation]==1) break;
            hinge_city_mutation=random.nextInt(hinge_citynum);
        }
        no_hinge_city_code[no_hinge_city_mutation]=hinge_city.charAt(hinge_city_mutation);
        List<AbstractAllele> targetlist=new ArrayList<>();
        CityAllele targetAllele=new CityAllele(new String(hinge_city_code),new String(no_hinge_city_code));
        targetlist.add(targetAllele);
        return new Chromosome(targetlist);
    }
}
