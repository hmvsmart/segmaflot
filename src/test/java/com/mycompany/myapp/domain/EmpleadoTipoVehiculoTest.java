package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpleadoTipoVehiculoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpleadoTipoVehiculo.class);
        EmpleadoTipoVehiculo empleadoTipoVehiculo1 = new EmpleadoTipoVehiculo();
        empleadoTipoVehiculo1.setId(1L);
        EmpleadoTipoVehiculo empleadoTipoVehiculo2 = new EmpleadoTipoVehiculo();
        empleadoTipoVehiculo2.setId(empleadoTipoVehiculo1.getId());
        assertThat(empleadoTipoVehiculo1).isEqualTo(empleadoTipoVehiculo2);
        empleadoTipoVehiculo2.setId(2L);
        assertThat(empleadoTipoVehiculo1).isNotEqualTo(empleadoTipoVehiculo2);
        empleadoTipoVehiculo1.setId(null);
        assertThat(empleadoTipoVehiculo1).isNotEqualTo(empleadoTipoVehiculo2);
    }
}
