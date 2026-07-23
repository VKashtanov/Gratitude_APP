package ru.kashtanov.user_service.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Viktor Кashtanov
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String portraitUrl;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String state;
    private String country;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDto dto = (UserDto) o;
        return Objects.equals(firstName, dto.firstName) && Objects.equals(lastName, dto.lastName) && Objects.equals(middleName, dto.middleName) && Objects.equals(position, dto.position) && Objects.equals(portraitUrl, dto.portraitUrl) && Objects.equals(phone, dto.phone) && Objects.equals(email, dto.email) && Objects.equals(address, dto.address) && Objects.equals(city, dto.city) && Objects.equals(state, dto.state) && Objects.equals(country, dto.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, position, portraitUrl, phone, email, address, city, state, country);
    }
}
