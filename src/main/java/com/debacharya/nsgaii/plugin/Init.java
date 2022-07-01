package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

public class Init implements PopulationProducer{
    @Override
    public Population produce(int populationSize, int chromosomeLength, GeneticCodeProducer geneticCodeProducer, FitnessCalculator fitnessCalculator) {
        List<Chromosome> populace = new ArrayList();
        for(int i = 0; i < populationSize; ++i) {
            populace.add(new Chromosome(geneticCodeProducer.produce(chromosomeLength)));
        }
        return new Population(populace);
    }
}
