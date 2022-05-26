package org.acme.vlille.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;

/**
 * Active record pattern
 */
@MongoEntity(collection="VlilleDataSet")
@ApplicationScoped
@Data
public class VlilleDataSetEntity extends PanacheMongoEntity {
	public VlilleDataSetEntity() {

	}

	OffsetDateTime retrivalTime;
	JsonObject records;

}