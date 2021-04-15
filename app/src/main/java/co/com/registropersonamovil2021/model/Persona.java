package co.com.registropersonamovil2021.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Persona {
    private Integer idPersona;
    private String numeroDocumento;
    private TipoDocumento tipoDocumento;
    private String nombre;
    private String apellido;
    private boolean activo;
}
