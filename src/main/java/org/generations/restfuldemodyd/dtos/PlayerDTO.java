package org.generations.restfuldemodyd.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Integer id;

    @NotBlank(message = "Campo obligatorio")
    private String name;

    @NotBlank(message = "Campo obligatorio")
    private String race;

    @NotBlank(message = "Campo obligatorio")
    @Positive(message = "No puede tener un valor negativo")
    @Size(max = 99, message = "No puede tener un nivel mayor a 99")
    private Integer level;

    private Integer jobId;
}
