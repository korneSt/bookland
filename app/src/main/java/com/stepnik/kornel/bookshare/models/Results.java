package com.stepnik.kornel.bookshare.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by korSt on 08.12.2016.
 */

@Root(strict = false)
public class Results {
    @ElementList(name = "results", entry = "work")
    ArrayList<Work> results;
}
