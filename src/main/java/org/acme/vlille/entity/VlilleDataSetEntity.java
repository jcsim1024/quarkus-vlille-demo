package org.acme.vlille.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.acme.vlille.domain.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.OffsetDateTime;
import java.util.List;


@MongoEntity(collection="VlilleDataSet")
@ApplicationScoped
public class VlilleDataSetEntity extends PanacheMongoEntity {

	private OffsetDateTime retrivalTime;

	private List<Record> records;

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}


	public OffsetDateTime getRetrivalTime() {
		return retrivalTime;
	}

	public void setRetrivalTime(OffsetDateTime retrivalTime) {
		this.retrivalTime = retrivalTime;
	}
}