package top.yumuing.community.test;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TestDaoOne implements TestDao{

    @Override
    public String select() {
        return "one";
    }
}
