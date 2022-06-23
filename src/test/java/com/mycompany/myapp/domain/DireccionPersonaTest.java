package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DireccionPersonaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DireccionPersona.class);
        DireccionPersona direccionPersona1 = new DireccionPersona();
        direccionPersona1.setId(1L);
        DireccionPersona direccionPersona2 = new DireccionPersona();
        direccionPersona2.setId(direccionPersona1.getId());
        assertThat(direccionPersona1).isEqualTo(direccionPersona2);
        direccionPersona2.setId(2L);
        assertThat(direccionPersona1).isNotEqualTo(direccionPersona2);
        direccionPersona1.setId(null);
        assertThat(direccionPersona1).isNotEqualTo(direccionPersona2);
    }
}
