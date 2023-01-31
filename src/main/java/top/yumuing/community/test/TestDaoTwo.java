package top.yumuing.community.test;

import org.springframework.stereotype.Repository;

@Repository("testDaoTwo")
public class TestDaoTwo implements TestDao{

    @Override
    public String select() {
        return "two";
    }
}
