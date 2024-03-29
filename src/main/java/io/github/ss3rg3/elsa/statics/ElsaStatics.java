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

package io.github.ss3rg3.elsa.statics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ss3rg3.elsa.jsonmapper.GsonUTCDateAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Constants for ElsaStatics
 */
public class ElsaStatics {
    private ElsaStatics() {
    }


    // ------------------------------------------------------------------------------------------ //
    // JSON RELATED
    // ------------------------------------------------------------------------------------------ //

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
            .create();

    /**
     * Used to get JSON from exception messages.
     */
    public static final Pattern jsonExtractorPattern = Pattern.compile("\\{.*}");

    public static final DateFormat UTC_FORMAT;

    static {
        UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
        UTC_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }


    // ------------------------------------------------------------------------------------------ //
    // MISC
    // ------------------------------------------------------------------------------------------ //
    /**
     * Temporary official recommendation for ES 6.x till 7.0,
     * see https://www.elastic.co/guide/en/elasticsearch/reference/current/removal-of-types.html
     * todo do we still need this?
     */
    public static final String DUMMY_TYPE = "_doc";

    public static final String DEFAULT_ID_FIELD_NAME = "id";

}
