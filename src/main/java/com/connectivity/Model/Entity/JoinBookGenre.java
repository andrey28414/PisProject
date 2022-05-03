package com.connectivity.Model.Entity;

import com.connectivity.Model.Dao.Markers.Column;
import com.connectivity.Model.Dao.Markers.Entity;
import com.connectivity.Model.Dao.Markers.JoinTable;
import com.connectivity.Model.Dao.Markers.JoinField;

@Entity(tableName = "books_genres")
@JoinTable
public class JoinBookGenre {
    @JoinField(referencedClass = Book.class, columnName = "ID", onDelete = JoinField.OnDelete.CASCADE)
    @Column(columnName = "book_id",isNotNull = true)
    Integer book_id;

    @JoinField(referencedClass = Genre.class, columnName = "ID", onDelete = JoinField.OnDelete.CASCADE)
    @Column(columnName = "genre_id",isNotNull = true)
    Integer genre_id;

    public JoinBookGenre(Integer book_id, Integer genre_id) {
        this.book_id = book_id;
        this.genre_id = genre_id;
    }

    public JoinBookGenre() {
    }
}
