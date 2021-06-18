package com.menus.checkout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
