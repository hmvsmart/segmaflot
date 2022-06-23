package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaterialServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialServicio.class);
        MaterialServicio materialServicio1 = new MaterialServicio();
        materialServicio1.setId(1L);
        MaterialServicio materialServicio2 = new MaterialServicio();
        materialServicio2.setId(materialServicio1.getId());
        assertThat(materialServicio1).isEqualTo(materialServicio2);
        materialServicio2.setId(2L);
        assertThat(materialServicio1).isNotEqualTo(materialServicio2);
        materialServicio1.setId(null);
        assertThat(materialServicio1).isNotEqualTo(materialServicio2);
    }
}
