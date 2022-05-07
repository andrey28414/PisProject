package com.connectivity.Model;

import com.connectivity.Model.Dao.Dao;
import com.connectivity.Model.Dao.EntityButcher;
import com.connectivity.Model.Dao.Operation;
import com.connectivity.Model.Entity.Book;
import com.connectivity.Model.Entity.Genre;
import com.connectivity.Model.Entity.JoinBookGenre;

import java.sql.Connection;
import java.util.List;

import java.util.Date;

public class Entry {
    public static void main(String[] args) {
        try {
            Dao<Book> bookDao = (Dao<Book>) Dao.Builder.BuildDao(Book.class, false);
            Dao<Genre> genreDao = (Dao<Genre>) Dao.Builder.BuildDao(Genre.class, false);
            Dao<JoinBookGenre> joinBookGenreDao = (Dao<JoinBookGenre>) Dao.Builder.BuildDao(JoinBookGenre.class, false);
            Date today = new Date();
            Genre genre = new Genre("genre12", new java.sql.Date(today.getTime()));
            Book book = new Book("kniga4", new java.sql.Date(today.getTime()));
            Genre genre1 = new Genre(2,"genre12_new", new java.sql.Date(today.getTime()));
//            genreDao.create(genre);
//            bookDao.create(book);
//            joinBookGenreDao.delete(new JoinBookGenre(1,1));

//            joinBookGenreDao.create(new JoinBookGenre(()))
//            JoinBookGenre joinBookGenre = new JoinBookGenre(2,1);
////            joinBookGenreDao.create(joinBookGenre);
//
            System.out.println(genreDao.createTransaction(Connection.TRANSACTION_READ_COMMITTED,
                    c -> genreDao.findBy(genre, c),
                    c -> genreDao.update(genre1, c),
                    c -> genreDao.findBy(new Genre(100, "genre123", new java.sql.Date(today.getTime())), c),
                    c -> bookDao.create(book, c),
                    c -> genreDao.findAll(c)));
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

