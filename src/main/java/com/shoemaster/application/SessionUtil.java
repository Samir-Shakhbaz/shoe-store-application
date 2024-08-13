package com.shoemaster.application.utils;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class SessionUtil {

    @SuppressWarnings("unchecked")
    public static <T> List<T> getSessionAttribute(HttpSession session, String name, Class<T> type) {
        Object attribute = session.getAttribute(name);
        if (attribute instanceof List<?>) {
            return (List<T>) attribute;
        } else {
            return new ArrayList<>();
        }
    }
}
