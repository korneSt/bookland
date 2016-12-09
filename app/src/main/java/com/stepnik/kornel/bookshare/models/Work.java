package com.stepnik.kornel.bookshare.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by korSt on 08.12.2016.
 */

public class Work {

    public Work() {

    }

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    @Element(name = "average_rating")
    int averageRating;

}
