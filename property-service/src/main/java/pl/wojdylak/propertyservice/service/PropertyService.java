package pl.wojdylak.propertyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.repository.PropertyRepository;
import pl.wojdylak.propertyservice.service.creator.PropertyCreator;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyCreator propertyCreator;

    public Property save(Property property) {

        return propertyRepository.save(propertyCreator.create(property));
    }

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }



}
