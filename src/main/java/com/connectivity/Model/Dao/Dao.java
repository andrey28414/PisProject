package com.connectivity.Model.Dao;

import com.connectivity.Model.ConnectionPool;
import com.connectivity.Model.Dao.Markers.Column;
import com.connectivity.Model.Dao.Markers.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Dao<T> extends IDao<T>{
    Dao(Class<T> entity, Boolean recreateTable) throws Exception {
        super(entity, recreateTable);
    }

    public static class Builder {
        public static Dao BuildDao(Class<?> entity, Boolean recreateTable) throws Exception {
            return new Dao<>(entity, recreateTable);
        }
    }

    public void recreateTable(Class<T> entity) throws Exception{
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement();
        ){
            String st = entityButcher.parse(entity);
            String tableName = entityButcher.getTableName();
            System.out.println("create table " + tableName + " (" + st + ");");
            statement.execute("drop table if exists " + tableName + " ;");
            statement.execute("create table " + tableName + " (" + st + ");");
        }
    }
    @Override
    public void create(T entity) throws SQLException, IllegalAccessException {
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement()){
            String values = "";
            String names = "";
            int i = 0;
            for (Field field : entityButcher.getFields()){
                if (field.isAnnotationPresent(Column.class)&& !field.getAnnotation(Column.class).isAutoIncrement()){
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if(value != null) {
                        if (i == entityButcher.getFields().size() - 1) {
                            values += "'" + value.toString() + "'";
                            names += entityButcher.getColumnName(field);
                            break;
                        }
                        values += ("'" + value.toString() + "'" + ", ");
                        names += (entityButcher.getColumnName(field) + ", ");
                    }
                }
                i++;
            }
            System.out.println("insert into " + entityButcher.getTableName() + " (" + names + ")" + " values (" + values + ");");
            statement.execute("insert into " + entityButcher.getTableName() + " (" + names + ")" + " values (" + values + ");");

        }
    }

    @Override
    public void delete(T entity) throws Exception {
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement()) {
            String definingColumn = "";
            String definingValue = "";
            for (Field field : entityButcher.getFields()) {
                field.setAccessible(true);
                if (field.get(entity) != null && (field.getAnnotation(Column.class).isUnique() ||
                        field.isAnnotationPresent(Id.class))) {
                    definingColumn = entityButcher.getColumnName(field);
                    definingValue = ("'"+field.get(entity).toString()+"'");
                }
            }
            if (definingColumn.equals("")) throw new Exception("Use unique fields or primary key for deleting!");
            System.out.println("delete from " + entityButcher.getTableName() + " where " + definingColumn + " = " + definingValue + ";");
            statement.execute("delete from " + entityButcher.getTableName() + " where " + definingColumn + " = " + definingValue + ";");

        }

    }

    @Override
    public void update(T entity) throws Exception {
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement()){
            String st = "";
            String definingColumn = "";
            String definingValue = "";
            int i = 0;
            for (Field field : entityButcher.getFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    Object value = field.get(entity);
                    String name = entityButcher.getColumnName(field);
                    if (field.get(entity) != null) {
                        if (field.isAnnotationPresent(Id.class)) {
                            definingColumn = name;
                            definingValue = ("'" + value.toString() + "'");
                            i++;
                            continue;
                        }
                        if (i == entityButcher.getFields().size() - 1) {
                            st += (name+ " = '" + value.toString() + "'");
                            break;
                        }
                        st += (name + " = '" + value.toString() + "', ");
                    }
                }
                i++;
            }
            if (definingColumn.equals("")) throw new Exception("Use primary key in your entity!");


            System.out.println("update '" + entityButcher.getTableName() + "' set " + st + " where " + definingColumn + " = " + definingValue + ";");
            statement.execute("update " + entityButcher.getTableName() + " set " + st + " where " + definingColumn + " = " + definingValue + ";");

        }
    }

    @Override
    public T findBy(T entity) throws Exception {
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement()) {
            String definingColumn = "";
            String definingValue = "";
            for (Field field : entityButcher.getFields()) {
                field.setAccessible(true);
                if (field.get(entity) != null && (field.getAnnotation(Column.class).isUnique() ||
                        field.isAnnotationPresent(Id.class))) {
                    definingColumn = entityButcher.getColumnName(field);
                    definingValue = field.get(entity).toString();
                }
            }
            if (definingColumn.equals("")) throw new Exception("Use unique fields or primary key for searching!");

            System.out.println("select * from "+entityButcher.getTableName()+" where "+definingColumn+" = '"+definingValue+"';");
            ResultSet resultSet = statement.executeQuery("select * from "+entityButcher.getTableName()+" where "+definingColumn+" = '"+definingValue+"';");
            if(resultSet.next()) {
                for (Field field : entityButcher.getFields()) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(entityButcher.getColumnName(field)));
                }
                return entity;
            }
            else throw new Exception("Not found");
        }
    }

    @Override
    public List<T> findAll() throws SQLException, IllegalAccessException, InstantiationException {
        try(Connection connection = ConnectionPool.getDatasource().getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from "+entityButcher.getTableName());
            ArrayList<T> result = new ArrayList<>();

            while (resultSet.next()){
                T entity =  entityClass.newInstance();
                for (Field field : entityButcher.getFields()) {
                    field.setAccessible(true);
                    field.set(entity, resultSet.getObject(entityButcher.getColumnName(field)));
                }
                result.add(entity);
            }
            return result;
        }
    }
}
