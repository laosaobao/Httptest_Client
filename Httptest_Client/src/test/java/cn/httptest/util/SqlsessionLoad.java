package cn.httptest.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SqlsessionLoad {
    public static SqlSession sqlSession;
    static {
        //得到配置文件流
        String resource = "Mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建会话工厂,传入mybatis
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //通过工厂得到SqlSession
        SqlsessionLoad.sqlSession = sqlSessionFactory.openSession();
    }
}
