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
package io.quarkus.jdbc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.Delete;
import com.oracle.svm.core.annotate.Inject;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

import oracle.net.jdbc.nl.mesg.NLSR;
import oracle.net.nt.NTAdapter;

//@TargetClass(className = "oracle.jdbc.driver.SQLUtil", innerClass = { "XMLFactory" })
//@Substitute
//final class XMLFactory {
//
//    static Datum createXML(OPAQUE param1OPAQUE) throws SQLException {
//        return null;
//    }
//
//    static Datum createXML(OracleConnection param1OracleConnection, String param1String) throws SQLException {
//        return null;//XMLType.createXML(param1OracleConnection, param1String);
//    }
//
//    static Datum createXML(OracleConnection param1OracleConnection, InputStream param1InputStream) throws SQLException {
//        return null;//XMLType.createXML(param1OracleConnection, param1InputStream);
//    }
//}
//
//@TargetClass(className = "oracle.jdbc.driver.NamedTypeAccessor", innerClass = { "XMLFactory" })
//@Substitute
//final class NamedXMLFactory {
//
//    static Datum createXML(OPAQUE param1OPAQUE) throws SQLException {
//        return null;
//    }
//
//}
//
//@TargetClass(className = "oracle.sql.OPAQUE")
//final class OPAQUESubst {
//
//    @Alias
//    OpaqueDescriptor descriptor;
//
//    @Alias
//    public Object toClass(Class paramClass, Map paramMap) throws SQLException {
//        return null;
//    }
//
//    @Substitute
//    public Object toJdbc(Map paramMap) throws SQLException {
//        Object object = this;
//
//        if (paramMap != null) {
//
//            Class clazz = this.descriptor.getClass(paramMap);
//
//            if (clazz != null) {
//                object = toClass(clazz, paramMap);
//            }
//        }
//        return object;
//    }
//}
@TargetClass(className = "oracle.jdbc.proxy.ProxyFactory")
final class ProxyFactory {

    @Substitute
    private Constructor prepareProxy(Class paramClass1, Class paramClass2) {
        System.out.println("paramClass1.name = " + paramClass1.getName());
        System.out.println("paramClass2.name = " + paramClass2.getName());
        return null;
    }
}

@TargetClass(className = "oracle.jdbc.driver.PhysicalConnection")
final class PhysicalConnection {

    @Substitute
    private static final String[] getSecretStoreCredentials(String paramString1, String paramString2, String paramString3)
            throws SQLException {
        return null;
    }
}

@TargetClass(className = "oracle.net.ns.SessionAtts")
final class SessionAtts {

    @Alias
    public ByteBuffer buffer;

    @Alias
    public ByteBuffer payloadDataBuffer;

    @Alias
    NTAdapter.NetworkAdapterType networkType;

    @Alias
    boolean needToReleaseMSGQBuffer;

    @Alias
    boolean useNativeBuffers;

    @Alias
    ByteBuffer headerBuffer;

    @Alias
    ByteBuffer payloadBuffer;

    @Alias
    public int sdu;

    @Substitute
    void setBuffer(ByteBuffer paramByteBuffer) {
        /*
         * Удалена возможность освободить MSGQ буффер
         */
        this.buffer = paramByteBuffer;
        sliceBuffer(this.buffer);
    }

    @Substitute
    public void initializeBuffer(int paramInt) throws IOException {
        /*
         * Здесь удалено определение адаптера как MSGQ
         */
        if (this.networkType == NTAdapter.NetworkAdapterType.MSGQ) {
            if (this.buffer == null) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
            }

        } else if (this.useNativeBuffers) {
            this.buffer = ByteBuffer.allocateDirect(paramInt);
        } else {

            this.buffer = ByteBuffer.allocate(paramInt);
        }

        sliceBuffer(this.buffer);
    }

    @Substitute
    public void sliceBuffer(ByteBuffer paramByteBuffer) {

        paramByteBuffer.clear().limit(8);
        this.headerBuffer = paramByteBuffer.slice();

        paramByteBuffer.clear().position(8).limit(getSDU());
        this.payloadBuffer = paramByteBuffer.slice();

        paramByteBuffer.clear().position(10).limit(getSDU());
        this.payloadDataBuffer = paramByteBuffer.slice();
    }

    @Substitute
    public int getSDU() {
        return this.sdu;
    }
}

@TargetClass(className = "oracle.net.jdbc.nl.NetStrings")
final class NetStrings {

    @Delete
    ResourceBundle strBundle;
    @Delete
    Locale loc;

    @Inject
    public NLSR nlsr;

    @Substitute
    public NetStrings(String paramString, Locale paramLocale) {
        //this.strBundle = ResourceBundle.getBundle(paramString, paramLocale); 
    }

    @Substitute
    public NetStrings(Locale paramLocale) {
        //this.strBundle = ResourceBundle.getBundle("oracle.net.jdbc.nl.mesg.NLSR", paramLocale); 
    }

    @Substitute
    public NetStrings() {
        //this.strBundle = ResourceBundle.getBundle("oracle.net.jdbc.nl.mesg.NLSR", Locale.getDefault());         
    }

