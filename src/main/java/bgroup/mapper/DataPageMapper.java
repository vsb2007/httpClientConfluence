package bgroup.mapper;

import bgroup.model.DataPage;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by VSB on 10.08.2017.
 * httpClientConfluence
 */
public interface DataPageMapper {

    @Insert("insert into page(page_id,page_header,page_date_create) values(#{pageId},#{pageHeader},#{pageDateCreate})")
    @SelectKey(statement = " SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    void insertDataPage(DataPage dataPage);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "pageId", column = "page_id"),
            @Result(property = "pageHeader", column = "page_header"),
            @Result(property = "pageDamage", column = "page_damage"),
            @Result(property = "pageDescription", column = "page_description"),
            @Result(property = "pageDateCreate", column = "page_date_create"),
            @Result(property = "pageChance", column = "page_chance")
    })
    @Select("select * from page WHERE id=#{id}")
    DataPage findPageById(Integer id);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "pageId", column = "page_id"),
            @Result(property = "pageHeader", column = "page_header"),
            @Result(property = "pageDamage", column = "page_damage"),
            @Result(property = "pageDescription", column = "page_description"),
            @Result(property = "pageDateCreate", column = "page_date_create"),
            @Result(property = "pageChance", column = "page_chance")
    })
    @Select("select * from page")
    List<DataPage> findAllPages();

}
