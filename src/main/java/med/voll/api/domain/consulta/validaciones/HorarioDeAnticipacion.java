package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

public class HorarioDeAnticipacion {

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var horaActual = LocalDateTime.now();
        var horaDeConsulta = datosAgendarConsulta.fecha();
        var diferenciade30Min = Duration.between(horaActual, horaDeConsulta).toMinutes() < 30;

        if (diferenciade30Min){
            throw new ValidationException("La consulta debe programarse con al menos 30 min de anticipación");
        }

    }
}
