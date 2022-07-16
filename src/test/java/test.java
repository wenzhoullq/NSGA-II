import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.loadData;
import com.debacharya.nsgaii.objectivefunction.min_fee;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;

import java.util.*;

import static com.debacharya.nsgaii.loadData.*;
import static com.debacharya.nsgaii.plugin.DefaultPluginProvider.*;

public class test {
    public static void main(String[] args) {
            loadData.init();
        Create_no_hinge_city_code(Create_hinge_city_code());
        String hinge_code=Create_hinge_city_code();
        String no_hinge_code=Create_no_hinge_city_code(hinge_code);
        System.out.println(hinge_city);
        System.out.println(hinge_code);
        System.out.println(no_hinge_code);
        List<CityAllele> cityAlleles = new ArrayList<>();
        cityAlleles.add(new CityAllele(hinge_code,no_hinge_code));
        Chromosome initIndividual1 = new Chromosome(new Chromosome(cityAlleles));
         hinge_code=Create_hinge_city_code();
         no_hinge_code=Create_no_hinge_city_code(hinge_code);
        System.out.println(hinge_code);
        System.out.println(no_hinge_code);
        cityAlleles = new ArrayList<>();
        cityAlleles.add(new CityAllele(hinge_code,no_hinge_code));
        Chromosome initIndividual2 = new Chromosome(new Chromosome(cityAlleles));
        List<Chromosome> children = new ArrayList<>();
//        Crossover(children,initIndividual1,initIndividual2);
    }
}
