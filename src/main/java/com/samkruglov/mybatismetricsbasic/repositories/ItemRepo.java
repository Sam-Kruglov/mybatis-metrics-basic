package com.samkruglov.mybatismetricsbasic.repositories;

import com.samkruglov.mybatismetricsbasic.domain.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface ItemRepo {
    
    @Select("select * from items where id = #{id}")
        //todo return optional
    Item findOne(String id);
    
    @Select("select * from items where id = #{id}")
    Set<Item> findAll();
    
    @Insert("insert into items values(#{id})")
    void save(Item item);
    
    @Delete("delete from items where id = #{id}")
    void delete(String item);
}
