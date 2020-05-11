package com.mybatis.test;


import java.io.IOException;
import java.io.InputStream;

import com.mybatis.bean.Reader;
import com.mybatis.bean.ReaderMapper;
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
    * 3、将sql映射文件注册到全局文件
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
        //从sqlSessionFactory中获取sqlSession实例，能直接执行已经映射的sql语句，一个SqlSession代表和数据库的一次会话
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

    //        4.尚硅谷_MyBatis_接口式编程
    @Test
    public void test2() throws IOException {
        //1、为方便，用写好的getSqlSessionFactory()方法获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2、获取SqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
//        4.尚硅谷_MyBatis_接口式编程 05:20
            //3、获取接口的实现类对象。传入接口，mybatis会为我们生成相应的代理对象
            ReaderMapper mapper = openSession.getMapper(ReaderMapper.class);

            //4、调用接口的方法
            Reader reader = mapper.getReaderById(3);

            //5、打印
            System.out.println(reader);
        }finally {
            //6、关闭资源
            openSession.close();
        }
    }
}