    @Substitute
    public String getString(String paramString) {
        return this.nlsr.MY_MAP.get(paramString);
        //return this.strBundle.getString(paramString;
    }

    @Substitute
    public String getString(String paramString, Object[] paramArrayOfObject) {
        String str = this.nlsr.MY_MAP.get(paramString);
        //this.strBundle.getString(paramString);

        StringBuffer stringBuffer = new StringBuffer();
        MessageFormat messageFormat = new MessageFormat(str);
        messageFormat.format(paramArrayOfObject, stringBuffer, null);

        return stringBuffer.toString();
    }
}

@TargetClass(className = "oracle.net.jdbc.nl.NLException")
final class NLException {
    @Alias
    public Object[] msg_params;
    @Alias
    public String error_index;
    @Alias
    public String error_msg;

    @Substitute
    public NLException(String paramString) {
        this.error_msg = paramString;
    }

    @Substitute
    public NLException(String paramString, Object paramObject) {
        this.error_index = paramString;
        Object[] arrayOfObject = { paramObject };
        this.msg_params = arrayOfObject;
        initErrorMessage();
    }

    @Substitute
    public NLException(String paramString, Object[] paramArrayOfObject) {
        this.error_index = paramString;
        this.msg_params = paramArrayOfObject;
        initErrorMessage();
    }

    @Substitute
    public void initErrorMessage() {
        ///FIXME
        //NetStrings netStrings = new NetStrings("oracle.net.jdbc.nl.mesg.NLSR", Locale.getDefault());
        NetStrings netStrings = new NetStrings("", null);

        this.error_msg = netStrings.getString(this.error_index, this.msg_params);
    }

    @Substitute
    public String toString() {
        return getErrorMessage();
    }

    @Substitute
    public String getErrorIndex() {
        return this.error_index;
    }

    @Substitute
    public String getErrorMessage() {
        return this.error_msg;
    }

    @Substitute
    public String getMessage() {
        return getErrorMessage();
    }
}

/*
 * @TargetClass(className = "oracle.net.nt.MQLNTAdapter")
 * final class MQLNTAdapter {
 * 
 * @Substitute
 * public MQLNTAdapter(String paramString, Properties paramProperties) throws NLException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void readRemoteQueueNameOnLocalQueue() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void writeLocalQueueNameOnSocket(LocalQueue paramLocalQueue) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public ByteBuffer readFromLocalQueue() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void readNTMQPacketFromLocalQueue() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void processNTMQLayer(ByteBuffer paramByteBuffer) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public int writeToRemoteQueue(ByteBuffer paramByteBuffer, boolean paramBoolean) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void connect() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private boolean isConnectionDead() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void connectSocket() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * protected void connectToRemoteQueue() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void setSocketOptions() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void disconnect() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public InputStream getInputStream() throws IOException {
 * return null;
 * }
 * 
 * @Substitute
 * public OutputStream getOutputStream() throws IOException {
 * return null;
 * }
 * 
 * @Substitute
 * public void setOption(int paramInt, Object paramObject) throws IOException, NetException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public Object getOption(int paramInt) throws IOException, NetException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void abort() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void sendUrgentByte(int paramInt) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public boolean isCharacteristicUrgentSupported() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void setReadTimeoutIfRequired(Properties paramProperties) throws IOException, NetException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public boolean isConnectionSocketKeepAlive() {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public InetAddress getInetAddress() {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public SocketChannel getSocketChannel() {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public NTAdapter.NetworkAdapterType getNetworkAdapterType() {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void setNegotiatedSDUAndTDU(int paramInt1, int paramInt2) {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private int replenish() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private boolean isClosed() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public void prepareFlowControlPacket(ByteBuffer paramByteBuffer) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void scheduleInterrupt(int paramInt) throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void handleInterrupt() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private void cancelTimeout() throws IOException {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * public static final String packetToString(ByteBuffer paramByteBuffer) {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * private static String dump(ByteBuffer paramByteBuffer) {
 * throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools |
 * Templates.
 * }
 * 
 * @Substitute
 * 
 * @Log
 * protected void debug(Logger paramLogger, Level paramLevel, Executable paramExecutable, String paramString) {
 * ClioSupport.log(paramLogger, paramLevel, getClass(), paramExecutable, paramString);
 * }
 * }
 */
//@TargetClass(className = "oracle.as.jmx.framework.PortableMBeanFactory")
//final class PortableMBeanFactory {
//
//    @Substitute
//    public MBeanServer getMBeanServer() {
//        return null;
//    }
//}
//@TargetClass(className = "oracle.security.pki.OraclePKIProvider")
//@Substitute
//final class OraclePKIProvider {
//
//}
//
//final class OraclePKIProviderSelector implements BooleanSupplier{
//
//    @Override
//    public boolean getAsBoolean() {
//        
//    }
//
//}
/**
 *
 * @author mvolkov
 *         ,
 * @authow mSmirnov
 */
public class OracleJDBCSubstitutions {

}
