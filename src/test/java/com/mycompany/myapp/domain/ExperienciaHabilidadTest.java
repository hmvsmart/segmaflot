package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExperienciaHabilidadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienciaHabilidad.class);
        ExperienciaHabilidad experienciaHabilidad1 = new ExperienciaHabilidad();
        experienciaHabilidad1.setId(1L);
        ExperienciaHabilidad experienciaHabilidad2 = new ExperienciaHabilidad();
        experienciaHabilidad2.setId(experienciaHabilidad1.getId());
        assertThat(experienciaHabilidad1).isEqualTo(experienciaHabilidad2);
        experienciaHabilidad2.setId(2L);
        assertThat(experienciaHabilidad1).isNotEqualTo(experienciaHabilidad2);
        experienciaHabilidad1.setId(null);
        assertThat(experienciaHabilidad1).isNotEqualTo(experienciaHabilidad2);
    }
}
