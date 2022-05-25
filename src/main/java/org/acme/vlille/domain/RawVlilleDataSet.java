package org.acme.vlille.domain;

import lombok.Data;

import javax.json.JsonArray;
import java.util.List;

@Data
public class RawVlilleDataSet {

	private Integer nhits;

	private JsonArray records;

}