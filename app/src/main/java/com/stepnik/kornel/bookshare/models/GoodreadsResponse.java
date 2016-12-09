package com.stepnik.kornel.bookshare.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by korSt on 08.12.2016.
 */

@Root(strict=false)
public class GoodreadsResponse {

    @Element(name = "work")
    public static class Work {
        @Element
        int id;
    }


    @Element(name = "search")
    public static class Search {
        @Element
        Results results;
    }
    @Element(name = "results")
    public static class Results {
        @ElementList(name = "results", entry = "work")
        ArrayList<Work> results;
    }

}
