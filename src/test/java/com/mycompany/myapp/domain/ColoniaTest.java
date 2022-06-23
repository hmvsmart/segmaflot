package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ColoniaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colonia.class);
        Colonia colonia1 = new Colonia();
        colonia1.setId(1L);
        Colonia colonia2 = new Colonia();
        colonia2.setId(colonia1.getId());
        assertThat(colonia1).isEqualTo(colonia2);
        colonia2.setId(2L);
        assertThat(colonia1).isNotEqualTo(colonia2);
        colonia1.setId(null);
        assertThat(colonia1).isNotEqualTo(colonia2);
    }
}
