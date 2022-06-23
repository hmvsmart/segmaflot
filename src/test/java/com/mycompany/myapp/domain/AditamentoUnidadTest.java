package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AditamentoUnidadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AditamentoUnidad.class);
        AditamentoUnidad aditamentoUnidad1 = new AditamentoUnidad();
        aditamentoUnidad1.setId(1L);
        AditamentoUnidad aditamentoUnidad2 = new AditamentoUnidad();
        aditamentoUnidad2.setId(aditamentoUnidad1.getId());
        assertThat(aditamentoUnidad1).isEqualTo(aditamentoUnidad2);
        aditamentoUnidad2.setId(2L);
        assertThat(aditamentoUnidad1).isNotEqualTo(aditamentoUnidad2);
        aditamentoUnidad1.setId(null);
        assertThat(aditamentoUnidad1).isNotEqualTo(aditamentoUnidad2);
    }
}
