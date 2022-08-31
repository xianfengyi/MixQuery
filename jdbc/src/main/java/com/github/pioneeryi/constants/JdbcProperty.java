package com.github.pioneeryi.constants;

/**
 * MixQuery jdbc支持的配置名
 */
public class JdbcProperty {

    /**
     * 用户名
     */
    public static final String USERNAME = "user";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";
    /**
     * Statement执行统一超时时间,单位秒
     */
    public static final String EXECUTE_TIMEOUT_S = "execute_timeout_s";
    /**
     * 是否执行同步查询
     */
    public static final String SYNC_EXECUTION = "sync_execution";
    /**
     * 引擎类型
     */
    public static final String ENGINE_TYPE = "engine_type";
    /**
     * 引擎id
     */
    public static final String ENGINE_ID = "engine_id";
    /**
     * sql方言
     */
    public static final String DIALECT = "dialect";
    /**
     * 基础catalog
     */
    public static final String CATALOG = "catalog";
    /**
     * bizId,用于数据资源查询
     */
    public static final String BIZ_ID = "biz_id";
    /**
     * dataType，指定是查询物理库表 还是 数据资源
     */
    public static final String DATA_TYPE = "data_type";
}
