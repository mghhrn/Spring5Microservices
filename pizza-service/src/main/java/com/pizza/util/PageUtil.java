package com.pizza.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *    Helper functions to work with ecosystem related with {@link Page} like:
 * {@link PageRequest}, {@link Sort}, etc
 */
@Component
public class PageUtil {

    /**
     * Generates a {@link PageRequest} to use in a database query
     *
     * @param page
     *    Number of page to get
     * @param size
     *    Number of elements in every page
     * @param sort
     *    {@link Sort} with the sorting configuration
     *
     * @return {@link PageRequest}
     */
    public PageRequest buildPageRequest(int page, int size, Sort sort) {
        return Optional.ofNullable(sort)
                       .map(s -> PageRequest.of(page, size, sort))
                       .orElse(PageRequest.of(page, size));
    }

}