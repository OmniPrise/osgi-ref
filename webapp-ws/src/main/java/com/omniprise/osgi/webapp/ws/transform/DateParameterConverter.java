package com.omniprise.osgi.webapp.ws.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;

public class DateParameterConverter implements ParamConverter<Date> {
    public static final String format = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Override
    public Date fromString(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(string);
        } catch (ParseException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public String toString(Date t) {
        return new SimpleDateFormat(format).format(t);
    }
}
