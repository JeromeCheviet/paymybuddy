package com.example.paymybuddy.service;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Service
class CalculateNbPageImplTest {

    CalculateNbPage calculateNbPage = new CalculateNbPageImpl();

    @Test
    void testPagesListWithOnePage() {
        List<Integer> pages = calculateNbPage.pagesList(1);

        assertThat(pages)
                .hasSize(1)
                .contains(1);
    }

    @Test
    void testPagesListWithTenPages() {
        List<Integer> pages = calculateNbPage.pagesList(10);

        assertThat(pages)
                .hasSize(10)
                .contains(5);

    }

}