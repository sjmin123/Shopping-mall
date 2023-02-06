package me.batchpractice.sjm.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDto {

    private final String id;
    private final String password;
    @Email
    private final String email;

}
