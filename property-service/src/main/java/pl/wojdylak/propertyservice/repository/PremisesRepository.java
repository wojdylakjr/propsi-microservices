package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wojdylak.propertyservice.domain.Premises;
import pl.wojdylak.propertyservice.domain.dto.PremisesCostsDetailsResponseDto;

import java.util.List;

@Repository
public interface PremisesRepository extends JpaRepository<Premises, Long> {

    @Query("SELECT new pl.wojdylak.propertyservice.domain.dto.PremisesCostsDetailsResponseDto(pcd.costValue, pcd.unit, pcd.date, pc.costType) " +
            "FROM Premises p " +
            "JOIN p.premisesCosts pc " +
            "JOIN pc.premisesCostDetails pcd " +
            "WHERE p.id = :premisesId " +
            "AND EXTRACT(MONTH FROM pcd.date) = :month")
    List<PremisesCostsDetailsResponseDto> findPremisesCostsWithDetailsByPremisesIdAndMonth(@Param("premisesId") Long premisesId,
                                                                                           @Param("month") int month);
}
