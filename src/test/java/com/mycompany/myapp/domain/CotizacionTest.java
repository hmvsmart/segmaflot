package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CotizacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cotizacion.class);
        Cotizacion cotizacion1 = new Cotizacion();
        cotizacion1.setId(1L);
        Cotizacion cotizacion2 = new Cotizacion();
        cotizacion2.setId(cotizacion1.getId());
        assertThat(cotizacion1).isEqualTo(cotizacion2);
        cotizacion2.setId(2L);
        assertThat(cotizacion1).isNotEqualTo(cotizacion2);
        cotizacion1.setId(null);
        assertThat(cotizacion1).isNotEqualTo(cotizacion2);
    }
}
