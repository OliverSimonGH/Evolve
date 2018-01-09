package com.nsa.evolve.controller;

import com.nsa.evolve.dto.Question;
import com.nsa.evolve.dto.Score;
import com.nsa.evolve.form.ScoreForm;
import com.nsa.evolve.service.QuestionService;
import com.nsa.evolve.service.ScoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(QuestionnaireController.class)
public class QuestionnaireControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private ScoreService scoreService;

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

    @Test
    public void getQuestionsForQuestionnaireAPI() throws Exception {
        List<Question> results = new ArrayList<>(Arrays.asList(
                new Question(1, "test1", 1),
                new Question(2, "test2", 1)
        ));

        when(questionService.findAllQuestionsByQuestionnaire(1)).thenReturn(results);
        this.mvc.perform(get("/api/questionnaire?id=1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].question", is("test1")))
                .andExpect(jsonPath("$.[1].question", is("test2")));
    }

    @Test
    public void insertScoreFromQuestionAPI() throws Exception {
        String formResult = this.json(new ScoreForm(4, "", 1, 1, 1));

        this.mvc.perform(post("/api/questionnaire/question")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(formResult))
                .andExpect(status().isOk());
    }

//  https://spring.io/guides/tutorials/bookmarks/
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}