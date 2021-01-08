package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class PromotionProductionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PromotionProduction.class);
        PromotionProduction promotionProduction1 = new PromotionProduction();
        promotionProduction1.setId(1L);
        PromotionProduction promotionProduction2 = new PromotionProduction();
        promotionProduction2.setId(promotionProduction1.getId());
        assertThat(promotionProduction1).isEqualTo(promotionProduction2);
        promotionProduction2.setId(2L);
        assertThat(promotionProduction1).isNotEqualTo(promotionProduction2);
        promotionProduction1.setId(null);
        assertThat(promotionProduction1).isNotEqualTo(promotionProduction2);
    }
}
