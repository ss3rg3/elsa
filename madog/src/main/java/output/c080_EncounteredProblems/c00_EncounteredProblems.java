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

package output.c080_EncounteredProblems;

import madog.core.Output;
import madog.core.Print;


public class c00_EncounteredProblems extends Output {

    @Override
    public void addMarkDownAsCode() {
        Print.h1("NoClassDefFoundError for Log4J");
        Print.wrapped("`java.lang.NoClassDefFoundError: org/apache/logging/log4j/util/MultiFormatStringBuilderFormattable`<br/>" +
                "No idea, no time to solve it. Start the cluster before testing and it's suddenly no problem... -__-");
    }

}
