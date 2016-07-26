package org.metric.metricminer2.java8.cohesion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.misc.NotNull;
import org.metric.metricminer2.java8.methods.Method;

import br.com.metricminer2.parser.antlr.java8.Java8AntlrMethods;
import br.com.metricminer2.parser.antlr.java8.Java8BaseListener;
import br.com.metricminer2.parser.antlr.java8.Java8Parser;
import br.com.metricminer2.parser.antlr.java8.Java8Parser.MethodDeclarationContext;

public class MethodsAndAttributesListener extends Java8BaseListener {

	public List<Method> publicMethods;
	public List<Method> privateMethods;
	public List<Method> protectedMethods;
	public List<Method> defaultMethods;
	public List<Method> constructorMethods;
	
	public List<String> publicAttributes;
	public List<String> privateAttributes;
	public List<String> protectedAttributes;
	public List<String> defaultAttributes;
	
	private Stack<String> methodsInARow;
	private boolean ignoreGettersAndSetters;
	
	public MethodsAndAttributesListener() {
		this(false);
	}
	
	public MethodsAndAttributesListener(boolean ignoreGettersAndSetters) {
		this.ignoreGettersAndSetters = ignoreGettersAndSetters;
		
		constructorMethods = new ArrayList<Method>();
		publicMethods = new ArrayList<Method>();
		privateMethods = new ArrayList<Method>();
		protectedMethods = new ArrayList<Method>();
		defaultMethods = new ArrayList<Method>();
		
		publicAttributes = new ArrayList<String>();
		privateAttributes = new ArrayList<String>();
		protectedAttributes = new ArrayList<String>();
		defaultAttributes = new ArrayList<String>();

		methodsInARow = new Stack<String>();
		
		
	}

	@Override public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		
			methodsInARow.push(Java8AntlrMethods.fullMethodName(ctx));
			if(!methodIsInAInnerClass()) addMethod(ctx);
			
	}

	@Override public void exitMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
		methodsInARow.pop();
	}

	private void addMethod(MethodDeclarationContext ctx) {
		String name = Java8AntlrMethods.fullMethodName(ctx);
		if(ignoreGettersAndSetters && (name.startsWith("get") || name.startsWith("set"))) return;
		
		int lines = ctx.stop.getLine() - ctx.start.getLine();
		boolean added = false;
		for(int i = 0; i < ctx.methodModifier().size() && !added; i++) {
			String modifier = ctx.methodModifier().get(i).getText();
			if(isAccessModifier(modifier)) {
				addMethod(name, lines, modifier);
				added = true;
			}
		}
		
		if(!added) addMethod(name, lines, "default");
		
	}
	
	private boolean isAccessModifier(String modifier) {
		return Arrays.asList("public", "private", "protected").contains(modifier);
	}

	private void addMethod(String name, int lines, String modifier) {
		if("public".equals(modifier)) publicMethods.add(new Method(name, lines));
		if("private".equals(modifier)) privateMethods.add(new Method(name, lines));
		if("protected".equals(modifier)) protectedMethods.add(new Method(name, lines));
		if("default".equals(modifier)) defaultMethods.add(new Method(name, lines));
	}

	private boolean methodIsInAInnerClass() {
		return methodsInARow.size() > 1;
	}

	@Override public void enterConstructorDeclaration(Java8Parser.ConstructorDeclarationContext ctx) {
		int lines = ctx.stop.getLine() - ctx.start.getLine();
		constructorMethods.add(new Method(Java8AntlrMethods.fullMethodName(ctx), lines));
	}

	@Override public void enterFieldDeclaration(@NotNull Java8Parser.FieldDeclarationContext ctx) {
		String modifier;
		
		if(ctx.fieldModifier().size()>0) modifier = ctx.fieldModifier().get(0).getText();
		else modifier = "default";
		
		for(int i = 0; i < ctx.variableDeclaratorList().variableDeclarator().size(); i++){
			String name = ctx.variableDeclaratorList().variableDeclarator().get(i).getText();
			
			if("public".equals(modifier)) publicAttributes.add(name);
			if("private".equals(modifier)) privateAttributes.add(name);
			if("protected".equals(modifier)) protectedAttributes.add(name);
			if("default".equals(modifier)) defaultAttributes.add(name);
		}
	}
	
	public List<Method> getMethods() {
		List<Method> methods = new ArrayList<Method>();
		methods.addAll(protectedMethods);
		methods.addAll(publicMethods);
		methods.addAll(privateMethods);
		methods.addAll(defaultMethods);
		methods.addAll(constructorMethods);

		return methods;
	}
	

	public List<String> getAttributes() {
		List<String> attributes = new ArrayList<String>();
		attributes.addAll(protectedAttributes);
		attributes.addAll(publicAttributes);
		attributes.addAll(privateAttributes);
		attributes.addAll(defaultAttributes);

		return attributes;
	}

	public List<Method> getPublicMethods() {
		return publicMethods;
	}

	public List<Method> getPrivateMethods() {
		return privateMethods;
	}

	public List<Method> getProtectedMethods() {
		return protectedMethods;
	}

	public List<Method> getDefaultMethods() {
		return defaultMethods;
	}

	public List<Method> getConstructorMethods() {
		return constructorMethods;
	}

	public List<String> getPublicAttributes() {
		return publicAttributes;
	}

	public List<String> getPrivateAttributes() {
		return privateAttributes;
	}

	public List<String> getProtectedAttributes() {
		return protectedAttributes;
	}

	public List<String> getDefaultAttributes() {
		return defaultAttributes;
	}

	

}
