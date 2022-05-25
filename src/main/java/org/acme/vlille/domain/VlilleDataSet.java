package org.acme.vlille.domain;

import lombok.Data;

import java.util.List;

@Data
public class VlilleDataSet {

	private Integer nhits;

	private List<Record> records;

}