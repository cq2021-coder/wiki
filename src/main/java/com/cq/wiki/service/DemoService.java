package com.cq.wiki.service;

import com.cq.wiki.domain.Demo;
import com.cq.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }

    public Demo selectById(){
        return demoMapper.selectByPrimaryKey(1L);
    }

    public Integer insert(Demo record){
        record.setId(2L);
        record.setName("程崎");
        return demoMapper.insert(record);
    }

}
