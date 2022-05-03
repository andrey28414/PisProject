package com.connectivity.Model.Entity;

import com.connectivity.Model.Dao.Markers.Column;
import com.connectivity.Model.Dao.Markers.Entity;
import com.connectivity.Model.Dao.Markers.Id;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Entity(tableName = "Genre")
public class Genre {
    @Id
    @Column(columnName = "ID",isNotNull = true, isAutoIncrement = true)
    private Integer id;
    @Column(isNotNull = true, isUnique = true)
    private String name;

    @Column(columnName = "date")
    private Date year;

    List<Book> genreBooks;

    public Genre(String name, Date year) {
        this.name = name;
        this.year = year;
    }

    public Genre(Integer id, String name, Date year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Genre() {
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
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
