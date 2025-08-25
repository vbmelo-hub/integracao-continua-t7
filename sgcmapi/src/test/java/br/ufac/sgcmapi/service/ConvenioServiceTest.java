package br.ufac.sgcmapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.ufac.sgcmapi.model.Convenio;
import br.ufac.sgcmapi.repository.ConvenioRepository;

@ExtendWith(MockitoExtension.class)
public class ConvenioServiceTest {

    @Mock
    private ConvenioRepository repo;

    @InjectMocks
    private ConvenioService service;

    private Convenio convenio;
    private List<Convenio> convenios;

    @BeforeEach
    void setUp() {
        convenio = new Convenio();
        convenio.setId(1L);
        
        Convenio convenio1 = new Convenio();
        convenio1.setId(1L);

        Convenio convenio2 = new Convenio();
        convenio2.setId(2L);

        convenios = new ArrayList<>();
        convenios.add(convenio1);
        convenios.add(convenio2);
    }

    @Test
    void testConvenioConsultarTodos() {
        Mockito.when(repo.consultar(""))
            .thenReturn(convenios);
        var registros = service.consultar("");
        assertEquals(2, registros.size());
    }

    @Test
    void testConvenioConsultarPorId() {
        Mockito.when(repo.findById(anyLong()))
            .thenReturn(Optional.of(convenio));
        var registro = service.consultar(1L);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testConvenioConsultarPorIdNaoEncontrado() {
        Mockito.when(repo.findById(anyLong())).thenReturn(Optional.empty());
        var registro = service.consultar(99L);
        assertNull(registro);
    }

    @Test
    void testConvenioConsultarPorTermoBusca() {
        Mockito.when(repo.consultar(anyString()))
            .thenReturn(convenios);
        var result = service.consultar("termo");
        assertEquals(2, result.size());
    }

    @Test
    void testConvenioSave() {
        Mockito.when(repo.save(any(Convenio.class)))
            .thenReturn(convenio);
        var registro = service.salvar(convenio);
        assertNotNull(registro);
        assertEquals(1L, registro.getId());
    }

    @Test
    void testConvenioRemover() {
        service.remover(1L);
        Mockito.verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testConvenioConsultarAtivos() {
        Mockito.when(repo.findByAtivo(anyBoolean())).thenReturn(convenios);
        var registros = service.consultarAtivos();
        assertEquals(2, registros.size());
    }

}
