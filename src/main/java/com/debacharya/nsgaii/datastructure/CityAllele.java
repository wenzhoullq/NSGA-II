package com.debacharya.nsgaii.datastructure;

public class CityAllele extends AbstractAllele{
    private  String city;
    private String code;
    public CityAllele(String city,String code) {
        super(city);
        this.city=city;
        this.code=code;
    }
    @Override
    public String[] getGene() {
        return new String[]{city,code};
    }
    @Override
    public AbstractAllele getCopy() {
        return new CityAllele(city,code);
    }

}
