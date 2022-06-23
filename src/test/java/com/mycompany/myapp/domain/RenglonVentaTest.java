package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RenglonVentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RenglonVenta.class);
        RenglonVenta renglonVenta1 = new RenglonVenta();
        renglonVenta1.setId(1L);
        RenglonVenta renglonVenta2 = new RenglonVenta();
        renglonVenta2.setId(renglonVenta1.getId());
        assertThat(renglonVenta1).isEqualTo(renglonVenta2);
        renglonVenta2.setId(2L);
        assertThat(renglonVenta1).isNotEqualTo(renglonVenta2);
        renglonVenta1.setId(null);
        assertThat(renglonVenta1).isNotEqualTo(renglonVenta2);
    }
}
