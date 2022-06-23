package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecioServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioServicio.class);
        PrecioServicio precioServicio1 = new PrecioServicio();
        precioServicio1.setId(1L);
        PrecioServicio precioServicio2 = new PrecioServicio();
        precioServicio2.setId(precioServicio1.getId());
        assertThat(precioServicio1).isEqualTo(precioServicio2);
        precioServicio2.setId(2L);
        assertThat(precioServicio1).isNotEqualTo(precioServicio2);
        precioServicio1.setId(null);
        assertThat(precioServicio1).isNotEqualTo(precioServicio2);
    }
}
