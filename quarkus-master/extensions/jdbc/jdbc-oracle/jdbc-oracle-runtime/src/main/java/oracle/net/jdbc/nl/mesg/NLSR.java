/*
 * Copyright 2019 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package oracle.net.jdbc.nl.mesg;

import java.util.HashMap;

/**
 *
 * @author michael
 */
public class NLSR {
    public static final HashMap<String, String> MY_MAP = new HashMap<String, String>() {
        {
            put("NoFile-04600", "TNS-04600: File not found: {0}");
            put("FileReadError-04601", "TNS-04601: Error while reading the parameter file: {0}, error is: {1}");
            put("SyntaxError-04602", "TNS-04602: Invalid syntax error: Expected \"{0}\" before or at {1}");
            put("UnexpectedChar-04603", "TNS-04603: Invalid syntax error: Unexpected char \"{0}\" while parsing {1}");
            put("ParseError-04604", "TNS-04604: Parse object not initialized");
            put("UnexpectedChar-04605", "TNS-04605: Invalid syntax error: Unexpected char or LITERAL \"{0}\" before or at {1}");
            put("NoLiterals-04610", "TNS-04610: No literals left, reached end of NV pair");
            put("InvalidChar-04611", "TNS-04611: Invalid continuation character after Comment");
            put("NullRHS-04612", "TNS-04612: Null RHS for \"{0}\"");
            put("Internal-04613", "TNS-04613: Internal error: {0}");
            put("NoNVPair-04614", "TNS-04614: NV Pair {0} not found");
            put("InvalidRHS-04615", "TNS-04615: Invalid RHS for {0}");
        }
    };

    public NLSR() {
    }
}
