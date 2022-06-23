package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OperadorUnidadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperadorUnidad.class);
        OperadorUnidad operadorUnidad1 = new OperadorUnidad();
        operadorUnidad1.setId(1L);
        OperadorUnidad operadorUnidad2 = new OperadorUnidad();
        operadorUnidad2.setId(operadorUnidad1.getId());
        assertThat(operadorUnidad1).isEqualTo(operadorUnidad2);
        operadorUnidad2.setId(2L);
        assertThat(operadorUnidad1).isNotEqualTo(operadorUnidad2);
        operadorUnidad1.setId(null);
        assertThat(operadorUnidad1).isNotEqualTo(operadorUnidad2);
    }
}
