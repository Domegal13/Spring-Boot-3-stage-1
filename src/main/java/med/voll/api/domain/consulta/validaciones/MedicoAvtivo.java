package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;

public class MedicoAvtivo {

    private MedicoRepository medicoRepository;
    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        if(datosAgendarConsulta.idMedico() == null){
            return;
        }

        var medicoActivo = medicoRepository.findActivoById(datosAgendarConsulta.idMedico());

        if (!medicoActivo){
            throw new Validat   ionException("No se puede agendar citas con médicos inactivos en el sistema");
        }

    }

}
