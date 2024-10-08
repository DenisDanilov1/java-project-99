package hexlet.code.service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LabelService {

    private LabelRepository repository;
    private LabelMapper labelMapper;

    public List<LabelDTO> getAll() {
        var labels = repository.findAll();
        return labelMapper.map(labels);
    }

    public LabelDTO getById(Long id) {
        var label = repository.findById(id)
                .orElseThrow();
        return labelMapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO labelData) {
        var label = labelMapper.map(labelData);
        repository.save(label);
        return labelMapper.map(label);
    }

    public LabelDTO update(LabelUpdateDTO labelData, Long id) {
        var label = repository.findById(id)
                .orElseThrow();
        labelMapper.update(labelData, label);
        repository.save(label);
        return labelMapper.map(label);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
