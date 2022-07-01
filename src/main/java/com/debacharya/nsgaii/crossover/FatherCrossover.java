package com.debacharya.nsgaii.crossover;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.GeneticCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.debacharya.nsgaii.loadData.historyRecord;


public class FatherCrossover extends AbstractCrossover{
    public FatherCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
        super(crossoverParticipantCreator);
    }
    private void singlePointCrossover(List<Chromosome> children, Chromosome p1, Chromosome p2){
        //取出p1,p2染色体的等位基因
        List<AbstractAllele> gene1=p1.getGeneticCode(),gene2=p2.getGeneticCode();
        CityAllele hinge_cityAllele1=(CityAllele)gene1.get(0),no_hinge_cityAllele1=(CityAllele) gene1.get(1),
                   hinge_cityAllele2=(CityAllele)gene2.get(0),no_hinge_cityAllele2=(CityAllele) gene2.get(1);
        String[] center1=hinge_cityAllele1.getGene(),no_center1=no_hinge_cityAllele1.getGene(),
                 center2=hinge_cityAllele2.getGene(),no_center2=no_hinge_cityAllele2.getGene();
        String hinge_city1=center1[0],hinge_city_code1=center1[1],no_hinge_city1=no_center1[0],no_hinge_code1=no_center1[1],
                hinge_city2=center2[0],hinge_city_code2=center2[1],no_hinge_city2=no_center2[0],no_hinge_code2=no_center2[1];
        //交叉染色体，对于枢纽城市，则将他们加入并集，并按照p=0.5的概率进行伯努利抽样,并且枢纽城市的数量保持n=hinge_citynum
        Chromosome c=null;
        if (!historyRecord.containsKey(c)) {
            historyRecord.put(c, true);
            children.add(c);
        }

    }
    @Override
    public List<Chromosome> perform(Population population) {
        int parentSize = population.getPopulace().size();
        if (parentSize < 2) {
            return new ArrayList<>();
        }
        // 基于交叉概率计算当前代交叉的组数
        int crossNum = (int) (this.crossoverProbability * parentSize);
        crossNum = Math.max(crossNum, 1);
        // 选择用于交叉的双亲和交叉点
        int[] parentArr1 = new int[crossNum];
        int[] parentArr2 = new int[crossNum];
        for (int i = 0; i < crossNum; i++) {
            parentArr1[i] = ThreadLocalRandom.current().nextInt(0, parentSize);
            do {
                parentArr2[i] = ThreadLocalRandom.current().nextInt(0, parentSize);
            } while (parentArr1[i] == parentArr2[i]);
        }
        // 执行交叉操作
        // 交叉产生的新个体集合
        List<Chromosome> children = new ArrayList<>();
        for (int i = 0; i < crossNum; i++) {
            Crossover(children,
                    population.getPopulace().get(parentArr1[i]),
                    population.getPopulace().get(parentArr2[i]),
                    );
        }
        return children;
    }
}
