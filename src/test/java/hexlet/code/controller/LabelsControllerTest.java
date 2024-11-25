package hexlet.code.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.controller.util.ModelGenerator;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@AutoConfigureMockMvc
class LabelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    private Label testLabel;

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;



    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity())
                .build();

        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testLabel = Instancio.of(modelGenerator.getLabelModel()).create();

        labelRepository.save(testLabel);

    }

    @AfterEach
    public void clean() {
        taskRepository.deleteAll();
        taskStatusRepository.deleteAll();
        labelRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        var count = labelRepository.count();
        var result = mockMvc.perform(get("/api/labels").with(token))
                .andExpect(status().isOk()).andReturn().getResponse();
        var body = result.getContentAsString();
        assertThatJson(body).isArray();
        var labelsDTO = om.readValue(body, new TypeReference<List<LabelDTO>>() { });
        assertThat(labelsDTO.size()).isEqualTo((count));
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(get("/api/labels/" + testLabel.getId()).with(token))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var data = new LabelCreateDTO();
        data.setName("testName");

        var request = post("/api/labels")
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var label = labelRepository.findByName(data.getName()).get();

        assertNotNull(label);
        assertThat(label.getName()).isEqualTo(data.getName());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = new LabelUpdateDTO();
        data.setName(JsonNullable.of("newName"));
        var request = put("/api/labels/" + testLabel.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isOk());

        var updatedLabel = labelRepository.findByName(data.getName().get()).get();

        assertNotNull(updatedLabel);
    }

    @Test
    public void testCreateWithInvalidData() throws Exception {
        var data = new HashMap<String, String>(Map.of(
                "name", "ne"
        ));
        var request = put("/api/labels/" + testLabel.getId())
                .with(token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/labels/" + testLabel.getId())
                        .with(token))
                .andExpect(status().isNoContent());
    }
}
