package cn.summerwaves.util;

import cn.summerwaves.dao.StudentDao;
import cn.summerwaves.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@ContextConfiguration(locations = {"classpath:ApplicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class XMemcachedUtilTest {
    @Test
    public void addCache() throws Exception {
    }

    @Resource
    private XMemcachedUtil xMemcachedUtil;
    @Resource
    private StudentDao studentDao;
    @Test
    public void init() throws Exception {

 /*       List<Student> studentsFromDB = studentDao.selectAllStudent();
        xMemcachedUtil.addCache("students", 3600, "fuck");*/
        xMemcachedUtil.addCache("fuck",3600,"fucking");
        System.out.println((String) xMemcachedUtil.getCache("fuck"));
    }
}