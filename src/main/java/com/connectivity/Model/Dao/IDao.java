package com.connectivity.Model.Dao;

import com.connectivity.Model.ConnectionPool;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class IDao<T> {
    final protected EntityButcher entityButcher = new EntityButcher();
    final protected Class<T> entityClass;
    final protected DataSource dataSource = ConnectionPool.getDatasource();

    protected IDao(Class<T> entity, Boolean recreateTable) throws Exception{
        this.entityClass = entity;
        if (recreateTable) recreateTable(entity);
        else entityButcher.fastParse(entity);
    }

    abstract public void recreateTable(Class<T> entity) throws Exception;
    abstract public List<Boolean> recreateTable(Class<T> entity, Connection connection) throws Exception;
    abstract public void create(T entity) throws SQLException, InvocationTargetException, IllegalAccessException;
    abstract public List<Boolean> create(T entity, Connection connection) throws SQLException, InvocationTargetException, IllegalAccessException;

    abstract public void delete(T entity) throws Exception;
    abstract public List<Boolean> delete(T entity, Connection connection) throws Exception;
    abstract public void update(T entity) throws Exception;
    abstract public List<Boolean> update(T entity, Connection connection) throws Exception;
    abstract public T findBy(T entity) throws Exception;
    abstract public List<T> findBy(T entity, Connection connection) throws Exception;
    abstract public List<T> findAll() throws SQLException, IllegalAccessException, InstantiationException;
    abstract public List<T> findAll(Connection connection) throws SQLException, IllegalAccessException, InstantiationException;
    abstract public List<List<?>> createTransaction(int c, Operation... operations) throws Exception;

}
