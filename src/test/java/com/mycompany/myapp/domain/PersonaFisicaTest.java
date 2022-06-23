package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaFisicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaFisica.class);
        PersonaFisica personaFisica1 = new PersonaFisica();
        personaFisica1.setId(1L);
        PersonaFisica personaFisica2 = new PersonaFisica();
        personaFisica2.setId(personaFisica1.getId());
        assertThat(personaFisica1).isEqualTo(personaFisica2);
        personaFisica2.setId(2L);
        assertThat(personaFisica1).isNotEqualTo(personaFisica2);
        personaFisica1.setId(null);
        assertThat(personaFisica1).isNotEqualTo(personaFisica2);
    }
}
