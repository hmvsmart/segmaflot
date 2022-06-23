package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListaPedidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPedido.class);
        ListaPedido listaPedido1 = new ListaPedido();
        listaPedido1.setId(1L);
        ListaPedido listaPedido2 = new ListaPedido();
        listaPedido2.setId(listaPedido1.getId());
        assertThat(listaPedido1).isEqualTo(listaPedido2);
        listaPedido2.setId(2L);
        assertThat(listaPedido1).isNotEqualTo(listaPedido2);
        listaPedido1.setId(null);
        assertThat(listaPedido1).isNotEqualTo(listaPedido2);
    }
}
