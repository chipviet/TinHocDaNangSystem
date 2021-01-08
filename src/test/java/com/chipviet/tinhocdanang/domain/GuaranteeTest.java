package com.chipviet.tinhocdanang.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.chipviet.tinhocdanang.web.rest.TestUtil;

public class GuaranteeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guarantee.class);
        Guarantee guarantee1 = new Guarantee();
        guarantee1.setId(1L);
        Guarantee guarantee2 = new Guarantee();
        guarantee2.setId(guarantee1.getId());
        assertThat(guarantee1).isEqualTo(guarantee2);
        guarantee2.setId(2L);
        assertThat(guarantee1).isNotEqualTo(guarantee2);
        guarantee1.setId(null);
        assertThat(guarantee1).isNotEqualTo(guarantee2);
    }
}
