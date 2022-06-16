package admin;

import org.elasticsearch.xcontent.NamedXContentRegistry;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.client.RestClients.ElasticsearchRestClient;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

public class MappingBuilder {

    private final ElasticsearchOperations ops;

    public MappingBuilder() {
        ClientConfiguration clientConfiguration = ClientConfiguration
                .builder().connectedToLocalhost()
                .build();
        try (ElasticsearchRestClient client = RestClients.create(clientConfiguration)) {
            this.ops = new ElasticsearchRestTemplate(client.rest());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String createMapping(Class<?> clazz) {
        return ops.indexOps(clazz).createMapping().toJson();
    }

}
