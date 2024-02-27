package pl.wojdylak.propertyservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.propertyservice.domain.Address;
import pl.wojdylak.propertyservice.domain.Premises;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.domain.PropertyFixedCost;
import pl.wojdylak.propertyservice.repository.PropertyRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public Property save(Property property) {

        addAddress(property);
        addFixedCosts(property);
        addPremises(property);
        return propertyRepository.save(property);
    }

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    private void addAddress(Property property) {

        Address address = property.getAddress();

        if (address == null) {
            return;
        }

        address.setProperty(property);
        property.setAddress(address);
    }

    private void addFixedCosts(Property property) {

        Set<PropertyFixedCost> propertyFixedCosts = property.getFixedCosts();

        if (propertyFixedCosts == null) {
            return;
        }

        property.setFixedCosts(propertyFixedCosts);
        propertyFixedCosts.forEach(
                propertyFixedCost -> propertyFixedCost.setProperty(property)
        );
    }

    private void addPremises(Property property) {

        Set<Premises> premises = property.getPremises();
        if (premises == null) {
            return;
        }

        property.setPremises(premises);
        premises.forEach(
                prem -> prem.setProperty(property)
        );
    }

}
