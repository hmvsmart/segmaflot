package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RenglonResurtirTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RenglonResurtir.class);
        RenglonResurtir renglonResurtir1 = new RenglonResurtir();
        renglonResurtir1.setId(1L);
        RenglonResurtir renglonResurtir2 = new RenglonResurtir();
        renglonResurtir2.setId(renglonResurtir1.getId());
        assertThat(renglonResurtir1).isEqualTo(renglonResurtir2);
        renglonResurtir2.setId(2L);
        assertThat(renglonResurtir1).isNotEqualTo(renglonResurtir2);
        renglonResurtir1.setId(null);
        assertThat(renglonResurtir1).isNotEqualTo(renglonResurtir2);
    }
}
