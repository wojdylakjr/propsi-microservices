package pl.wojdylak.propertyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wojdylak.propertyservice.domain.Property;
import pl.wojdylak.propertyservice.domain.dto.PropertyResponseDto;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT new pl.wojdylak.propertyservice.domain.dto.PropertyResponseDto(p.id, p.name) " +
            "FROM Property p")
    List<PropertyResponseDto> findAllProperties();
}
