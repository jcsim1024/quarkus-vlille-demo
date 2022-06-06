package org.acme.vlille.utils;

import org.bson.Document;

import java.util.List;

public class MongoHelper {
    public static List<Document> getListDocument(String jsRequest) {
        return Document.parse("{\"json\":" + jsRequest + "}").getList("json", Document.class);
    }
}
