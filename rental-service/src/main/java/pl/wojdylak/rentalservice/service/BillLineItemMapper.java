package pl.wojdylak.rentalservice.service;

import lombok.AllArgsConstructor;
import pl.wojdylak.rentalservice.domain.Bill;
import pl.wojdylak.rentalservice.domain.BillLineItem;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.property.client.PremisesCostsDetailsResponseDto;

import java.math.BigDecimal;
import java.util.function.Function;


@AllArgsConstructor
public class BillLineItemMapper implements Function<PremisesCostsDetailsResponseDto, BillLineItem> {
    private final Rental rental;
    private final Bill bill;

    @Override
    public BillLineItem apply(PremisesCostsDetailsResponseDto costsDetailsDto) {
        return BillLineItem.builder()
                .name(costsDetailsDto.costType())
                .price(costsDetailsDto.value().multiply(BigDecimal.valueOf(rental.getCostsPart())))
                .unit(costsDetailsDto.unit())
                .bill(bill)
                .build();
    }
}
