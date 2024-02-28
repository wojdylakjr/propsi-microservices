package pl.wojdylak.propertyservice.service.creator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wojdylak.propertyservice.domain.Address;
import pl.wojdylak.propertyservice.domain.Premises;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.domain.PropertyFixedCost;

import java.util.Set;

@Component
@AllArgsConstructor
public class PropertyCreator {
    private final PremisesCreator premisesCreator;

    public Property create(Property property) {
        addAddress(property);
        addFixedCosts(property);
        addPremises(property);
        return property;
    }

    private void addAddress(Property property) {

        Address address = property.getAddress();

        if (address == null) {
            return;
        }

        address.setProperty(property);
    }

    private void addFixedCosts(Property property) {

        Set<PropertyFixedCost> propertyFixedCosts = property.getFixedCosts();

        if (propertyFixedCosts == null) {
            return;
        }

        propertyFixedCosts.forEach(
                propertyFixedCost -> propertyFixedCost.setProperty(property)
        );
    }

    private void addPremises(Property property) {

        Set<Premises> premises = property.getPremises();
        if (premises == null) {
            return;
        }

        premises.forEach(
                prem -> {
                    premisesCreator.create(prem);
                    prem.setProperty(property);
                }
        );
    }
}
