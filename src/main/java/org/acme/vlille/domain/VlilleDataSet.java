package org.acme.vlille.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VlilleDataSet {

	private Integer nhits;

	private List<InfoBorne> records;

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

	public List<InfoBorne> getRecords() {
		return records;
	}

	public void setRecords(final List<InfoBorne> records) {
		this.records = records;
	}

}