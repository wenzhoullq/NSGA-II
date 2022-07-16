package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.datastructure.ValueAllele;
import com.debacharya.nsgaii.objectivefunction.min_fee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.debacharya.nsgaii.loadData.hinge_city;
import static com.debacharya.nsgaii.plugin.DefaultPluginProvider.Create_hinge_city_code;
import static com.debacharya.nsgaii.plugin.DefaultPluginProvider.Create_no_hinge_city_code;

public class GeneticCodeProducerProvider {

	public static GeneticCodeProducer binaryGeneticCodeProducer() {
		return (length) -> {

			List<BooleanAllele> geneticCode = new ArrayList<>();

			for(int i = 0; i < length; i++)
				geneticCode.add(i, new BooleanAllele(ThreadLocalRandom.current().nextBoolean()));

			return geneticCode;
		};
	}

	public static GeneticCodeProducer valueEncodedGeneticCodeProducer() {
		return length -> {
			Create_no_hinge_city_code(Create_hinge_city_code());
			String hinge_code=Create_hinge_city_code();
			String no_hinge_code=Create_no_hinge_city_code(hinge_code);
//			System.out.println(hinge_city);
//			System.out.println(hinge_code);
//			System.out.println(no_hinge_code);
			List<CityAllele> cityAlleles = new ArrayList<>();
			cityAlleles.add(new CityAllele(hinge_code,no_hinge_code));

			return cityAlleles;
		};
	}
}
