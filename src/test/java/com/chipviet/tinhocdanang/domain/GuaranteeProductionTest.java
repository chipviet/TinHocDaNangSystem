package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class GuaranteeProductionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuaranteeProduction.class);
        GuaranteeProduction guaranteeProduction1 = new GuaranteeProduction();
        guaranteeProduction1.setId(1L);
        GuaranteeProduction guaranteeProduction2 = new GuaranteeProduction();
        guaranteeProduction2.setId(guaranteeProduction1.getId());
        assertThat(guaranteeProduction1).isEqualTo(guaranteeProduction2);
        guaranteeProduction2.setId(2L);
        assertThat(guaranteeProduction1).isNotEqualTo(guaranteeProduction2);
        guaranteeProduction1.setId(null);
        assertThat(guaranteeProduction1).isNotEqualTo(guaranteeProduction2);
    }
}
