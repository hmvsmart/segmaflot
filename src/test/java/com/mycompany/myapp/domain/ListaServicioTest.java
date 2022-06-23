package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ListaServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaServicio.class);
        ListaServicio listaServicio1 = new ListaServicio();
        listaServicio1.setId(1L);
        ListaServicio listaServicio2 = new ListaServicio();
        listaServicio2.setId(listaServicio1.getId());
        assertThat(listaServicio1).isEqualTo(listaServicio2);
        listaServicio2.setId(2L);
        assertThat(listaServicio1).isNotEqualTo(listaServicio2);
        listaServicio1.setId(null);
        assertThat(listaServicio1).isNotEqualTo(listaServicio2);
    }
}
