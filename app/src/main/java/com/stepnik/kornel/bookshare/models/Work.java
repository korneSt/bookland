package com.stepnik.kornel.bookshare.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by korSt on 08.12.2016.
 */

@Root(strict=false)
public class Work {
    @Element
    int id;
}
