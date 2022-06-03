package org.acme.vlille.entity;

//import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
//import org.acme.vlille.entity.Record;

import java.lang.annotation.Documented;
import java.util.List;

@Data
public class VlilleDataSet {

	private List<Record> records;

}