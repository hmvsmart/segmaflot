package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehiculoClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculoCliente.class);
        VehiculoCliente vehiculoCliente1 = new VehiculoCliente();
        vehiculoCliente1.setId(1L);
        VehiculoCliente vehiculoCliente2 = new VehiculoCliente();
        vehiculoCliente2.setId(vehiculoCliente1.getId());
        assertThat(vehiculoCliente1).isEqualTo(vehiculoCliente2);
        vehiculoCliente2.setId(2L);
        assertThat(vehiculoCliente1).isNotEqualTo(vehiculoCliente2);
        vehiculoCliente1.setId(null);
        assertThat(vehiculoCliente1).isNotEqualTo(vehiculoCliente2);
    }
}
