package com.samkruglov.mybatismetricsbasic.repositories;

import com.samkruglov.mybatismetricsbasic.domain.Item;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface ItemRepo {
    
    @Select("select * from items where id = #{id}")
        //todo return optional
    Item findOne(String id);
    
    @Select("select * from items")
    Set<Item> findAll();
    
    @Insert("insert into items values(#{id}, #{desc})")
    void insert(Item item);
    
    @Update("update items set desc = #{item.desc} where id = #{id}")
        // even though we compile with -parameters, it just doesn't work without @Param here
    void update(@Param("id") String id, @Param("item") Item item);
    
    @Delete("delete from items where id = #{id}")
    void delete(String item);
}
