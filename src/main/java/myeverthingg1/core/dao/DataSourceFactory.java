package myeverthingg1.core.dao;

import myeverthingg1.core.model.Thing;


import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;


public class DataSourceFactory {
    /**
     * 数据库的数据源
     */
     //  ---  数据源工厂 单例  ---
    public static volatile DruidDataSource instance;

    private DataSourceFactory() {
    }
    public static DataSource getInstance() {
        if (instance == null) {
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DruidDataSource();

                    //连接数据库需要 ：
                    //  url -> host,port,databaseName
                    //  driverClass
                    //  username
                    //  password
                    //1. 这是链接MySQL的配置
                    //        instance.setUrl("jdbc:mysql://127.0.0.1:3306/everything_g1" +
                    //  "?useUnicode=true&characterEncoding=utf-8&useSSL=false");
//                    instance.setUsername("root");
//                    instance.setPassword("123456");
//                    instance.setDriverClassName("com.mysql.jdbc.Driver");

                    //2. 这是链接H2数据库的配置
                    instance.setTestWhileIdle(false);
                    instance.setDriverClassName("org.h2.Driver");
                    String path = System.getProperty("user.dir") + File.separator + "lib";
                    instance.setUrl("jdbc:h2:" + path);

                    //数据库创建完成之后，初始化表结构
                    databaseInit(false);
                }
            }
        }
        return instance;
    }

  //数据库表的初始化
    public static void databaseInit(boolean buildIndex) {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = DataSourceFactory.class.getClassLoader().getResourceAsStream("database.sql");)
         {
            if (in!=null) {
                try (
                        //按行读取  InputStreamReader：字节流转字符流
                       BufferedReader reader = new BufferedReader(new InputStreamReader(in))
                ){
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("database.sql script can't load please check it.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sql = sb.toString();

        try (Connection connection = getInstance().getConnection();) {
            if (buildIndex) {
                try (PreparedStatement statement = connection.prepareStatement("drop table if exists thing;");) {
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataSourceFactory.databaseInit(true);
    }
}


//  //测试   测试是否与数据库建立连接，将thing传入到数据库 是否能写入SQL语句，是否能够进行查询
//    public static void main(String[] args){
//        Thing thing = new Thing();
//        DataSource dataSource = DataSourceFactory.getInstance();
//
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.
//                     prepareStatement("insert into thing(name,path,depth,file_type) value(?,?,?,?)")
//        ) {
//            statement.setString(1, "1.png");
//            statement.setString(2, "C:\\打地鼠\\1.png");
//            statement.setInt(3, 2);
//            statement.setString(4,"DOC");
//            statement.executeUpdate();
//        } catch (SQLException e) {
//
//        }


