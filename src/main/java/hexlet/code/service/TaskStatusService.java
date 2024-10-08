package hexlet.code.service;

import hexlet.code.dto.taskStatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskStatus.TaskStatusDTO;
import hexlet.code.dto.taskStatus.TaskStatusUpdateDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskStatusService {

    private TaskStatusRepository repository;
    private TaskStatusMapper statusMapper;

    public List<TaskStatusDTO> getAll() {
        var statuses = repository.findAll();
        return statusMapper.map(statuses);
    }

    public TaskStatusDTO getById(Long id) {
        var status = repository.findById(id)
                .orElseThrow();
        return statusMapper.map(status);
    }

    public TaskStatusDTO create(TaskStatusCreateDTO statusData) {
        var status = statusMapper.map(statusData);
        repository.save(status);
        return statusMapper.map(status);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO statusData, Long id) {
        var status = repository.findById(id)
                .orElseThrow();
        statusMapper.update(statusData, status);
        repository.save(status);
        return statusMapper.map(status);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
