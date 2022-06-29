package com.debacharya.nsgaii.datastructure;

public class CityAllele extends AbstractAllele{
    private  String city;
    public CityAllele(String city) {
        super(city);
        this.city=city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getGene() {
        return city;
    }
    @Override
    public AbstractAllele getCopy() {
        return new CityAllele(this.city);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CityAllele) {
            CityAllele contrast = (CityAllele) obj;
            if (this.city.length() != contrast.city.length()) {
                return false;
            }
            if(!this.city.equals(contrast.city)) return false;
            return true;
        }
        return false;
    }
    @Override
    public int hashCode() {
        // 返回一个固定值，强行执行equals
        return 0;
    }
}
