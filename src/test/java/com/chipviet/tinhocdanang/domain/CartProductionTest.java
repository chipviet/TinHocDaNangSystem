package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class CartProductionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartProduction.class);
        CartProduction cartProduction1 = new CartProduction();
        cartProduction1.setId(1L);
        CartProduction cartProduction2 = new CartProduction();
        cartProduction2.setId(cartProduction1.getId());
        assertThat(cartProduction1).isEqualTo(cartProduction2);
        cartProduction2.setId(2L);
        assertThat(cartProduction1).isNotEqualTo(cartProduction2);
        cartProduction1.setId(null);
        assertThat(cartProduction1).isNotEqualTo(cartProduction2);
    }
}
