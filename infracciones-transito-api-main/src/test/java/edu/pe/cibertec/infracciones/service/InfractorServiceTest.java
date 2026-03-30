package edu.pe.cibertec.infracciones.service;

import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.service.impl.InfractorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfractorServiceTest {

    @Mock
    private InfractorRepository infractorRepository;

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private InfractorServiceImpl infractorService;

    @Test
    void verificarBloqueo_NoBloquea_CuandoMenosDeTresVencidas() {
        Long id = 1L;
        Infractor infractor = new Infractor();
        infractor.setId(id);
        infractor.setBloqueado(false);

        Multa m1 = new Multa();
        m1.setEstado(EstadoMulta.VENCIDA);
        Multa m2 = new Multa();
        m2.setEstado(EstadoMulta.VENCIDA);
        Multa m3 = new Multa();
        m3.setEstado(EstadoMulta.PAGADA);
        Multa m4 = new Multa();
        m4.setEstado(EstadoMulta.PAGADA);
        Multa m5 = new Multa();
        m5.setEstado(EstadoMulta.PAGADA);

        List<Multa> multas = Arrays.asList(m1, m2, m3, m4, m5);

        when(infractorRepository.findById(id)).thenReturn(Optional.of(infractor));
        when(multaRepository.findByInfractorId(id)).thenReturn(multas);

        infractorService.verificarBloqueo(id);

        assertFalse(infractor.isBloqueado());
        verify(infractorRepository, never()).save(any(Infractor.class));
    }
}
