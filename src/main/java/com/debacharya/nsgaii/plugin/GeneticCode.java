package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.CityAllele;

import java.util.ArrayList;
import java.util.List;

public class GeneticCode implements GeneticCodeProducer{
    //两种等位基因，一种是中枢城市等位基因，另一种是非中枢城市等位基因
    private String hinge_city;
    private String hinge_code;
    private String no_hinge_city;
    private String no_hinge_code;

    public void setNo_hinge_city(String no_hinge_city) {
        this.no_hinge_city = no_hinge_city;
    }

    @Override
    public List<CityAllele> produce(int length) {
        List<CityAllele> geneticCode=new ArrayList<>();
        CityAllele hinge_city_Allele=new CityAllele(hinge_city,hinge_code),no_hinge_city_Allele=new CityAllele(no_hinge_city,no_hinge_code);
        geneticCode.add(0,hinge_city_Allele);
        geneticCode.add(1,no_hinge_city_Allele);
        return geneticCode;
    }
}
