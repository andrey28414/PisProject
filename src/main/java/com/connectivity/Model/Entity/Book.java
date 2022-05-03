package com.connectivity.Model.Entity;

import com.connectivity.Model.Dao.Markers.Column;
import com.connectivity.Model.Dao.Markers.Entity;
import com.connectivity.Model.Dao.Markers.Id;

import java.sql.Date;
import java.util.List;


@Entity(tableName = "Book")
public class Book {
    @Id
    @Column(columnName = "ID",isNotNull = true, isAutoIncrement = true)
    private Integer id;
    @Column(isNotNull = true, isUnique = true)
    private String name;

    @Column(columnName = "date")
    private Date year;

    List<Genre> bookGenres;

    public Book(String name, Date year) {
        this.name = name;
        this.year = year;
    }

    public Book(Integer id, String name, Date year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Book() {
    }

    public Book(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
