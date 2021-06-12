package com.menus.checout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto implements Serializable {

    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    private String mobileNumber;

}
