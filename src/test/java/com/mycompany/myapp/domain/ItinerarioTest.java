package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItinerarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Itinerario.class);
        Itinerario itinerario1 = new Itinerario();
        itinerario1.setId(1L);
        Itinerario itinerario2 = new Itinerario();
        itinerario2.setId(itinerario1.getId());
        assertThat(itinerario1).isEqualTo(itinerario2);
        itinerario2.setId(2L);
        assertThat(itinerario1).isNotEqualTo(itinerario2);
        itinerario1.setId(null);
        assertThat(itinerario1).isNotEqualTo(itinerario2);
    }
}
