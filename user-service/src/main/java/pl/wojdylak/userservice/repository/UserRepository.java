package pl.wojdylak.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.wojdylak.userservice.domain.User;
import pl.wojdylak.userservice.domain.dto.UserResponseDto;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new pl.wojdylak.userservice.domain.dto.UserResponseDto(u.id, u.firstName, u.lastName, u.email) " +
            "FROM User u")
    List<UserResponseDto> findAllUsers();
}