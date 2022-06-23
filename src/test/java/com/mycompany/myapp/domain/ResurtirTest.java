package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResurtirTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resurtir.class);
        Resurtir resurtir1 = new Resurtir();
        resurtir1.setId(1L);
        Resurtir resurtir2 = new Resurtir();
        resurtir2.setId(resurtir1.getId());
        assertThat(resurtir1).isEqualTo(resurtir2);
        resurtir2.setId(2L);
        assertThat(resurtir1).isNotEqualTo(resurtir2);
        resurtir1.setId(null);
        assertThat(resurtir1).isNotEqualTo(resurtir2);
    }
}
