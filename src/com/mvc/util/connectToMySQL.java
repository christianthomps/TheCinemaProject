/*
#DataSourceSettings#
        #LocalDataSource: e-booking@localhost
#BEGIN#
<data-source source="LOCAL" name="e-booking@localhost" uuid="a7d8a540-debb-4020-9a0d-321fde031442">
<database-info product="MySQL" version="8.0.13" jdbc-version="4.0" driver-name="MySQL Connector Java" driver-version="mysql-connector-java-5.1.46 ( Revision: 9cc87a48e75c2d2e87c1a293b2862ce651cb256e )" family="MYSQL" exact-version="8.0.13">
<extra-name-characters>#@</extra-name-characters><identifier-quote-string>`</identifier-quote-string></database-info><case-sensitivity plain-identifiers="lower" quoted-identifiers="lower"/><driver-ref>mysql</driver-ref><synchronize>true</synchronize><jdbc-driver>com.mysql.jdbc.com.mvc.test.Driver</jdbc-driver><jdbc-url>jdbc:mysql://localhost:3306/e-booking</jdbc-url><secret-storage>master_key</secret-storage><user-name>root</user-name><introspection-schemas>*:</introspection-schemas><driver-properties><property name="autoReconnect" value="true"/><property name="zeroDateTimeBehavior" value="convertToNull"/><property name="tinyInt1isBit" value="false"/><property name="characterEncoding" value="utf8"/><property name="characterSetResults" value="utf8"/><property name="yearIsDateType" value="false"/></driver-properties></data-source>
#END#
*/

package com.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class connectToMySQL {

    public static Connection createConnection()
    {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/e-booking"; //MySQL URL followed by the database name
        String username = "root"; //MySQL username
        String password = "password"; //MySQL password

        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //loading MySQL drivers. This differs for database servers
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Printing connection object "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }
}