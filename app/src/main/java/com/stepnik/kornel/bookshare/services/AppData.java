package com.stepnik.kornel.bookshare.services;

import com.stepnik.kornel.bookshare.models.Book;
import com.stepnik.kornel.bookshare.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Path;

/**
 * Created by korSt on 03.11.2016.
 */

public class AppData{

    public static User loggedUser;

    public static Book[] booksArray = {new Book("Tytul", "Autor"), new Book("Tytul1", "Autor"),
            new Book("Tytul", "Autor"), new Book("Tytul1", "Autor"),
            new Book("Tytul", "Autor"), new Book("Tytul1", "Autor")};

    public static ArrayList<Book> getBookList() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Tytul", "Autor") {{
            setLocalLat(51.30);
            setLocalLon(21.06);
        }});
        books.add(new Book("Tytul1", "Autor") {{
            setLocalLat(51.26);
            setLocalLon(21.24);
        }});
        return books;
    }
}
