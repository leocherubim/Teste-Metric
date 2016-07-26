package org.metric.study;

import org.metric.metricminer2.ClassLevelMetricCalculator;
import org.metric.metricminer2.java8.cohesion.LackOfCohesionFactory;

import br.com.metricminer2.RepositoryMining;
import br.com.metricminer2.Study;
import br.com.metricminer2.persistence.csv.CSVFile;
import br.com.metricminer2.scm.GitRepository;
import br.com.metricminer2.scm.commitrange.Commits;

public class MetricStudy implements Study {

	private String projetoGit = "C:\\Users\\Leonardo Cherubini\\workspaceEE\\TutorialFrameworks";
	private String resultadoCSV = "C:\\Users\\Leonardo Cherubini\\Documents\\INPE\\devs.CSV";
	
	@Override
	public void execute() {
		new RepositoryMining()
		.in(GitRepository.singleProject(this.projetoGit))
		.through(Commits.all())
		.process(new ClassLevelMetricCalculator(new LackOfCohesionFactory()), 
				new CSVFile(this.resultadoCSV))
		.mine();		
	}

}
