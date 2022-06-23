package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerdidaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perdida.class);
        Perdida perdida1 = new Perdida();
        perdida1.setId(1L);
        Perdida perdida2 = new Perdida();
        perdida2.setId(perdida1.getId());
        assertThat(perdida1).isEqualTo(perdida2);
        perdida2.setId(2L);
        assertThat(perdida1).isNotEqualTo(perdida2);
        perdida1.setId(null);
        assertThat(perdida1).isNotEqualTo(perdida2);
    }
}
