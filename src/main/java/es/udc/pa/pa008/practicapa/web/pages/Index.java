package es.udc.pa.pa008.practicapa.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import es.udc.pa.pa008.practicapa.web.util.UserSession;

public class Index {

    @Property
    @SessionState(create = false)
    private UserSession userSession;

}
