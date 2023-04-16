package com.uqii.services;

import com.uqii.models.MappedRow;

public interface Filterable {
	
	Boolean filter(MappedRow value);
}
