package cn.summerwaves.service.impl;

import cn.summerwaves.dao.StudentDao;
import cn.summerwaves.model.Student;
import cn.summerwaves.service.IStudentService;
import cn.summerwaves.util.XMemcachedUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Resource
    private StudentDao studentDao;
    @Resource
    private XMemcachedUtil xMemcachedUtil;

    private static Logger logger = Logger.getLogger("StudentServiceImpl.class");

    @Override
    public List<Student> selectThreeStudent() {
        Student student1 = (Student) xMemcachedUtil.getCache("student1");
        Student student2 = (Student) xMemcachedUtil.getCache("student2");
        Student student3 = (Student) xMemcachedUtil.getCache("student3");
        if (student1 != null && student2 != null && student3 != null) {
            List<Student> students = new ArrayList<Student>();
            students.add(student1);
            students.add(student2);
            students.add(student3);
            logger.error("使用缓存");
            return students;
        }
        logger.error("使用数据库");
        Student studentFromDB1 = studentDao.selectOneStudent("10");
        Student studentFromDB2 = studentDao.selectOneStudent("11");
        Student studentFromDB3 = studentDao.selectOneStudent("12");

        xMemcachedUtil.addCache("student1",3600,studentFromDB1);
        xMemcachedUtil.addCache("student2",3600,studentFromDB2);
        xMemcachedUtil.addCache("student3",3600,studentFromDB3);
        List<Student> studentsFromDB = new ArrayList<Student>();
        studentsFromDB.add(studentFromDB1);
        studentsFromDB.add(studentFromDB2);
        studentsFromDB.add(studentFromDB3);
        return  studentsFromDB;
    }

    @Override
    public List<Student> selectThreeStudentFromDB() {
        Student studentsFromDB1 = studentDao.selectOneStudent("10");
        Student studentsFromDB2 = studentDao.selectOneStudent("12");
        Student studentsFromDB3 = studentDao.selectOneStudent("13");

        List<Student> studentsFromDB =new ArrayList<Student>();
        studentsFromDB.add(studentsFromDB1);
        studentsFromDB.add(studentsFromDB2);
        studentsFromDB.add(studentsFromDB3);
        return studentsFromDB;
    }

    @Override
    public void insertStudent(Student student) {
        xMemcachedUtil.deleteCache("student1");
        xMemcachedUtil.deleteCache("student2");
        xMemcachedUtil.deleteCache("student3");
        studentDao.insertStudent(student);
    }
}
