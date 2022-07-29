package com.github.pioneeryi.gateway.dao.mapper;

import com.github.pioneeryi.gateway.dao.po.CalciteSchemaDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface CalciteSchemaMapper {

    @Insert({
            "insert into t_meta_calcite_schema (id, name, type, factory, operand)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{type,jdbcType=VARCHAR}, #{factory,jdbcType=VARCHAR}, #{operand,jdbcType=VARCHAR})"
    })
    int insert(CalciteSchemaDO record);

    @Select({
            "select",
            "id, name, type, factory, operand ",
            "from t_meta_calcite_schema",
            "where name = #{name,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR),
            @Result(column = "factory", property = "factory", jdbcType = JdbcType.VARCHAR),
            @Result(column = "operand", property = "operand", jdbcType = JdbcType.VARCHAR),
    })
    CalciteSchemaDO selectByName(String name);
}
