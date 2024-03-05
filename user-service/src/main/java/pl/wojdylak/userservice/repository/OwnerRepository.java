package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.Owner;
import pl.wojdylak.userservice.domain.dto.OwnerResponseDto;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("SELECT new pl.wojdylak.userservice.domain.dto.OwnerResponseDto(o.id, o.name, o.payUClientId, o.payUClientSecret) " +
            "FROM Owner o " +
            "WHERE o.id = :id ")
    Optional<OwnerResponseDto> findDtoById(@Param("id") Long id);
}
