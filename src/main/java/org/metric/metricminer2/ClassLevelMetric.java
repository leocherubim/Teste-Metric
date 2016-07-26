package org.metric.metricminer2;

public interface ClassLevelMetric extends CodeMetric {

	double calculate(String sourceCode);
	
}
