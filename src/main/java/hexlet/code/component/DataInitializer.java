package hexlet.code.component;

import hexlet.code.dto.LabelCreateDTO;
import hexlet.code.dto.TaskStatusCreateDTO;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.CustomUserDetailsService;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final TaskStatusRepository taskStatusRepository;
    private final CustomUserDetailsService userDetailsService;
    private final TaskStatusService taskStatusService;
    private final LabelService labelService;
    private final LabelRepository labelRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPasswordDigest("qwerty");
        userDetailsService.createUser(userData);

        Map<String, String> taskStatuses = Map.of(
                "Draft", "draft",
                "toReview", "to_review",
                "toBeFixed", "to_be_fixed",
                "toPublish", "to_publish",
                "Published", "published"
        );
        //taskStatuses.forEach((key, value) -> taskStatusService.create(new TaskStatusCreateDTO(key, value)));
        var currentTaskStatusesSlug = taskStatusRepository.findAll().stream()
                .map(t -> t.getSlug())
                .toList();
        taskStatuses.keySet().stream()
                .filter(k -> !currentTaskStatusesSlug.contains(taskStatuses.get(k)))
                .forEach(k -> taskStatusService.create(new TaskStatusCreateDTO(k, taskStatuses.get(k))));

        List<String> labels = List.of("feature", "bug");

        var currentLabels = labelRepository.findAll().stream()
                .map(l -> l.getName())
                .toList();
        labels.stream()
                .filter(n -> !currentLabels.contains(n))
                .forEach(l -> labelService.create(new LabelCreateDTO(l)));
    }
}
