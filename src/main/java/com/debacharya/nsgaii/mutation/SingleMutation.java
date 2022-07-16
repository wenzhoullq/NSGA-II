package com.debacharya.nsgaii.mutation;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.debacharya.nsgaii.loadData.*;

public class SingleMutation extends AbstractMutation{
    public SingleMutation(float mutationProbability){
        super(mutationProbability);
    }
    @Override
    public Chromosome perform(Chromosome chromosome) {
        //取出染色体的等位基因
        List<AbstractAllele> genelist=chromosome.getGeneticCode();
        CityAllele cityAllele=(CityAllele)genelist.get(0);
        String[] codes=cityAllele.getGene();
        char[] hinge_city_code=codes[0].toCharArray();
        String no_hinge_city_code=codes[1];
        Random random=new Random();
        for(int i=0;i<hinge_citynum;i++){
            float temp_p=random.nextFloat();
//            System.out.println(temp_p+" "+mutationProb);
            if(temp_p<mutationProb){
                if(hinge_city_code[i]=='1') hinge_city_code[i]='0';
                else hinge_city_code[i]='1';
            }
        }
        List<AbstractAllele> targetlist=new ArrayList<>();
        String after_hinge_city_code=new String(hinge_city_code);
//        System.out.println(after_hinge_city_code);
//        System.out.println(codes[0]);
        if(!after_hinge_city_code.equals(codes[0])) no_hinge_city_code=DefaultPluginProvider.Create_no_hinge_city_code(new String(hinge_city_code));
        CityAllele targetAllele=new CityAllele(after_hinge_city_code,no_hinge_city_code );
        targetlist.add(targetAllele);
        return new Chromosome(targetlist);
    }
}
