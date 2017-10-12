package com.s4c.stg.dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection conn;
	private Statement st;
	private ResultSet rs;
	
	public DBConnection(){
		String dbName = "STGDB";
		String dbURL = "jdbc:mysql://localhost:3306/";
		
		CreateDataBase(dbName);
		
		try {
			//Class.forName("org.mysql.jdbc.Driver");
			//Connect to database
			this.conn = DriverManager.getConnection(dbURL+dbName,"root","apmsetup");
			this.st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DataBase Connection Error :"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void CreateDataBase(String dbName){
		PreparedStatement pstmt = null;
		String dbSql ="SELECT * FROM Information_schema.SCHEMATA WHERE SCHEMA_NAME = ?";
		try {
			pstmt = conn.prepareStatement(dbSql);
			pstmt.setString(1, dbName);
			rs = pstmt.executeQuery();
			
			//if there is no db, create db
			if(!rs.next()){
				this.st = conn.createStatement();
				String sql = "create database "+dbName;
				//database createFlag
				boolean cFlag = st.execute(sql);
				if(!cFlag){
					System.out.println("Fail to Create Database");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}finally{
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	public void CreateTable(String tableName){
		
		
	}
	
	public void exitDB(){
		try {
			this.st.close();
			this.rs.close();
			this.conn.close();
			} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}
