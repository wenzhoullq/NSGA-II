package com.debacharya.nsgaii.crossover;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.Init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.debacharya.nsgaii.loadData.*;


public class FatherCrossover extends AbstractCrossover{
    public FatherCrossover(CrossoverParticipantCreator crossoverParticipantCreator,float crossoverProbability) {
        super(crossoverParticipantCreator);
        this.crossoverProbability = crossoverProbability;
    }
    private void Crossover(List<Chromosome> children, Chromosome p1, Chromosome p2){
        //取出p1,p2染色体的等位基因
        List<AbstractAllele> gene1=p1.getGeneticCode(),gene2=p2.getGeneticCode();
        CityAllele cityAllele1=(CityAllele)gene1.get(0),
                   cityAllele2=(CityAllele)gene2.get(0);
        String[] cityCodes1=cityAllele1.getGene(),
                 cityCodes2=cityAllele2.getGene();
        char[] hinge_city_code1=cityCodes1[0].toCharArray(),
               hinge_city_code2=cityCodes2[0].toCharArray();
        //交叉染色体，对于枢纽城市，则将他们加入并集，并按照p=0.5的概率进行伯努利抽样,并且枢纽城市的数量保持n=hinge_citynum

        Random random=new Random();
        HashSet<Integer> hs=new HashSet<>();
        //交叉中枢城市编码,按伯努利分布进行抽样，满足设定概率值则两两交换
        for(int i=0;i<hinge_citynum;i++){
            double temp_p=random.nextDouble();
            if(temp_p<=0.2) {
                char temp=hinge_city_code1[i];
                hinge_city_code1[i]=hinge_city_code2[i];
                hinge_city_code2[i]=temp;
            }
        }
        //生成非中枢城市路径之间的连接
        String son_no_hinge_city_code1= Init.find_way(),son_no_hinge_city_code2=Init.find_way();
        List<CityAllele> geneticCodes1=new ArrayList<>(),geneticCodes2=new ArrayList<>();
        CityAllele gen1=new CityAllele(new String(hinge_city_code1),son_no_hinge_city_code1),
                    gen2=new CityAllele(new String(hinge_city_code2),son_no_hinge_city_code2);
        geneticCodes1.add(gen1);
        geneticCodes2.add(gen2);
        Chromosome c1=new Chromosome(geneticCodes1),
                   c2=new Chromosome(geneticCodes2);
        //
        if (!historyRecord.containsKey(hinge_city_code1+son_no_hinge_city_code1)) {
            historyRecord.put(hinge_city_code1+son_no_hinge_city_code1, true);
            children.add(c1);
        }
        if (!historyRecord.containsKey(hinge_city_code2+son_no_hinge_city_code2)) {
            historyRecord.put(hinge_city_code2+son_no_hinge_city_code2, true);
            children.add(c2);
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
                    population.getPopulace().get(parentArr2[i])
                    );
        }
        return children;
    }
}
