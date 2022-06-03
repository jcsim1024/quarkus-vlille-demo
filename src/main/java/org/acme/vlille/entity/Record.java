package org.acme.vlille.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
public class Record {
	private Station fields;


	@Override
	public String toString() {
		return "Record [fields=" + fields + "]";
	}
}
