import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.crossover.FatherCrossover;
import com.debacharya.nsgaii.mutation.SingleMutation;
import com.debacharya.nsgaii.objectivefunction.ObjectiveProvider;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;
import com.debacharya.nsgaii.termination.TerminatingCriterionProvider;

import java.text.DecimalFormat;

import static com.debacharya.nsgaii.loadData.*;

public class testNSGAii {
    static DecimalFormat fourDF = new DecimalFormat("#.0000");
    public static String getFourBitsDoubleString(double data) {
        return fourDF.format(data);
    }
    public static void main(String[] args) {
        init();
        Configuration configuration = new Configuration(ObjectiveProvider.provideMin_co2AndMin_fee());
        long start = System.currentTimeMillis();
        // 初始化种群
        configuration.setPopulationProducer(DefaultPluginProvider.InitPopulationProducer());
        //交叉操作
        configuration.setChromosomeLength(citynum);//染色体的长度是城市的数量，是中枢城市+非中枢城市的长度
        configuration.setCrossover(new FatherCrossover(null, crossoverProb));
        // 变异操作
        configuration.setMutation(new SingleMutation(mutationProb));
        // 子代更新方法
        configuration.setChildPopulationProducer(DefaultPluginProvider.childrenProducer(mutationProb));
        // 最大代数，与终止条件有关
        configuration.setGenerations(MaxGeneration);
        // 原意是种群的固定数量，但在本研究中，种群数量是从2开始的，次值应该是种群上限
        configuration.setPopulationSize(MaxPopulationSize);
        // 遗传终止方法
        configuration.setTerminatingCriterion(TerminatingCriterionProvider.refactorTerminatingCriterion(
                MaxPopulationSize, MaxRecord));
        NSGA2 Nsga2 = new NSGA2(configuration);
        for (int i = 1; i <= experimentTimes; i++) {
            System.out.println("experiment - " + i + " -------------------------------------------------------------------");
            long start1 = System.currentTimeMillis();
            Nsga2.run();
            long end1 = System.currentTimeMillis();

            System.out.println("运行时间：" + getFourBitsDoubleString((end1 - start1) / 1000.0) + " s");
        }
        long end = System.currentTimeMillis();
        System.out.println("任务总耗时：" + getFourBitsDoubleString((end - start) / 1000.0));
    }
}
