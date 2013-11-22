package org.motechproject.noyawa.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class dbConnection {
	
	public Connection noYawaConnection() throws Exception{
		Context initCtx = new InitialContext();
        DataSource ds = (DataSource) initCtx.lookup("java:/comp/env/jdbc/NoyawaDB");
        return ds.getConnection();
	}
}
