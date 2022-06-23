package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estante.class);
        Estante estante1 = new Estante();
        estante1.setId(1L);
        Estante estante2 = new Estante();
        estante2.setId(estante1.getId());
        assertThat(estante1).isEqualTo(estante2);
        estante2.setId(2L);
        assertThat(estante1).isNotEqualTo(estante2);
        estante1.setId(null);
        assertThat(estante1).isNotEqualTo(estante2);
    }
}
