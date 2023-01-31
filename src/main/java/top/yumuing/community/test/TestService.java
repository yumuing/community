package top.yumuing.community.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    public TestDao testDaoOne;

    public String getDateOne(){
        return testDaoOne.select();
    }

    @Autowired
    @Qualifier("testDaoTwo")
    public TestDao testDaoTwo;
    public String getDateTwo(){
        return testDaoTwo.select();
    }
}
