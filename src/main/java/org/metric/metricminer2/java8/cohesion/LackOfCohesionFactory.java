package org.metric.metricminer2.java8.cohesion;

import org.metric.metricminer2.ClassLevelMetric;
import org.metric.metricminer2.ClassLevelMetricFactory;

public class LackOfCohesionFactory implements ClassLevelMetricFactory{

	@Override
	public ClassLevelMetric build() {
		return new LackOfCohesion();
	}

	@Override
	public String getName() {
		return "LCOM";
	}

}
