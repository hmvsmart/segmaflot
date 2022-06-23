package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LicenciaManejoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenciaManejo.class);
        LicenciaManejo licenciaManejo1 = new LicenciaManejo();
        licenciaManejo1.setId(1L);
        LicenciaManejo licenciaManejo2 = new LicenciaManejo();
        licenciaManejo2.setId(licenciaManejo1.getId());
        assertThat(licenciaManejo1).isEqualTo(licenciaManejo2);
        licenciaManejo2.setId(2L);
        assertThat(licenciaManejo1).isNotEqualTo(licenciaManejo2);
        licenciaManejo1.setId(null);
        assertThat(licenciaManejo1).isNotEqualTo(licenciaManejo2);
    }
}
