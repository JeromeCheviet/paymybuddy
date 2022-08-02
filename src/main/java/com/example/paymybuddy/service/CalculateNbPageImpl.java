package com.example.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manage the operations related to the calculation of the number of page in a tab.
 */
public class CalculateNbPageImpl implements CalculateNbPage {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> pagesList(int totalPages) {
        List<Integer> pagesList = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            pagesList.add(i);
        }
        return pagesList;
    }
}
