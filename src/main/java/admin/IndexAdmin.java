/*
 * Copyright 2018 Sergej Schaefer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package admin;

import client.ElsaClient;
import endpoints.Endpoint;
import exceptions.RequestExceptionHandler;
import helpers.IndexName;
import helpers.ModelClass;
import helpers.RequestBody;
import model.ElsaModel;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import responses.ConfirmationResponse;
import responses.ElsaResponse;
import responses.ResponseFactory;
import statics.ElsaStatics;
import statics.Messages.ExceptionMsg;
import statics.Method;
import statics.UrlParams;

public class IndexAdmin {

    private final ElsaClient elsa;

    public IndexAdmin(final ElsaClient elsa) {
        this.elsa = elsa;
    }


    // ------------------------------------------------------------------------------------------ //
    // CREATE INDEX
    // ------------------------------------------------------------------------------------------ //

    public ElsaResponse<CreateIndexResponse> createIndex(final Class<? extends ElsaModel> modelClass, final RequestExceptionHandler handler) {
        try {
            final ElsaModel model = ModelClass.createEmpty(modelClass);
            final XContentBuilder mapping = MappingBuilder.buildMapping(modelClass, ElsaStatics.DUMMY_TYPE, ElsaStatics.DEFAULT_ID_FIELD_NAME, "");
            final CreateIndexRequest request = new CreateIndexRequest();
            request.index(model.getIndexConfig().getIndexName());
            final Settings settings = Settings.builder()
                    .put("index.number_of_shards", model.getIndexConfig().getShards())
                    .put("index.number_of_replicas", model.getIndexConfig().getReplicas())
                    .put("index.refresh_interval", model.getIndexConfig().getRefreshInterval().toString())
                    .build();
            request.settings(settings);
            request.mapping(ElsaStatics.DUMMY_TYPE, mapping);
            return ElsaResponse.of(this.elsa.client.indices().create(request));
        } catch (final Exception e) {
            handler.process(e, ExceptionMsg.REQUEST_FAILED);
            return ElsaResponse.of(e);
        }
    }

    public ElsaResponse<CreateIndexResponse> createIndex(final Class<? extends ElsaModel> modelClass) {
        return this.createIndex(modelClass, this.elsa.getRequestExceptionHandler());
    }


    // ------------------------------------------------------------------------------------------ //
    // UPDATE MAPPING
    // ------------------------------------------------------------------------------------------ //

    public ElsaResponse<ConfirmationResponse> updateMapping(final Class<? extends ElsaModel> modelClass, final RequestExceptionHandler handler) {
        try {
            final String indexName = IndexName.of(modelClass);
            final XContentBuilder xContentBuilder = MappingBuilder.buildMapping(modelClass, "_doc", "", "");
            final Response response = this.elsa.client.getLowLevelClient().performRequest(
                    Method.PUT,
                    Endpoint.INDEX_MAPPING.update(indexName),
                    UrlParams.NONE,
                    RequestBody.asJson(xContentBuilder));
            return ElsaResponse.of(ResponseFactory.createConfirmationResponse(response));
        } catch (final Exception e) {
            handler.process(e, ExceptionMsg.REQUEST_FAILED);
            return ElsaResponse.of(e);
        }
    }

    public ElsaResponse<ConfirmationResponse> updateMapping(final Class<? extends ElsaModel> modelClass) {
        return this.updateMapping(modelClass, this.elsa.getRequestExceptionHandler());
    }


    public boolean indexExists(final String indexName, final RequestExceptionHandler handler) {
        final Response response;
        try {
            response = this.elsa.client.getLowLevelClient().performRequest("HEAD", indexName);
            return response.getStatusLine().getStatusCode() == 200;
        } catch (final Exception e) {
            handler.process(e, ExceptionMsg.REQUEST_FAILED);
        }
        return false;
    }

    public boolean indexExists(final String indexName) {
        return this.indexExists(indexName, this.elsa.getRequestExceptionHandler());
    }

    public boolean indexExists(final Class<? extends ElsaModel> modelClass, final RequestExceptionHandler handler) {
        return this.indexExists(IndexName.of(modelClass), handler);
    }

    public boolean indexExists(final Class<? extends ElsaModel> modelClass) {
        return this.indexExists(IndexName.of(modelClass), this.elsa.getRequestExceptionHandler());
    }


    public ElsaResponse<AcknowledgedResponse> deleteIndex(final String indexName, final RequestExceptionHandler handler) {
        try {
            return ElsaResponse.of(this.elsa.client.indices().delete(new DeleteIndexRequest(indexName)));
        } catch (final Exception e) {
            handler.process(e, ExceptionMsg.REQUEST_FAILED);
            return ElsaResponse.of(e);
        }
    }

    public ElsaResponse<AcknowledgedResponse> deleteIndex(final String indexName) {
        return this.deleteIndex(indexName, this.elsa.getRequestExceptionHandler());
    }

    public ElsaResponse<AcknowledgedResponse> deleteIndex(final Class<? extends ElsaModel> modelClass, final RequestExceptionHandler handler) {
        return this.deleteIndex(IndexName.of(modelClass), handler);
    }

    public ElsaResponse<AcknowledgedResponse> deleteIndex(final Class<? extends ElsaModel> modelClass) {
        return this.deleteIndex(IndexName.of(modelClass));
    }
}
