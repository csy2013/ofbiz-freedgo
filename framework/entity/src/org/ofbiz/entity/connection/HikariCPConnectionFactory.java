package org.ofbiz.entity.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import javolution.util.FastMap;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.transaction.TransactionFactory;
import org.w3c.dom.Element;

import javax.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by changsy on 16/8/4.
 */
public class HikariCPConnectionFactory implements ConnectionFactoryInterface {
    public static final String module = HikariCPConnectionFactory.class.getName();
    protected static Map<String, HikariDataSource> dsCache = FastMap.newInstance();
    @Override
    public Connection getConnection(GenericHelperInfo helperInfo, Element jdbcElement) throws SQLException, GenericEntityException {
        HikariDataSource mds = dsCache.get(helperInfo.getHelperFullName());
        if (mds != null) {
            return TransactionFactory.getCursorConnection(helperInfo, mds.getConnection());
        }
        synchronized (DBCPConnectionFactory.class) {
            mds = dsCache.get(helperInfo.getHelperFullName());
            if (mds != null) {
                return TransactionFactory.getCursorConnection(helperInfo, mds.getConnection());
            }
        
            // connection properties
            TransactionManager txMgr = TransactionFactory.getTransactionManager();
            String driverName = jdbcElement.getAttribute("jdbc-driver");
            String jdbcUri = UtilValidate.isNotEmpty(helperInfo.getOverrideJdbcUri()) ? helperInfo.getOverrideJdbcUri() : jdbcElement.getAttribute("jdbc-uri");
            //增加docker container 使用 mysql IP 目前只支持mysql
            String dockerMysqlName = System.getProperty("ofbiz.docker.mysql.name");
            System.out.println("dockerMysqlName = " + dockerMysqlName);
            if(dockerMysqlName!=null && !dockerMysqlName.equals("")){
                String jdbcIp = System.getenv(dockerMysqlName+"_PORT_3306_TCP_ADDR");
                System.out.println("jdbcIp = " + jdbcIp);
                if(jdbcIp!=null) {
                    String partUri = jdbcUri.substring("jdbc:mysql://".length());
                    partUri = partUri .substring(partUri.indexOf("/"));
                    jdbcUri = "jdbc:mysql://" + jdbcIp + partUri;
                }
                System.out.println("jdbcUri = " + jdbcUri);
            }
            String jdbcUsername = UtilValidate.isNotEmpty(helperInfo.getOverrideUsername()) ? helperInfo.getOverrideUsername() : jdbcElement.getAttribute("jdbc-username");
            String jdbcPassword = UtilValidate.isNotEmpty(helperInfo.getOverridePassword()) ? helperInfo.getOverridePassword() : jdbcElement.getAttribute("jdbc-password");
        
            // pool settings
            int maxSize, minSize, timeBetweenEvictionRunsMillis;
            try {
                maxSize = Integer.parseInt(jdbcElement.getAttribute("pool-maxsize"));
            } catch (NumberFormatException nfe) {
                Debug.logError("Problems with pool settings [pool-maxsize=" + jdbcElement.getAttribute("pool-maxsize") + "]; the values MUST be numbers, using default of 20.", module);
                maxSize = 20;
            } catch (Exception e) {
                Debug.logError("Problems with pool settings [pool-maxsize], using default of 20.", module);
                maxSize = 20;
            }
            try {
                minSize = Integer.parseInt(jdbcElement.getAttribute("pool-minsize"));
            } catch (NumberFormatException nfe) {
                Debug.logError("Problems with pool settings [pool-minsize=" + jdbcElement.getAttribute("pool-minsize") + "]; the values MUST be numbers, using default of 2.", module);
                minSize = 2;
            } catch (Exception e) {
                Debug.logError("Problems with pool settings [pool-minsize], using default of 2.", module);
                minSize = 2;
            }
            // idle-maxsize, default to half of pool-maxsize
            int maxIdle = maxSize / 2;
            if (jdbcElement.hasAttribute("idle-maxsize")) {
                try {
                    maxIdle = Integer.parseInt(jdbcElement.getAttribute("idle-maxsize"));
                } catch (NumberFormatException nfe) {
                    Debug.logError("Problems with pool settings [idle-maxsize=" + jdbcElement.getAttribute("idle-maxsize") + "]; the values MUST be numbers, using calculated default of" + (maxIdle > minSize ? maxIdle : minSize) + ".", module);
                } catch (Exception e) {
                    Debug.logError("Problems with pool settings [idle-maxsize], using calculated default of" + (maxIdle > minSize ? maxIdle : minSize) + ".", module);
                }
            }
            // Don't allow a maxIdle of less than pool-minsize
            maxIdle = maxIdle > minSize ? maxIdle : minSize;
        
            try {
                timeBetweenEvictionRunsMillis = Integer.parseInt(jdbcElement.getAttribute("time-between-eviction-runs-millis"));
            } catch (NumberFormatException nfe) {
                Debug.logError("Problems with pool settings [time-between-eviction-runs-millis=" + jdbcElement.getAttribute("time-between-eviction-runs-millis") + "]; the values MUST be numbers, using default of 600000.", module);
                timeBetweenEvictionRunsMillis = 600000;
            } catch (Exception e) {
                Debug.logError("Problems with pool settings [time-between-eviction-runs-millis], using default of 600000.", module);
                timeBetweenEvictionRunsMillis = 600000;
            }
         
        
            // connection factory properties
            HikariConfig cfProps = new HikariConfig();
            cfProps.setDriverClassName(driverName);
            cfProps.setUsername(jdbcUsername);
            cfProps.setPassword(jdbcPassword);
            cfProps.setJdbcUrl(jdbcUri);
            cfProps.setMaxLifetime(MINUTES.toMillis(1));
            
            cfProps.setMaximumPoolSize(maxSize);
             
            String transIso = jdbcElement.getAttribute("isolation-level");
            if (UtilValidate.isNotEmpty(transIso)) {
                if ("Serializable".equals(transIso)) {
                    
                    cfProps.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_SERIALIZABLE));
                } else if ("RepeatableRead".equals(transIso)) {
                    
                    cfProps.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_REPEATABLE_READ));
                } else if ("ReadUncommitted".equals(transIso)) {
                    
                    cfProps.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_READ_UNCOMMITTED));
                } else if ("ReadCommitted".equals(transIso)) {
                   
                    cfProps.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_READ_COMMITTED));
                } else if ("None".equals(transIso)) {
                   
                    cfProps.setTransactionIsolation(String.valueOf(Connection.TRANSACTION_NONE));
                }
            }
            
        
            mds = new HikariDataSource(cfProps);
            dsCache.put(helperInfo.getHelperFullName(), mds);
        
            return TransactionFactory.getCursorConnection(helperInfo, mds.getConnection());
        }
    }
    
    @Override
    public void closeAll() {
        // no methods on the pool to shutdown; so just clearing for GC
        dsCache.clear();
    }
}
