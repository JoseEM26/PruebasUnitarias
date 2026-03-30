package edu.pe.cibertec.infracciones.service;

import edu.pe.cibertec.infracciones.dto.PagoResponseDTO;
import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.model.Pago;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.repository.PagoRepository;
import edu.pe.cibertec.infracciones.service.impl.PagoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private PagoServiceImpl pagoService;

    @Test
    void procesarPago_AplicaDescuento_CuandoFechaEmisionEsHoy() {
        Long multaId = 1L;
        Multa multa = new Multa();
        multa.setId(multaId);
        multa.setMonto(500.00);
        multa.setFechaEmision(LocalDate.now());
        multa.setFechaVencimiento(LocalDate.now().plusDays(30));
        multa.setEstado(EstadoMulta.PENDIENTE);

        when(multaRepository.findById(multaId)).thenReturn(Optional.of(multa));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PagoResponseDTO response = pagoService.procesarPago(multaId);

        assertEquals(400.00, response.getMontoPagado(), "El monto con descuento del 20% debe ser 400.00");
        assertEquals(EstadoMulta.PAGADA, multa.getEstado(), "El estado de la multa debe ser PAGADA");
        verify(multaRepository, times(1)).save(multa);
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    void procesarPago_AplicaRecargo_CuandoFechaVencida() {
        Long multaId = 2L;
        Multa multa = new Multa();
        multa.setId(multaId);
        multa.setMonto(500.00);
        multa.setFechaEmision(LocalDate.now().minusDays(12));
        multa.setFechaVencimiento(LocalDate.now().minusDays(2));
        multa.setEstado(EstadoMulta.PENDIENTE);

        when(multaRepository.findById(multaId)).thenReturn(Optional.of(multa));

        ArgumentCaptor<Pago> pagoCaptor = ArgumentCaptor.forClass(Pago.class);

        pagoService.procesarPago(multaId);

        verify(pagoRepository, times(1)).save(pagoCaptor.capture());
        Pago pagoCapturado = pagoCaptor.getValue();

        assertEquals(75.00, pagoCapturado.getRecargo(), "El recargo por vencimiento debe ser 15% (75.00)");
        assertEquals(0.00, pagoCapturado.getDescuentoAplicado(), "No debe aplicar descuento después de 5 días");
        assertEquals(575.00, pagoCapturado.getMontoPagado(), "El monto total con recargo debe ser 575.00");
        assertEquals(EstadoMulta.PAGADA, multa.getEstado(), "El estado de la multa debe ser PAGADA");
    }
}
