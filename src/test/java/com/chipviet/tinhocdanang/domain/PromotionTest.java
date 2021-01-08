package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class PromotionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Promotion.class);
        Promotion promotion1 = new Promotion();
        promotion1.setId(1L);
        Promotion promotion2 = new Promotion();
        promotion2.setId(promotion1.getId());
        assertThat(promotion1).isEqualTo(promotion2);
        promotion2.setId(2L);
        assertThat(promotion1).isNotEqualTo(promotion2);
        promotion1.setId(null);
        assertThat(promotion1).isNotEqualTo(promotion2);
    }
}
