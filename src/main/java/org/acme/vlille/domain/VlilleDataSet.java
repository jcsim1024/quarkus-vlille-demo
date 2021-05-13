package org.acme.vlille.domain;

import java.util.List;

public class VlilleDataSet {

	private Integer nhits;

	private List<Record> records;

	public Integer getNhits() {
		return nhits;
	}

	public void setNhits(final Integer hits) {
		nhits = hits;
	}

	@Override
	public String toString() {
		return "VlilleDataSet [nhits=" + nhits + ", records=" + records + "]";
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(final List<Record> records) {
		this.records = records;
	}

}