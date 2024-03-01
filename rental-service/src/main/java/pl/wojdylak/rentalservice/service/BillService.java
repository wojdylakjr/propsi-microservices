package pl.wojdylak.rentalservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wojdylak.rentalservice.domain.Bill;
import pl.wojdylak.rentalservice.domain.BillLineItem;
import pl.wojdylak.rentalservice.domain.Rental;
import pl.wojdylak.rentalservice.domain.dto.BillResponseDto;
import pl.wojdylak.rentalservice.domain.dto.CreateBillDto;
import pl.wojdylak.rentalservice.property.client.PremisesCostsDetailsResponseDto;
import pl.wojdylak.rentalservice.property.client.PropertyServiceClient;
import pl.wojdylak.rentalservice.repository.BillRepository;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BillService {
    private final PropertyServiceClient propertyServiceClient;
    private final RentalService rentalService;
    private final BillRepository billRepository;

    public List<BillResponseDto> findAll() {
        return billRepository.findAllBillDto();
    }

    public void createBill(CreateBillDto createBillDto) {

        Rental rental = rentalService.findById(createBillDto.rentalId())
                .orElseThrow();

        List<PremisesCostsDetailsResponseDto> premisesCostsInMonth = propertyServiceClient.getPremisesCostsInMonth(rental.getPremisesId(), createBillDto.monthValue());

        Bill bill = new Bill(Instant.now(), rental);

        Set<BillLineItem> billLineItems = premisesCostsInMonth.stream()
                .map(new BillLineItemMapper(rental, bill))
                .collect(Collectors.toSet());

        billLineItems.add(getRentalBillLineItem(rental, bill));

        bill.addBillLineItems(billLineItems);
        billRepository.save(bill);
    }

    private BillLineItem getRentalBillLineItem(Rental rental,
                                               Bill bill) {
        BillLineItem rentalBillLineItem = BillLineItem.builder()
                .price(rental.getRentPrice())
                .name(rental.getName() + "-price")
                .bill(bill)
                .build();


        return rentalBillLineItem;
    }
}
