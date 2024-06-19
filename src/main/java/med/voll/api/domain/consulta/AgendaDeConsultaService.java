package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;



//    public void agendar(DatosAgendarConsulta datos) {
//        // Verificar si el paciente existe
//        var pacienteOptional = pacienteRepository.findById(datos.idPaciente());
//        if (pacienteOptional.isEmpty()) {
//            throw new ValidacionDeIntegridad("Este ID para el paciente no fue encontrado");
//        }
//
//        // Verificar si el médico existe solo si se proporciona un ID de médico
//        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
//            throw new ValidacionDeIntegridad("Este ID para el médico no fue encontrado");
//        }
//
//        var paciente = pacienteOptional.get();
//        var medico = seleccionarMedico(datos);
//
//        // Crear la entidad Consulta
//        var consulta = new Consulta(null, medico, paciente, datos.fecha());
//
//        // Guardar la consulta en la base de datos
//        consultaRepository.save(consulta);
//    }

    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isEmpty()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

//        var medico = medicoRepository.findById(datos.idMedico()).get();
        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }


        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}