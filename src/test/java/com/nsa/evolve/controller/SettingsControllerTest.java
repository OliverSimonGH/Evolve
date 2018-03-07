package com.nsa.evolve.controller;

import com.nsa.evolve.form.PasswordForm;
import com.nsa.evolve.service.AccountService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by c1633899 on 08/01/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SettingsController.class)
public class SettingsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    //  https://spring.io/guides/tutorials/bookmarks/
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


//    @Test
//    public void ModifyPassword() throws Exception {
//        String passwordform = this.json(new PasswordForm("hello", "newer"));
//
//        when(accountService.changePassword("hello", "new", 1)).thenReturn(true);
//        mvc.perform(put("/account/updatePassword")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(json(passwordform)))
//                .andExpect(status().isOk());
//    }

    @Test
    public void SettingsDashboardView() throws Exception {
        this.mvc.perform(get("/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("webpage/settings"));
    }

    //  https://spring.io/guides/tutorials/bookmarks/
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}