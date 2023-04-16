package com.uqii.models;

public class MappedRow {
	
	public Object[] column = new Object[15];
	
	public MappedRow(String[] initialValue) {
		for (int i = 0; i < initialValue.length; i++) {
			initialValue[i] = initialValue[i].replaceAll("\"", "");
			if (initialValue[i].equals("\\N")) {
				initialValue[i] = null;
			}
		}
		
		column[1] = initialValue[0] != null ? Integer.parseInt(initialValue[0]) : null;
		column[2] = initialValue[1];
		column[3] = initialValue[2];
		column[4] = initialValue[3];
		column[5] = initialValue[4];
		column[6] = initialValue[5];
		column[7] = Double.parseDouble(initialValue[6]);
		column[8] = Double.parseDouble(initialValue[7]);
		column[9] = Integer.parseInt(initialValue[8]);
		column[10] = initialValue[9] != null ? Float.parseFloat(initialValue[9]) : null;
		column[11] = initialValue[10];
		column[12] = initialValue[11];
		column[13] = initialValue[12];
		column[14] = initialValue[13];
		
		if (!column[14].equals("OurAirports")) {
			throw new IllegalArgumentException("Error in string with id " + column[1]);
		}
	}
}
