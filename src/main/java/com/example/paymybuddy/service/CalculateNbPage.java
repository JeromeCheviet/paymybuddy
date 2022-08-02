package com.example.paymybuddy.service;

import java.util.List;

/**
 * Interface link to the paging form.
 */
public interface CalculateNbPage {

    /**
     * Methode to create a list with each number based on total pages.
     *
     * @param totalPages Number total of page in a Page<> object.
     * @return A list of Integer that contains all page numbers.
     */
    List<Integer> pagesList(int totalPages);
}
