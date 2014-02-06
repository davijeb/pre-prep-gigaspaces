package com.preperation.ubs;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.StringHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder;

/**
 * Created by Home PC on 05/02/14.
 */
public class MarkLogicMain {

    public static void main(String[] args) {

        // Create a thread-safe connection to the database.
        DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8003, "admin", "password", DatabaseClientFactory.Authentication.DIGEST);

        // Create or update a JSON document
        JSONDocumentManager doc = client.newJSONDocumentManager();
        doc.write("hello.json", new StringHandle("{\"recipient\": \"world\", \"message\": \"Hello, world!\"}"));
        doc.write("car.json", new StringHandle("{\"make\": \"Vanilla\", \"size\": \"2 litre\"}"));
        doc.write("/icecream", new StringHandle("{\"make\": \"Vanilla\", \"type\": \"Cone\"}"));

        //doc.delete("/icecream");

        // Build up a query and run it
        QueryManager query = client.newQueryManager();
        StructuredQueryBuilder b = query.newStructuredQueryBuilder();
        SearchHandle results = query.search(b.and(b.term("hello"), b.value(b.jsonKey("recipient"), "world")), new SearchHandle());

        SearchHandle handle = new SearchHandle();

        StringQueryDefinition sqd = query.newStringDefinition();
        sqd.setCriteria("Vanilla");
        query.search(sqd, handle);

        System.out.println("Found " + handle.getMatchResults().length);

        // Loop through the results and get each document by its unique ID
        for (MatchDocumentSummary summary : results.getMatchResults()) {
            System.out.println(doc.read(summary.getUri(), new StringHandle()).toString());
        }

    }
}
