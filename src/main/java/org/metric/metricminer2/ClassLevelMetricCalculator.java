package org.metric.metricminer2;

import br.com.metricminer2.domain.Commit;
import br.com.metricminer2.domain.Modification;
import br.com.metricminer2.persistence.PersistenceMechanism;
import br.com.metricminer2.scm.CommitVisitor;
import br.com.metricminer2.scm.SCMRepository;

public class ClassLevelMetricCalculator implements CommitVisitor {

	private ClassLevelMetricFactory factory;

	public ClassLevelMetricCalculator(ClassLevelMetricFactory factory) {
		this.factory = factory;
	}

	@Override
	public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
		
		ClassLevelMetric metric = factory.build();
		for(Modification change : commit.getModifications()) {
			if(metric.accepts(change.getNewPath())) {
				double value = metric.calculate(change.getSourceCode());
				writer.write(commit.getHash(), change.getNewPath(), value);
			}
		}
	}

	@Override
	public String name() {
		return "Class Level metric Processor for " + factory.getName();
	}

}
