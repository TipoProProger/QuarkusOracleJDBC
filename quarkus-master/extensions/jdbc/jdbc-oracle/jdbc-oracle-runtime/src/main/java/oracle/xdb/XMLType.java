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
package oracle.xdb;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.Document;

import oracle.jdbc.OracleBlob;
import oracle.jdbc.OracleClob;
import oracle.jdbc.internal.XMLTypeIntf;
import oracle.sql.CLOB;
import oracle.sql.Datum;
import oracle.sql.OPAQUE;
import oracle.sql.OpaqueDescriptor;

/**
 *
 * @author mvolkov
 */
@SuppressWarnings("deprecation")
public class XMLType extends OPAQUE implements XMLTypeIntf {

    public static final int _SQL_TYPECODE = 2007;
    public static final String _SQL_NAME = "SYS.XMLTYPE";
    public static final String ENCODE_ON_CLIENT = "CLIENTCONTEXT.XML_ENCODE_ON";
    public static final String DECODE_ON_CLIENT = "CLIENTCONTEXT.XML_DECODE_ON";
    public static final int CONNTYPE_THIN = 0;
    public static final int CONNTYPE_OCI8 = 1;
    public static final int CONNTYPE_KPRB = 2;
    public static final int DEF_INDENT = 2;
    public static final int MAX_INDENT = 12;
    public static final int MAX_PFLAG = 71;
    public static final int PRINT_DEFAULT_PROPERTY = 1;
    public static final int PRINT_PRETTY = 2;
    public static final int PRINT_NOPRETTY = 4;

    public XMLType(OpaqueDescriptor od, Connection cnctn, Object o) throws SQLException {
        super(od, cnctn, o);
    }

    @Override
    public void free() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutputStream setBinaryStream() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Writer setCharacterStream() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getString() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setString(String string) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends Source> T getSource(Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T extends Result> T setResult(Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Datum toDatum(Connection cnctn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static XMLType createXML(OPAQUE opq, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(OPAQUE opq) throws SQLException {
        return createXML(opq, null);
    }

    public static XMLType createXML(Connection conn, String xmlval) throws SQLException {
        return createXML(conn, xmlval, null);
    }

    public static XMLType createXML(Connection conn, String xmlval, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, String xmlval, String schemaURL, boolean wellformed, boolean valid)
            throws SQLException {
        return createXML(conn, xmlval, schemaURL, wellformed, valid, null);
    }

    public static XMLType createXML(Connection conn, String xmlval,
            String schemaURL, boolean wellformed, boolean valid, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, CLOB xmlval, String schemaURL, boolean wellformed, boolean valid)
            throws SQLException {
        return createXML(conn, xmlval, schemaURL, wellformed, valid, null);
    }

    public static XMLType createXML(Connection conn, CLOB xmlval) throws SQLException {
        return createXML(conn, xmlval, null);
    }

    public static XMLType createXML(Connection conn, CLOB xmlval, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, CLOB xmlval,
            String schemaURL, boolean wellformed, boolean valid, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, InputStream is) throws SQLException {
        return createXML(conn, is, null);
    }

    public static XMLType createXML(Connection conn, InputStream is, String kind) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, Document domdoc) throws SQLException {
        return null;
    }

    public static XMLType createXML(Connection conn, OracleClob xmlval) throws SQLException {
        return (XMLType) xmlval.toSQLXML();
    }

    public static XMLType createXML(Connection conn, OracleClob xmlval, String schemaURL) throws SQLException {
        return (XMLType) xmlval.toSQLXML(schemaURL);
    }

    public static XMLType createXML(Connection conn, OracleBlob xmlval, int csid) throws SQLException {
        return null;
    }

    public OracleClob toClob() throws SQLException {
        return null;
    }

    public byte[] toBytes() throws SQLException {
        return null;
    }
}
