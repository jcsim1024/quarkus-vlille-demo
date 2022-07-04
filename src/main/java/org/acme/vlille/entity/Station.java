package org.acme.vlille.entity;

import lombok.Data;

@Data
public class Station {

	/**
	 * IDx.
	 */
	private String nom;

	/**
	 * Number of bikes.
	 */
	private Integer nbvelosdispo;

}
