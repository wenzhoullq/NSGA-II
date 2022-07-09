package com.debacharya.nsgaii.termination;

import static com.debacharya.nsgaii.loadData.historyRecord;

public class TerminatingCriterionProvider {

	/*
	 * The most commonly used stopping criterion in Evolutionary Multi-objective Algorithms is an a priori fixed number of generations
	 * (or evaluations).
	 */
	public static TerminatingCriterion fixedTerminatingCriterion() {
		return (population, generationCount, maxGenerations) -> (generationCount <= maxGenerations);
	}
	public static TerminatingCriterion refactorTerminatingCriterion(int maxPopulationSize, int maxRecordSize) {
		return ((parentPopulation, generationCount, maxGenerations) -> {
			return generationCount <= maxGenerations && historyRecord.size() <= maxRecordSize;
		});
	}
}
