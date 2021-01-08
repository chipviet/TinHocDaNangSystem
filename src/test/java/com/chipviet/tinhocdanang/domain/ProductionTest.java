package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class ProductionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Production.class);
        Production production1 = new Production();
        production1.setId(1L);
        Production production2 = new Production();
        production2.setId(production1.getId());
        assertThat(production1).isEqualTo(production2);
        production2.setId(2L);
        assertThat(production1).isNotEqualTo(production2);
        production1.setId(null);
        assertThat(production1).isNotEqualTo(production2);
    }
}
