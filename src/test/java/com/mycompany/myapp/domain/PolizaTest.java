package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolizaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poliza.class);
        Poliza poliza1 = new Poliza();
        poliza1.setId(1L);
        Poliza poliza2 = new Poliza();
        poliza2.setId(poliza1.getId());
        assertThat(poliza1).isEqualTo(poliza2);
        poliza2.setId(2L);
        assertThat(poliza1).isNotEqualTo(poliza2);
        poliza1.setId(null);
        assertThat(poliza1).isNotEqualTo(poliza2);
    }
}
