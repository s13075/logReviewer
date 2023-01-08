package com.pjatkInz.logReviewer.dto;

import com.pjatkInz.logReviewer.model.MyRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String emploeeId;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private Set<RoleDto> roles;

    @NotNull
    private boolean enabled;

}
