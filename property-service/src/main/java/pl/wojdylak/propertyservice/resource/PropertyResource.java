package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.domain.dto.PropertyResponseDto;
import pl.wojdylak.propertyservice.service.PropertyService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyResource {
    private final PropertyService propertyService;

    @PostMapping()
    public PropertyResponseDto createProperty(@RequestBody Property property) {
        return propertyService.save(property);
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Long id) {
        return propertyService.findById(id);
    }

    @GetMapping()
    public List<PropertyResponseDto> getAllProperties() {
        return propertyService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletePropertyById(@PathVariable Long id) {
        propertyService.deleteById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        propertyService.deleteAll();
    }

}
