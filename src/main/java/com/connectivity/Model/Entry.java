package com.connectivity.Model;

import com.connectivity.Model.Dao.Dao;
import com.connectivity.Model.Dao.EntityButcher;
import com.connectivity.Model.Entity.Book;
import com.connectivity.Model.Entity.Genre;
import com.connectivity.Model.Entity.JoinBookGenre;

import java.util.Date;

public class Entry {
    public static void main(String[] args) {
        try {
//            Dao<Book> bookDao = (Dao<Book>) Dao.Builder.BuildDao(Book.class, true);
            Dao<Genre> genreDao = (Dao<Genre>) Dao.Builder.BuildDao(Genre.class, false);
            Dao<JoinBookGenre> joinBookGenreDao = (Dao<JoinBookGenre>) Dao.Builder.BuildDao(JoinBookGenre.class, false);
            Date today = new Date();
            Genre genre = new Genre("genre1", new java.sql.Date(today.getTime()));
            genreDao.create(genre);
            JoinBookGenre joinBookGenre = new JoinBookGenre(2,1);
            joinBookGenreDao.create(joinBookGenre);
//            Date today = new Date();
//            Book book = new Book("kniga4", new java.sql.Date(today.getTime()));
//            bookDao.create(book);
//            bookDao.update(new Book(1,"kniga2", new java.sql.Date(today.getTime())));
//            System.out.println(bookDao.findBy(book));
//            System.out.println(bookDao.findAll());

        }
        catch (Exception e){
                System.out.println(e);}
    }
    }

