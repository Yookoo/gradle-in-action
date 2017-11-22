package com.edu.gradle.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/22.
 */
public class JDBCUtil {
    private static final String DRIVER = "org.h2.Driver" ;
    private static final String URL = "jdbc:h2:tcp://localhost/mem:test2;DB_CLOSE_DELAY=-1" ;
    private static final String USERNAME = "sa" ;
    private static final String PASSWORD = "" ;

    /**
     * 获得H2数据库链接
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getH2Connection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    /**
     * 关闭h2数据库链接
     * @param conn
     */
    public static void close(Connection conn) {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询单个
     * @param connection
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Map<String, Object> executeQueryOne(Connection connection,String sql, List<Object> params)
            throws SQLException, ClassNotFoundException{
        Map<String, Object> map = new HashMap<>();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(i+1, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData  = resultSet.getMetaData();
        int colsSize = metaData.getColumnCount();
        while(resultSet.next()){
            for(int i = 0; i<colsSize; i++){
                map = changeResultSetToMap(resultSet);
            }
        }
        return map;
    }

    /**
     *
     * @param connection
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static List<Map<String, Object>> executeQueryList (Connection connection, String sql, List<Object> params)
            throws SQLException, ClassNotFoundException{
        List<Map<String, Object>> list = new ArrayList<>();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i = 0; i<params.size(); i++){
                pstmt.setObject(i+1, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        while(resultSet.next()){
            Map<String, Object> map = changeResultSetToMap(resultSet);
            list.add(map);
        }
        return list;
    }

    /**
     * 把查询到的ResultSet转化为map返回
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private static Map<String, Object> changeResultSetToMap(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colSize = metaData.getColumnCount();
        Map<String, Object> map = new HashMap<>();
        for(int i = 0; i<colSize; i++){
            String colsName = metaData.getColumnName(i+1);
            Object colsValue = resultSet.getObject(colsName);
            colsValue = colsValue == null ? "" : colsValue;
            map.put(colsName, colsValue);
        }
        return map;
    }

    /**
     * 删除、修改
     * @param connection
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */

    public static boolean executeUpdateOrDelete (Connection connection, String sql, List<Object>params)throws SQLException{

        PreparedStatement pstmt = connection.prepareStatement(sql);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(i + 1, params.get(i));
            }
        }
        int result = pstmt.executeUpdate();
        boolean flag = result > 0 ? true : false;
        return flag;
    }

    /**
     * 新增
     * @param connection
     * @param sql
     * @param params
     * @return 主键
     * @throws SQLException
     */
    public static Long executeInsert (Connection connection, String sql, List<Object> params) throws SQLException {

        PreparedStatement pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        if(params != null && !params.isEmpty()){
            for(int i=0; i<params.size(); i++){
                pstmt.setObject(i + 1, params.get(i));
            }
        }
        pstmt.executeUpdate();
        ResultSet keys = pstmt.getGeneratedKeys();
        Long result = keys.next() ? keys.getLong(1) : -1;
        return result;
    }
}
