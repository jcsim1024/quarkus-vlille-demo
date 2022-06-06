package org.acme.vlille.vlille.api;

import lombok.Data;

import javax.json.JsonArray;

@Data
public class RawVlilleDataSetDTO {

	private Integer nhits;

	private JsonArray records;

}