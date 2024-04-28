package pl.wojdylak.propertyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.domain.dto.PropertyResponseDto;
import pl.wojdylak.propertyservice.repository.PropertyRepository;
import pl.wojdylak.propertyservice.service.creator.PropertyCreator;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyCreator propertyCreator;

    public PropertyResponseDto save(Property property) {

        Property saved = propertyRepository.save(propertyCreator.create(property));
        return new PropertyResponseDto(saved.getId(), saved.getName());
    }

    public List<PropertyResponseDto> findAll() {
        return propertyRepository.findAllProperties();
    }


    public Property findById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Property not found with ID: " + id));
    }

    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    public void deleteAll() {
        propertyRepository.deleteAll();
    }
}
