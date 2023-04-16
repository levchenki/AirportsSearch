package com.uqii.services.impl;

import com.uqii.exceptions.InvalidFilterException;
import com.uqii.models.MappedRow;
import com.uqii.services.Filterable;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter implements Filterable {
	private final Expression expression;
	
	public Filter(String filterPattern) {
		String preparedPattern = preparePattern(filterPattern);
		this.expression = getExpression(preparedPattern);
	}
	
	@Override
	public Boolean filter(MappedRow value) {
		try {
			return expression.getValue(value, Boolean.class);
		} catch (Exception e) {
			throw new InvalidFilterException(e);
		}
	}
	
	private Expression getExpression(String filterPattern) {
		SpelExpressionParser parser = new SpelExpressionParser();
		try {
			return parser.parseExpression(filterPattern.length() > 0 ? filterPattern : String.valueOf(true));
		} catch (Exception e) {
			throw new InvalidFilterException(e);
		}
	}
	
	private String preparePattern(String filterPattern) {
		if (filterPattern.contains("column[0]")) {
			throw new InvalidFilterException();
		}
		
		Deque<String> literals = extractLiterals(filterPattern);
		String replacedLiterals = replaceLiterals(filterPattern);
		String replacedSigns = replaceSigns(replacedLiterals);
		
		return restoreLiterals(replacedSigns, literals);
	}
	
	private Deque<String> extractLiterals(String filter) {
		final Pattern strLiteral = Pattern.compile("(?<=[=>< ]['\"]).*?(?=['\"])");
		Matcher matcher = strLiteral.matcher(filter);
		Deque<String> literals = new ArrayDeque<>();
		while (matcher.find()) {
			literals.addLast(matcher.group());
		}
		return literals;
	}
	
	private String replaceLiterals(String filter) {
		final Pattern strLiteral = Pattern.compile("(?<=[=>< ]['\"]).*?(?=['\"])");
		Matcher matcher = strLiteral.matcher(filter);
		StringBuilder builder = new StringBuilder();
		
		int lastIndex = 0;
		while (matcher.find()) {
			builder.append(filter, lastIndex, matcher.start());
			builder.append("$$");
			lastIndex = matcher.end();
		}
		builder.append(filter, lastIndex, filter.length());
		return builder.toString();
	}
	
	private String replaceSigns(String filter) {
		final Pattern whitespaces = Pattern.compile("\\s*");
		final Pattern equals = Pattern.compile("=");
		final Pattern notEquals = Pattern.compile("<>");
		final Pattern and = Pattern.compile("&");
		
		filter = whitespaces.matcher(filter).replaceAll("");
		filter = equals.matcher(filter).replaceAll("==");
		filter = notEquals.matcher(filter).replaceAll("!=");
		filter = and.matcher(filter).replaceAll("&&");
		return filter;
	}
	
	private String restoreLiterals(String filter, Deque<String> literals) {
		final Pattern restoredStrLiteral = Pattern.compile("\\$\\$");
		Matcher matcher = restoredStrLiteral.matcher(filter);
		StringBuilder builder = new StringBuilder();
		
		int lastIndex = 0;
		while (matcher.find()) {
			builder.append(filter, lastIndex, matcher.start());
			builder.append(literals.pop());
			lastIndex = matcher.end();
		}
		builder.append(filter, lastIndex, filter.length());
		return builder.toString();
	}
}
