package edu.pe.cibertec.infracciones.service;

import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.service.impl.MultaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MultaServiceTest {

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private MultaServiceImpl multaService;

    @Test
    void actualizarEstados_VenceMultaPendiente_CuandoFechaVencida() {
        Multa multa = new Multa();
        multa.setEstado(EstadoMulta.PENDIENTE);
        multa.setFechaVencimiento(LocalDate.of(2026, 1, 1)); // Vencida

        when(multaRepository.findByEstado(EstadoMulta.PENDIENTE)).thenReturn(Arrays.asList(multa));

        multaService.actualizarEstados();

        assertEquals(EstadoMulta.VENCIDA, multa.getEstado());
        verify(multaRepository, times(1)).save(multa);
    }
}
