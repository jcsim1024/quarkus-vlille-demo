package org.acme.vlille.domain;

import lombok.Data;

@Data
public class Record {
	private Station fields;


	@Override
	public String toString() {
		return "Record [fields=" + fields + "]";
	}
}
