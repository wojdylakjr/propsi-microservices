package pl.wojdylak.propertyservice.service;

import org.springframework.stereotype.Component;
import pl.wojdylak.propertyservice.domain.Meter;
import pl.wojdylak.propertyservice.domain.Premises;
import pl.wojdylak.propertyservice.domain.PremisesCost;

import java.util.Set;

@Component
public class PremisesCreator {
    public Premises create(Premises premises) {
        addCosts(premises);
        addMeters(premises);
        return premises;
    }

    private void addCosts(Premises premises) {
        Set<PremisesCost> premisesCosts = premises.getPremisesCosts();

        if (premisesCosts == null) {
            return;
        }

        premisesCosts.forEach(
                premisesCost -> premisesCost.setPremises(premises)
        );
    }

    private void addMeters(Premises premises) {
        Set<Meter> meters = premises.getMeters();

        if (meters == null) {
            return;
        }

        meters.forEach(
                meter -> meter.setPremises(premises)
        );
    }
}
