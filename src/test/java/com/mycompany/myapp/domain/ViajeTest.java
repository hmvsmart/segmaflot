package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ViajeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Viaje.class);
        Viaje viaje1 = new Viaje();
        viaje1.setId(1L);
        Viaje viaje2 = new Viaje();
        viaje2.setId(viaje1.getId());
        assertThat(viaje1).isEqualTo(viaje2);
        viaje2.setId(2L);
        assertThat(viaje1).isNotEqualTo(viaje2);
        viaje1.setId(null);
        assertThat(viaje1).isNotEqualTo(viaje2);
    }
}
