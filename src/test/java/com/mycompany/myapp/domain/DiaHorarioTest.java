package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DiaHorarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiaHorario.class);
        DiaHorario diaHorario1 = new DiaHorario();
        diaHorario1.setId(1L);
        DiaHorario diaHorario2 = new DiaHorario();
        diaHorario2.setId(diaHorario1.getId());
        assertThat(diaHorario1).isEqualTo(diaHorario2);
        diaHorario2.setId(2L);
        assertThat(diaHorario1).isNotEqualTo(diaHorario2);
        diaHorario1.setId(null);
        assertThat(diaHorario1).isNotEqualTo(diaHorario2);
    }
}
