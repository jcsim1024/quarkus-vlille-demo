package org.acme.vlille.domain;

public class Record {
	private Station fields;

	public Station getFields() {
		return fields;
	}

	public void setFields(final Station fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Record [fields=" + fields + "]";
	}
}
