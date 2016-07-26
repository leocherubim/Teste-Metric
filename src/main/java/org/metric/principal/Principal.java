package org.metric.principal;

import org.metric.study.MetricStudy;

import br.com.metricminer2.MetricMiner2;


public class Principal {

	public static void main(String[] args) {
		
		new MetricMiner2().start(new MetricStudy());
		
	}

}
