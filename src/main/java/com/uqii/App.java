package com.uqii;

import com.google.common.collect.TreeMultimap;
import com.uqii.exceptions.InvalidFilterException;
import com.uqii.services.Filterable;
import com.uqii.services.Parser;
import com.uqii.services.Searcher;
import com.uqii.services.impl.CsvParser;
import com.uqii.services.impl.Filter;
import com.uqii.services.impl.SearcherImpl;
import com.uqii.utils.Printer;

import java.time.Instant;
import java.util.Collection;
import java.util.Scanner;
import java.util.SortedMap;

public class App {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Printer printer = new Printer();
		
		TreeMultimap<String, String> filteredMap;
		
		System.out.print("Enter the filter: ");
		
		while (true) try {
			String filterPattern = scanner.nextLine();
			Filterable filter = new Filter(filterPattern);
			Parser csvParser = new CsvParser(filter);
			
			Instant scanStart = Instant.now();
			filteredMap = csvParser.parse();
			Instant scanFinish = Instant.now();
			
			printer.printMillis("File scan time", scanStart, scanFinish);
			break;
		} catch (InvalidFilterException e) {
			System.out.print("Incorrect input.\nEnter the filter in the format \"column[1]<10 & column[5]='GKA'\": ");
		}
		
		Searcher searcher = new SearcherImpl(filteredMap);
		String searchValue;
		
		while (true) {
			System.out.print("Enter the beginning of the airport name: ");
			searchValue = scanner.nextLine().toLowerCase();
			
			if (searchValue.equals("!quit")) {
				break;
			}
			
			Instant searchStart = Instant.now();
			SortedMap<String, Collection<String>> searchedMap = searcher.search(searchValue);
			Instant searchFinish = Instant.now();
			
			printer.printMap(searchedMap);
			printer.printMillis("Search time", searchStart, searchFinish);
		}
	}
}
