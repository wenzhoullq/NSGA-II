/*
 * MIT License
 *
 * Copyright (c) 2019 Debabrata Acharya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.debacharya.nsgaii.plugin;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.CityAllele;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.loadData;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.debacharya.nsgaii.loadData.*;


public class DefaultPluginProvider {

	public static void Create_city(){
		float[] city_all_transport=new float[citynum];
		int[] city_all_distance=new int[citynum];
		//统计城市i的出入流量,与其他城市距离的总和
		for(int i=0;i<citynum;i++){
			for(int j=0;j<citynum;j++){
				city_all_transport[i]+=city_transport[i][j];
				city_all_distance[i]+=city_distance[i][j];
			}
//                System.out.println(city_all_transport[i]+" "+city_all_distance[i]);
		}
		float sum_transport=0;
		float sum_distance=0;
		//统计总流量
		for(float temp:city_all_transport) sum_transport+=temp;
//            System.out.println(sum_transport);
		//统计总距离
		for(int temp:city_all_distance) sum_distance+=temp;
//            System.out.println(sum_distance);
		//备选枢纽i的旅客需求量占网络总旅客需求量的比例zx
		float[] zx=new float[citynum];
		for(int i=0;i<citynum;i++) {
			zx[i]=city_all_transport[i]/sum_transport;
//                System.out.println(zx[i]+" "+i);
		}
		//备选枢纽i的旅客需求量占网络总距离的比例zx
		float[] zd=new float[citynum];
		//计算流量/距离 zx_zd
		for(int i=0;i<citynum;i++){
			zd[i]=city_all_distance[i]/sum_distance;
			zx_zd[i]=zx[i]/zd[i];
//                System.out.println(zd[i]);
//                System.out.println(zx_zd[i]);
		}
		//选取hinge_citynum个中枢城市作为备选基因表
		Queue<Integer> q=new PriorityQueue<>((a, b)->Float.compare(zx_zd[b],zx_zd[a]));
		for(int i=0;i<citynum;i++) q.add(i);
		String str=new String();
		boolean[] visit=new boolean[citynum];
		String[] hinge_city=new String[hinge_citynum];
		for(int i=0;i<hinge_citynum;i++) {
			int temp=q.poll();
			hinge_map1.put(i,temp);
			hinge_map2.put(temp,i);
			str+=temp+"_";
			hinge_city[i]=String.valueOf(temp);
			visit[temp]=true;
			//System.out.println(temp);
		}
		loadData.hinge_city=new String(str.substring(0,str.length()-1));
//            System.out.println(loadData.hinge_city);
		//对中枢机场的选择概率进行归一化处理
		float sum=0;
		for(int i=0;i<hinge_citynum;i++){
			//System.out.println(hinge_city[i]+" "+hinge_map.get(hinge_city[i]));
			sum+=zx_zd[Integer.valueOf(hinge_city[i])];
		}
		for(int i=0;i<hinge_citynum;i++){
			hinge_city_zx_zd_choose[i]=zx_zd[Integer.valueOf(hinge_city[i])]/sum;
		}
//            System.out.println(sum);
		for(int i=0;i<hinge_citynum;i++){
			int index=Integer.parseInt(hinge_city[i]);
//			System.out.println(index);
			zx_zd[hinge_map2.get(index)]=zx_zd[hinge_map2.get(index)]/sum;
//                System.out.println(zx_zd[hinge_map.get(hinge_city[i])]);
		}
		// 对权重的zx_zd_quanzhong进行初始化
		for(int i=1;i<=hinge_citynum;i++){
			hinge_city_zx_zd_quanzhong[i]=hinge_city_zx_zd_quanzhong[i-1]+hinge_city_zx_zd_choose[i-1];
		}
		//初始化非中枢城市基因表
		int index=0;
		str="";
		for(int i=0;i<citynum;i++) {
			if(visit[i]) continue;
//			System.out.println(i+" "+index);
			no_hinge_map1.put(index,i);
			no_hinge_map2.put(i,index++);
			str+=String.valueOf(i)+"_";
		}
//            System.out.println(str);
		loadData.no_hinge_city=new String(str.substring(0,str.length()-1));
	}
	public static  String Create_hinge_city_code(){
		char[] hinge_city_code=new char[hinge_citynum];
		Arrays.fill(hinge_city_code,'0');
		Random random=new Random();
		//产生一个随机数，与zx/zd在总的（zx/zd）的比值为概率p
		for(int i=0;i<hinge_citynum;i++) {
			int left=1,right=hinge_citynum;
			double target=random.nextDouble();
//			System.out.println(target);
			while(left<right){
				int mid=(left+right)/2;
//				System.out.println(hinge_city_zx_zd_quanzhong[mid]);
				if(hinge_city_zx_zd_quanzhong[mid]<target) left=mid+1;
				else right=mid;
			}
			hinge_city_code[left-1]='1';
		}
		return new String(hinge_city_code);
	}
	public static String Create_no_hinge_city_code(String hinge_city_code){
//        System.out.println(hinge_city_code);
		String ans="";
		//通过与该中枢的流量和距离的比值，来确定选择的概率，zx是距离的流量比值，zd是距离的比值
		float[][] zx=new float[no_hinge_citynum][hinge_citynum];
		float[][] zd=new float[no_hinge_citynum][hinge_citynum];
		float[][] zx_zd=new float[no_hinge_citynum][hinge_citynum];
		Random random=new Random();
		for(int i=0;i<no_hinge_citynum;i++){
			int i_sumdistance=0;
			float i_sumtransport=0;
			//计算zx_zd
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)=='0') continue;
//                System.out.println(i+" "+j+" "+no_hinge_map1.get(i)+" "+hinge_map1.get(j));
				i_sumdistance+=city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)];
				i_sumtransport+=city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]
						+city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)];
			}
//            System.out.println(i_sumdistance+" "+i_sumtransport);
			float sum=0;
			int city_choose_num=0;
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)=='0') continue;
//                System.out.println((city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]+city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)])/i_sumtransport);
				zx[i][j]=(city_transport[no_hinge_map1.get(i)][hinge_map1.get(j)]+city_transport[hinge_map1.get(j)][no_hinge_map1.get(i)])/i_sumtransport;
//                System.out.println((float)city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)]/i_sumdistance+"sadsad");
				zd[i][j]=(float) (city_distance[no_hinge_map1.get(i)][hinge_map1.get(j)])/i_sumdistance;
				zx_zd[i][j]=zx[i][j]/zd[i][j];
//                System.out.println(zx_zd[i][j]);
				sum+=zx_zd[i][j];
				city_choose_num++;
			}
			//zx_zd归一化和制作被选择中枢城市表
			double[] zx_zd_city=new double[city_choose_num];
			HashMap<Integer,Integer> m=new HashMap<>();//被选择的枢纽城市的映射表
			int index=0;
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)=='0') continue;
				zx_zd[i][j]=zx_zd[i][j]/sum;
				zx_zd_city[index]=zx_zd[i][j];
				m.put(index++,j);
//                System.out.println(zx_zd[i][j]);
			}
			//根据权重选择中枢城市
			double[] no_hinge_city_zx_zd_quanzhong=new double[city_choose_num+1];
			for(int j=1;j<=city_choose_num;j++){
				no_hinge_city_zx_zd_quanzhong[j]=no_hinge_city_zx_zd_quanzhong[j-1]+zx_zd_city[j-1];
			}
			int left=1,right=city_choose_num;
			double target=random.nextDouble();
			while(left<right){
				int mid=(left+right)/2;
				if(no_hinge_city_zx_zd_quanzhong[mid]<target) left=mid+1;
				else right=mid;
			}
			ans+=hinge_map1.get(m.get(left-1))+"_";
		}
		return ans.substring(0,ans.length()-1);
	}

	public static PopulationProducer InitPopulationProducer() {
		// 接口里的参数是没用到的
		return (populationSize, chromosomeLength, geneticCodeProducer, fitnessCalculator) -> {
			// 初始个体
			List<CityAllele> cityAlleles = new ArrayList<>(start_num);
			for (int i = 0; i < start_num; i++) {
				String hinge_code=Create_hinge_city_code();
				String no_hinge_code=Create_no_hinge_city_code(hinge_code);
				while(historyRecord.contains(hinge_code+no_hinge_code)) {
					hinge_code=Create_hinge_city_code();
					no_hinge_code=Create_no_hinge_city_code(hinge_code);
				}
				historyRecord.add(hinge_code+no_hinge_code);
				cityAlleles.add(new CityAllele(hinge_code,no_hinge_code));
			}
			Chromosome initIndividual = new Chromosome(new Chromosome(cityAlleles));
			List<Chromosome> populace = new ArrayList<Chromosome>() {{ add(initIndividual); }};
			return new Population(populace);
		};
	}
	public static PopulationProducer defaultPopulationProducer() {
		return (populationSize, chromosomeLength, geneticCodeProducer, fitnessCalculator) -> {

			List<Chromosome> populace = new ArrayList<>();

			for(int i = 0; i < populationSize; i++)
				populace.add(
					new Chromosome(
						geneticCodeProducer.produce(chromosomeLength)
					)
				);

			return new Population(populace);
		};
	}
		//产生子代的方法
	public static ChildPopulationProducer childrenProducer(float mutationProb) {
		return (parentPopulation, crossover, mutation, populationSize) -> {
			List<Chromosome> populace;
			// 交叉产生的新后代
			populace = crossover.perform(parentPopulation);

			// 变异产生的新后代
			int mutateNum = (int) (parentPopulation.getPopulace().size() * mutationProb);
			mutateNum = Math.max(mutateNum, 1);
			HashSet<Integer> mutateParents = new HashSet<>();
			for (int i = 0; i < mutateNum; i++) {
				int id;
				do {
					id = ThreadLocalRandom.current().nextInt(0, parentPopulation.getPopulace().size());
				}  while (mutateParents.contains(id));
				mutateParents.add(id);
			}

			for (int id : mutateParents) {
				Chromosome child = mutation.perform(parentPopulation.get(id));
				List<AbstractAllele> genelist=child.getGeneticCode();
				CityAllele cityAllele=(CityAllele)genelist.get(0);
				String[] codes=cityAllele.getGene();
				if (!historyRecord.contains(child)) {
					historyRecord.add(codes[0]+codes[1]);
					populace.add(child);
				}
			}

			return new Population(populace);
		};
	}
	public static ChildPopulationProducer defaultChildPopulationProducer() {
		return (parentPopulation, crossover, mutation, populationSize) -> {

			List<Chromosome> populace = new ArrayList<>();

			while(populace.size() < populationSize)
				if((populationSize - populace.size()) == 1)
					populace.add(
						mutation.perform(
							Service.crowdedBinaryTournamentSelection(parentPopulation)
						)
					);
				else
					for(Chromosome chromosome : crossover.perform(parentPopulation))
						populace.add(mutation.perform(chromosome));

			return new Population(populace);
		};
	}


}
