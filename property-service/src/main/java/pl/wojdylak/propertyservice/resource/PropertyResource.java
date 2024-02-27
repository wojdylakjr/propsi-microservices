package pl.wojdylak.propertyservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.service.PropertyService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/properties")
public class PropertyResource {
    private final PropertyService propertyService;

    @PostMapping()
    public void createProperty(@RequestBody Property property) {
        propertyService.save(property);
    }

    @GetMapping()
    public List<Property> getAllProperties() {
        return propertyService.findAll();
    }
}
