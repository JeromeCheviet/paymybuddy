package com.example.paymybuddy.service;

import java.util.ArrayList;
import java.util.List;

public class CalculateNbPageImpl implements CalculateNbPage {

    @Override
    public List<Integer> pagesList(int totalPages) {
        List<Integer> pagesList = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            pagesList.add(i);
        }
        return pagesList;
    }
}
