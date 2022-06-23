package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeccionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seccion.class);
        Seccion seccion1 = new Seccion();
        seccion1.setId(1L);
        Seccion seccion2 = new Seccion();
        seccion2.setId(seccion1.getId());
        assertThat(seccion1).isEqualTo(seccion2);
        seccion2.setId(2L);
        assertThat(seccion1).isNotEqualTo(seccion2);
        seccion1.setId(null);
        assertThat(seccion1).isNotEqualTo(seccion2);
    }
}
