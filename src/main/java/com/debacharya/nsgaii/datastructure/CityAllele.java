package com.debacharya.nsgaii.datastructure;

public class CityAllele extends AbstractAllele{
    private String hinge_code;
    private String no_hinge_code;
    public CityAllele(String hinge_code,String no_hinge_code) {
        super(null);
        this.hinge_code=hinge_code;
        this.no_hinge_code=no_hinge_code;
    }
    @Override
    public String[] getGene() {
        return new String[]{hinge_code,no_hinge_code};
    }
    @Override
    public AbstractAllele getCopy() {
        return new CityAllele(hinge_code,no_hinge_code);
    }

}
