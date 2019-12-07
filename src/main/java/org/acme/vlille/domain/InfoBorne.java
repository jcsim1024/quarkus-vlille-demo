package org.acme.vlille.domain;

public class InfoBorne {
	private Station fields;

	public Station getFields() {
		return fields;
	}

	public void setFields(final Station fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "InfoBorne [fields=" + fields + "]";
	}
}
