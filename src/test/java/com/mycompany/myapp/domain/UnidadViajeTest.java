package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadViajeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadViaje.class);
        UnidadViaje unidadViaje1 = new UnidadViaje();
        unidadViaje1.setId(1L);
        UnidadViaje unidadViaje2 = new UnidadViaje();
        unidadViaje2.setId(unidadViaje1.getId());
        assertThat(unidadViaje1).isEqualTo(unidadViaje2);
        unidadViaje2.setId(2L);
        assertThat(unidadViaje1).isNotEqualTo(unidadViaje2);
        unidadViaje1.setId(null);
        assertThat(unidadViaje1).isNotEqualTo(unidadViaje2);
    }
}
