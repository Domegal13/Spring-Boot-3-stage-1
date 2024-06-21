package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class PacienteSinConsulta implements ValidadorDeConsultas {
    @Component
    private ConsultaRepository consultaRepository;
    @Autowired
    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        var primerHorario = datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);
        var pacienteConConsulta = consultaRepository.existsPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(), primerHorario, ultimoHorario);
        if (pacienteConConsulta){
            throw new ValidationException("El paciente ya tiene una consulta para este día");
        }

    }
}
