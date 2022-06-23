package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecioVentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrecioVenta.class);
        PrecioVenta precioVenta1 = new PrecioVenta();
        precioVenta1.setId(1L);
        PrecioVenta precioVenta2 = new PrecioVenta();
        precioVenta2.setId(precioVenta1.getId());
        assertThat(precioVenta1).isEqualTo(precioVenta2);
        precioVenta2.setId(2L);
        assertThat(precioVenta1).isNotEqualTo(precioVenta2);
        precioVenta1.setId(null);
        assertThat(precioVenta1).isNotEqualTo(precioVenta2);
    }
}
