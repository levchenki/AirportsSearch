package com.uqii.services.impl;

import com.google.common.collect.TreeMultimap;
import com.uqii.services.Searcher;

import java.util.Collection;
import java.util.SortedMap;

public class SearcherImpl implements Searcher {
	
	private final TreeMultimap<String, String> filteredMap;
	
	public SearcherImpl(TreeMultimap<String, String> filteredMap) {
		this.filteredMap = filteredMap;
	}
	
	@Override
	public SortedMap<String, Collection<String>> search(String value) {
		return filteredMap.asMap().subMap(value, value + Character.MAX_VALUE);
	}
}
