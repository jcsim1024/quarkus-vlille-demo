package org.acme.vlille.entity;


import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Multi;
import lombok.Data;
import org.acme.vlille.utils.MongoHelper;

@Data
@MongoEntity(collection="vlillecol")
public class VlilleDataSet  extends ReactivePanacheMongoEntity {

	private Record records;


	private static final String GET_LATEST_STATION_INFO_BY_NAME = """
			[
				 {$sort:
					  {'records.fields.nom':-1,'records.record_timestamp':1}
				 },
				
				 {$group:
					  {_id:'$records.fields.nom',
					  
					   result:{$first:'$$ROOT'}
					  }
	   
				 },
				 {$replaceRoot: {
						newRoot: "$result"
				   }
	   
				 }
			]
			""";

	public static Multi<VlilleDataSet> aggregateByStationNameOrderByTime() {
		var specifications = MongoHelper.getListDocument(GET_LATEST_STATION_INFO_BY_NAME);

		return VlilleDataSet
				.mongoCollection()
				.withDocumentClass(VlilleDataSet.class)
				.aggregate(specifications);
	}
}