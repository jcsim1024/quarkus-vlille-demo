package org.acme.vlille.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import org.bson.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;

/**
 * Active record pattern
 */
@MongoEntity(collection="vlillecol")
@ApplicationScoped
@Data
public class RawVlilleDataSetEntity extends ReactivePanacheMongoEntity {
	public RawVlilleDataSetEntity() {

	}

	OffsetDateTime retrivalTime;
	JsonObject records;

}