package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CPTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CP.class);
        CP cP1 = new CP();
        cP1.setId(1L);
        CP cP2 = new CP();
        cP2.setId(cP1.getId());
        assertThat(cP1).isEqualTo(cP2);
        cP2.setId(2L);
        assertThat(cP1).isNotEqualTo(cP2);
        cP1.setId(null);
        assertThat(cP1).isNotEqualTo(cP2);
    }
}
