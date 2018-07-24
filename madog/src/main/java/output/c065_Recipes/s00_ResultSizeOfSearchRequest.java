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

package output.c065_Recipes;

import madog.core.Output;
import madog.core.Print;
import madog.core.Ref;
import madog.markdown.Icon;
import madog.markdown.List;
import output.c070_Helpers.s00_Helpers;


public class s00_ResultSizeOfSearchRequest extends Output {

    @Override
    public void addMarkDownAsCode() {

        Print.h1("Cookbook Recipes");

        Print.h2("Getting the Result Size of a SearchRequest");
        Print.wrapped("Use `SearchDAO.search` to execute any `SearchRequest`. Use the returning `SearchResponse` to " +
                "get the hits count. Use `SearchResponseMapper` to map the `SearchResponse` to your model.");

    }

}