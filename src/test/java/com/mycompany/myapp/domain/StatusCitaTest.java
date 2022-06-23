package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusCitaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusCita.class);
        StatusCita statusCita1 = new StatusCita();
        statusCita1.setId(1L);
        StatusCita statusCita2 = new StatusCita();
        statusCita2.setId(statusCita1.getId());
        assertThat(statusCita1).isEqualTo(statusCita2);
        statusCita2.setId(2L);
        assertThat(statusCita1).isNotEqualTo(statusCita2);
        statusCita1.setId(null);
        assertThat(statusCita1).isNotEqualTo(statusCita2);
    }
}
