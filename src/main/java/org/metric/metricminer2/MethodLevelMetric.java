package org.metric.metricminer2;

import java.util.Map;

public interface MethodLevelMetric extends CodeMetric {

	Map<String, Double> calculate(String sourceCode);

}
