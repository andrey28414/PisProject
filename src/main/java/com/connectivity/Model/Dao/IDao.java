package com.connectivity.Model.Dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public abstract class IDao<T> {
    final protected EntityButcher entityButcher = new EntityButcher();
    final protected Class<T> entityClass;

    protected IDao(Class<T> entity, Boolean recreateTable) throws Exception{
        this.entityClass = entity;
        if (recreateTable) recreateTable(entity);
        else entityButcher.fastParse(entity);
    }

    abstract public void recreateTable(Class<T> entity) throws Exception;
    abstract public void create(T entity) throws SQLException, InvocationTargetException, IllegalAccessException;
    abstract public void delete(T entity) throws Exception;
    abstract public void update(T entity) throws Exception;
    abstract public T findBy(T entity) throws Exception;
    abstract public List<T> findAll() throws SQLException, IllegalAccessException, InstantiationException;

}
