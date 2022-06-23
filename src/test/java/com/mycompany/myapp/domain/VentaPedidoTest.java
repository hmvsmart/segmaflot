package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VentaPedidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaPedido.class);
        VentaPedido ventaPedido1 = new VentaPedido();
        ventaPedido1.setId(1L);
        VentaPedido ventaPedido2 = new VentaPedido();
        ventaPedido2.setId(ventaPedido1.getId());
        assertThat(ventaPedido1).isEqualTo(ventaPedido2);
        ventaPedido2.setId(2L);
        assertThat(ventaPedido1).isNotEqualTo(ventaPedido2);
        ventaPedido1.setId(null);
        assertThat(ventaPedido1).isNotEqualTo(ventaPedido2);
    }
}
