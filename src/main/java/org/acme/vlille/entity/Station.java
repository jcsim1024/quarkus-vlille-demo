package org.acme.vlille.entity;

import liquibase.pro.packaged.I;
import lombok.Data;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import javax.inject.Inject;

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
