package org.acme.vlille.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Builder;
import lombok.Data;
import org.acme.vlille.domain.Record;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Active record pattern
 */
@MongoEntity(collection="VlilleDataSet")
@ApplicationScoped
public class VlilleDataSetEntity extends PanacheMongoEntity {
	public VlilleDataSetEntity() {

	}

	public OffsetDateTime retrivalTime;
	public String records;

	public OffsetDateTime getRetrivalTime() {
		return retrivalTime;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public void setRetrivalTime(OffsetDateTime retrivalTime) {
		this.retrivalTime = retrivalTime;
	}


}