package com.uqii.services;

import com.google.common.collect.TreeMultimap;

public interface Parser {
	
	TreeMultimap<String, String> parse();
}
