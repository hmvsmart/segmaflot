package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AditamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aditamento.class);
        Aditamento aditamento1 = new Aditamento();
        aditamento1.setId(1L);
        Aditamento aditamento2 = new Aditamento();
        aditamento2.setId(aditamento1.getId());
        assertThat(aditamento1).isEqualTo(aditamento2);
        aditamento2.setId(2L);
        assertThat(aditamento1).isNotEqualTo(aditamento2);
        aditamento1.setId(null);
        assertThat(aditamento1).isNotEqualTo(aditamento2);
    }
}
