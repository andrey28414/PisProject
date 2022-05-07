package com.connectivity.Model.Dao;


import com.connectivity.Model.Dao.Markers.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class EntityButcher {
    private String tableName;
    private ArrayList<Field> fields = new ArrayList<>();

    public String parse(Class<?> entity) throws Exception{
        tableName = entity.getAnnotation(Entity.class).tableName().equals("") ?
                entity.getSimpleName():entity.getAnnotation(Entity.class).tableName();
        String result = "";
        String primaryKey = "";
        String uniqueKeys = "";
        String foreignKeys = "";
        for(Field f: entity.getDeclaredFields()){
            if(f.isAnnotationPresent(Column.class)){
                fields.add(f);
                Column columnAnnotation = f.getAnnotation(Column.class);
                String definedType = defineType(f);

                String name = getColumnName(f);

                String type = columnAnnotation.columnType().equals("null")? definedType : columnAnnotation.columnType();

                String isNull = columnAnnotation.isNotNull() ? " not null" : "";

                String isAutoIncrement = columnAnnotation.isAutoIncrement() ? " auto_increment" : "";

                result += (" " + name + " " + type + isNull + isAutoIncrement + ",");

                if (primaryKey.equals("") && f.isAnnotationPresent(Id.class) &&
                        (definedType.equals("int") || definedType.equals("bigint"))){
                    primaryKey = " primary key (" + name + ")";
                }
                uniqueKeys += columnAnnotation.isUnique() ? (uniqueKeys.equals("")?"":",")+" unique key ("+name+")" : "";

                if (f.isAnnotationPresent(JoinField.class)){
                    JoinField fieldAnnotation = f.getAnnotation(JoinField.class);
                    Class<?> referencedClass = fieldAnnotation.referencedClass();
                    String referencedClassName = referencedClass.getAnnotation(Entity.class).tableName().equals("") ?
                            referencedClass.getSimpleName():referencedClass.getAnnotation(Entity.class).tableName();
                    foreignKeys += (foreignKeys.equals("")?"":",")+" foreign key ("+name+") references "+referencedClassName+" ("+fieldAnnotation.columnName()+") on delete "+fieldAnnotation.onDelete().toString();
                }

            }
        }
        if (primaryKey.equals("") && !entity.isAnnotationPresent(JoinTable.class)) throw new Exception("Mark identifier for entity "+entity.getName());
        return result + (primaryKey + (primaryKey.equals("")||uniqueKeys.equals("")?"":",") + uniqueKeys
                + ((uniqueKeys.equals("")&&primaryKey.equals(""))||foreignKeys.equals("")?"":",") + foreignKeys);
    }

    public void fastParse(Class<?> entity){
        tableName = entity.getAnnotation(Entity.class).tableName().equals("") ?
                entity.getSimpleName():entity.getAnnotation(Entity.class).tableName();

        for(Field f: entity.getDeclaredFields()){
            if(f.isAnnotationPresent(Column.class)) {
                fields.add(f);
            }
        }
    }

    public static String defineType(Field field) throws Exception{
        Type type = field.getType();

        if (type.equals(int.class) || type.equals(double.class))
            throw new Exception("Don't use primitives as fields types!" + field.getName());

        if (type.equals(Integer.class)){
            return "int";
        }

        if (type.equals(Double.class)){
            return "bigint";
        }

        if (type.equals(String.class)){
            return "varchar(255)";
        }

        if (type.equals(Date.class)){
            return "date";
        }
        else throw new Exception("Unsupported column type for column "+field.getName());
    }

    public String getColumnName(Field f) {
        Column columnAnnotation = f.getAnnotation(Column.class);
        return columnAnnotation.columnName().equals("null")? f.getName() : columnAnnotation.columnName();
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }
}
