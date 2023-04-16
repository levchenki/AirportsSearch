package com.uqii.services;

import java.util.Collection;
import java.util.SortedMap;

public interface Searcher {
	
	SortedMap<String, Collection<String>> search(String value);
}
