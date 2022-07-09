import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.crossover.FatherCrossover;
import com.debacharya.nsgaii.mutation.SingleMutation;
import com.debacharya.nsgaii.objectivefunction.ObjectiveProvider;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.termination.TerminatingCriterionProvider;

import static com.debacharya.nsgaii.loadData.*;

public class testNSGAii {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        long start = System.currentTimeMillis();
        // 代码文件粒度重构，初始化种群
        configuration.setPopulationProducer(DefaultPluginProvider.fileInitPopulationProducer());
        configuration.objectives = ObjectiveProvider.provideMin_co2AndMin_fee();
        //交叉操作
        configuration.setChromosomeLength(citynum);//染色体的长度是城市的数量，是中枢城市+非中枢城市的长度
        configuration.setCrossover(new FatherCrossover(null, crossoverProb));
        // 变异操作
        configuration.setMutation(new SingleMutation(mutationProb));
        // 子代更新方法
        configuration.setChildPopulationProducer(DefaultPluginProvider.childrenProducer(mutationProb));
        // 最大代数，与终止条件有关
        configuration.setGenerations(MaxGeneration);
        // 原意是种群的固定数量，但在本研究中，种群数量是从1开始的，次值应该是种群上限
        configuration.setPopulationSize(MaxPopulationSize);
        // 遗传终止方法
        configuration.setTerminatingCriterion(TerminatingCriterionProvider.refactorTerminatingCriterion(
                MaxPopulationSize, MaxRecord));
        long end = System.currentTimeMillis();
        System.out.println("任务总耗时：" + ((end - start) / 1000.0));
    }
}
