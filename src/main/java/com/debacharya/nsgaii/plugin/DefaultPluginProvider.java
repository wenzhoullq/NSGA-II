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
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.loadData;

import java.math.BigDecimal;
import java.util.*;

import static com.debacharya.nsgaii.loadData.*;


public class DefaultPluginProvider {
	//

	public static void Create_city(){
		int[][] transport=(int[][])city_transport.clone();
		int[][] distance=(int[][])city_distance.clone();
		int[] city_all_transport=new int[citynum];
		int[] city_all_distance=new int[citynum];
		//统计城市i的出入流量,与其他城市距离的总和
		for(int i=0;i<citynum;i++){
			for(int j=0;j<citynum;j++){
				city_all_transport[i]+=transport[i][j]+transport[j][i];
				city_all_distance[i]+=distance[i][j];
			}
		}
		int sum_transport=0;
		int sum_distance=0;
		//统计总流量
		for(int temp:city_all_transport) sum_transport+=temp;
		//统计总距离
		for(int temp:city_all_distance) sum_distance+=temp;
		//备选枢纽i的旅客需求量占网络总旅客需求量的比例zx
		double[] zx=new double[citynum];
		for(int i=0;i<citynum;i++) zx[i]=city_all_transport[i]/sum_transport;
		//备选枢纽i的旅客需求量占网络总距离的比例zx
		double[] zd=new double[citynum];
		//计算流量/距离 zx_zd
		for(int i=0;i<citynum;i++){
			zd[i]=city_all_distance[i]/sum_distance;
			zx_zd[i]=zx[i]/zd[i];
		}
		//选取hinge_citynum个中枢城市作为备选基因表
		Queue<Integer> q=new PriorityQueue<>((a,b)->new BigDecimal(zx_zd[b]).compareTo(new BigDecimal(zx_zd[a])));
		for(int i=0;i<citynum;i++) q.add(i);
		char[] hinge_city=new char[hinge_citynum];
		boolean[] visit=new boolean[hinge_citynum];
		for(int i=0;i<hinge_citynum;i++) {
			int temp=q.poll();
			hinge_map.put((char)(temp+'0'),i);
			hinge_city[i]=(char)(temp+'0');
			visit[temp]=true;
		}
		loadData.hinge_city=new String(hinge_city);
		//对中枢机场的选择概率进行归一化处理
		double sum=0;
		for(int i=0;i<hinge_citynum;i++){
			sum+=hinge_city_zx_zd_choose[hinge_city[i]-'0'];
		}
		for(int i=0;i<hinge_citynum;i++){
			hinge_city_zx_zd_choose[hinge_city[i]-'0']=hinge_city_zx_zd_choose[hinge_city[i]-'0']/sum;
		}
		// 对权重的zx_zd_quanzhong进行初始化
		for(int i=1;i<=hinge_citynum;i++){
			hinge_city_zx_zd_quanzhong[i]=hinge_city_zx_zd_quanzhong[i-1]+hinge_city_zx_zd_choose[i-1];
		}
		//初始化非中枢城市基因表
		char[] no_hinge_city=new char[no_hinge_citynum];
		int index=0;
		for(int i=0;i<citynum;i++) {
			if(visit[i]) continue;
			no_hinge_map.put((char)(i+'0'),index);
			no_hinge_city[index++]=(char)(i+'0');
		}
		loadData.no_hinge_city=new String(no_hinge_city);
	}
	public static  String Create_hinge_city_code(){
		char[] hinge_city_code=new char[hinge_citynum];
		Arrays.fill(hinge_city_code,'0');
		Random random=new Random();
		//产生一个随机数，与zx/zd在总的（zx/zd）的比值为概率p
		for(int i=0;i<hinge_citynum;i++) {
			int left=1,right=hinge_citynum;
			double target=random.nextDouble();
			while(left<right){
				int mid=(left+right)/2;
				if(hinge_city_zx_zd_quanzhong[mid]<target) left=mid+1;
				else right=mid;
			}
			hinge_city_code[left-1]='1';
		}
		return new String(hinge_city_code);
	}
	public static String Create_no_hinge_city_code(String hinge_city_code){
		char[] no_hinge_city_code=new char[no_hinge_citynum];
		Arrays.fill(no_hinge_city_code,'0');
		//通过与该中枢的流量和距离的比值，来确定选择的概率，zx是距离的流量比值，zd是距离的比值
		double[][] zx=new double[no_hinge_citynum][hinge_citynum];
		double[][] zd=new double[no_hinge_citynum][hinge_citynum];
		double[][] zx_zd=new double[no_hinge_citynum][hinge_citynum];
		Random random=new Random();
		for(int i=0;i<no_hinge_citynum;i++){
			int i_sumdistance=0,i_sumtransport=0;
			//计算zx_zd
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)==0) continue;
				i_sumdistance+=city_distance[i][j]+city_distance[j][i];
				i_sumtransport+=city_transport[i][j]+city_transport[j][i];
			}
			double sum=0;
			int city_choose_num=0;//设置为中枢城市且编码为1的数量
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)==0) continue;
				zx[i][j]=(city_distance[i][j]+city_distance[j][i])/i_sumdistance;
				zd[i][j]=(city_transport[i][j]+city_transport[j][i])/i_sumtransport;
				zx_zd[i][j]=zx[i][j]/zd[i][j];
				sum+=zx_zd[i][j];
				city_choose_num++;
			}
			//zx_zd归一化和制作中枢城市表
			HashMap<Character,Integer> m=new HashMap<>();//根据城市名找下标
			char[] city_choose=new char[city_choose_num];
			double[] zx_zd_city=new double[city_choose_num];
			int index=0;
			for(int j=0;j<hinge_citynum;j++){
				if(hinge_city_code.charAt(j)==0) continue;
				char c=hinge_city.charAt(j);
				m.put(c,j);
				city_choose[index]=c;
				zx_zd[i][j]=zx_zd[i][j]/sum;
				zx_zd_city[index++]=zx_zd[i][j];
			}
			//根据权重选择中枢城市
			double[] no_hinge_city_zx_zd_quanzhong=new double[city_choose_num+1];
			for(int j=1;j<=city_choose_num;j++){
				no_hinge_city_zx_zd_quanzhong[i]=no_hinge_city_zx_zd_quanzhong[j-1]+zx_zd_city[j-1];
			}
			int left=1,right=city_choose_num;
			double target=random.nextDouble();
			while(left<right){
				int mid=(left+right)/2;
				if(no_hinge_city_zx_zd_quanzhong[mid]<target) left=mid+1;
				else right=mid;
			}
			no_hinge_city_code[m.get(city_choose[left-1])]='1';
		}
		return new String(no_hinge_city_code);
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
