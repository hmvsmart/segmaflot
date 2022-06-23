package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UbicacionInventarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UbicacionInventario.class);
        UbicacionInventario ubicacionInventario1 = new UbicacionInventario();
        ubicacionInventario1.setId(1L);
        UbicacionInventario ubicacionInventario2 = new UbicacionInventario();
        ubicacionInventario2.setId(ubicacionInventario1.getId());
        assertThat(ubicacionInventario1).isEqualTo(ubicacionInventario2);
        ubicacionInventario2.setId(2L);
        assertThat(ubicacionInventario1).isNotEqualTo(ubicacionInventario2);
        ubicacionInventario1.setId(null);
        assertThat(ubicacionInventario1).isNotEqualTo(ubicacionInventario2);
    }
}
