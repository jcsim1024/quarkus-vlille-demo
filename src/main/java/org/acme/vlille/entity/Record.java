package org.acme.vlille.entity;

import lombok.Data;

@Data
public class Record {
	private Station fields;


	@Override
	public String toString() {
		return "Record [fields=" + fields + "]";
	}
}
