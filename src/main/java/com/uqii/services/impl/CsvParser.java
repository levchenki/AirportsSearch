package com.uqii.services.impl;

import com.google.common.collect.TreeMultimap;
import com.uqii.App;
import com.uqii.models.MappedRow;
import com.uqii.services.Filterable;
import com.uqii.services.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvParser implements Parser {
	
	private final Filterable filter;
	
	public CsvParser(Filterable filter) {
		this.filter = filter;
	}
	
	@Override
	public TreeMultimap<String, String> parse() {
		InputStream inputStream = App.class.getResourceAsStream("/airports.csv");
		
		if (inputStream == null) {
			throw new RuntimeException("File not found: /airports.csv");
		}
		
		TreeMultimap<String, String> map = TreeMultimap.create();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			boolean isFileScanned = false;
			while (!isFileScanned) {
				isFileScanned = parseChunk(br, map);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return map;
	}
	
	private boolean parseChunk(BufferedReader br, TreeMultimap<String, String> map) throws IOException {
		final int CHUNK_SIZE = 500;
		String line;
		
		for (int i = 0; i < CHUNK_SIZE; i++) {
			if ((line = br.readLine()) == null) {
				return true;
			}
			String[] split = line.split("(?<=[\\d\"]|\\\\N),(?=[\\d\"\\\\-])");
			MappedRow mappedRow = new MappedRow(split);
			
			if (filter.filter(mappedRow)) {
				String key = split[1].toLowerCase().replace("\"", "");
				map.put(key, line);
			}
		}
		return false;
	}
}
