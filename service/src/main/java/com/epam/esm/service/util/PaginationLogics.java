package com.epam.esm.service.util;

import com.epam.esm.service.exception.IllegalPageNumberException;

public class PaginationLogics {

    public static final Integer DEFAULT_LIMIT = 5;

    private static final Integer DEFAULT_PAGE = 1;

    public static Integer convertToOffset(Integer pageNumber) throws IllegalPageNumberException {
        if (pageNumber <= 0) {
            throw new IllegalPageNumberException(ExceptionMessageHandler.INVALID_PAGE_MESSAGE_NAME);
        }
        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE;
        }
        return (pageNumber - 1) * DEFAULT_LIMIT;
    }

}
