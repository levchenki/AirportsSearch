package com.uqii.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;

public class Printer {
	
	private static int getSize(SortedMap<String, Collection<String>> map) {
		int count = 0;
		for (Map.Entry<String, Collection<String>> entry: map.entrySet()) {
			count += entry.getValue().size();
		}
		return count;
	}
	
	public void printMap(SortedMap<String, Collection<String>> map) {
		map.forEach((key, value) -> {
			for (var element: value) {
				int l = element.indexOf("\"") + 1;
				int r = element.indexOf("\"", l);
				String airportName = element.substring(l, r);
				System.out.printf("\"%s\"[%s]\n", airportName, element);
			}
		});
		int size = getSize(map);
		System.out.printf("Rows found: %d\n", size);
	}
	
	public void printMillis(String prefix, Instant start, Instant finish) {
		long millis = Duration.between(start, finish).toMillis();
		System.out.printf("%s: %dms\n", prefix, millis);
	}
	
	public void printMemory(String prefix) {
		long kBytes = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
		System.out.printf("Used memory %s: %dkB\n", prefix, kBytes);
	}
}
