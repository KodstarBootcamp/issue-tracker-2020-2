package com.kodstar.issuetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private long id;
    @NotBlank(message = "invalid input, Username  can't be null")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email
    private String email;




}
