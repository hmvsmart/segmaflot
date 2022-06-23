package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonaMoralTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonaMoral.class);
        PersonaMoral personaMoral1 = new PersonaMoral();
        personaMoral1.setId(1L);
        PersonaMoral personaMoral2 = new PersonaMoral();
        personaMoral2.setId(personaMoral1.getId());
        assertThat(personaMoral1).isEqualTo(personaMoral2);
        personaMoral2.setId(2L);
        assertThat(personaMoral1).isNotEqualTo(personaMoral2);
        personaMoral1.setId(null);
        assertThat(personaMoral1).isNotEqualTo(personaMoral2);
    }
}
