package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;

import com.mybatis.bean.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class mybatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /*
    * 1、根据xml全局配置文件（其中包含运行环境配置信息）创建一个SqlSessionFactory对象
    * 2、sql映射文件：配置每一个sql以及它的封装规则
    * 3、将sql映射文件这测到全局文件
    * 4、写如下代码：
    * 切记用完后.close()关闭
    * */
    @Test
    public void test() throws IOException {
        //1
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2
        //从sqlSessionFactory中获取sqlSession实例，能直接执行已经映射的sql语句
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
//            3.尚硅谷_MyBatis_HelloWorld 16:56
            //s:传入xml文件中配置好的sql的唯一标识（也就是id），以免发生id重复，最好将namespace连在id前
            Reader reader = openSession.selectOne("com.mybatis.bean.ReaderMapper.selectReader", 1);
            System.out.println(reader);
        }finally {
            openSession.close();
        }
    }

    @Test
    public void test2() throws IOException {
        //为方便，用写好的getSqlSessionFactory()方法获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //获取SqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Reader reader = openSession.selectOne("com.mybatis.bean.ReaderMapper.selectReader", 3);
            System.out.println(reader);
        }finally {
            openSession.close();
        }
    }
}
