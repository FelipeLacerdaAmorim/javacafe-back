package com.cafeteria.java_cafe.service.cliente;

import com.cafeteria.java_cafe.dto.auth.ClienteRequestDTO;
import com.cafeteria.java_cafe.dto.auth.ClienteResponseDTO;
import com.cafeteria.java_cafe.model.Cliente;
import com.cafeteria.java_cafe.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteBO {

    private final ClienteRepository clienteRepo;

    public ClienteResponseDTO criarCliente(
            ClienteRequestDTO dto) {
        if ((clienteRepo).existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        // demais campos

        Cliente salvo = clienteRepo.save(cliente);

        return toResponseDTO(salvo);
    }

    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());

        Cliente salvo = clienteRepo.save(cliente);
        return toResponseDTO(salvo);
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return toResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> listarTodos() {
        List<Cliente> clientes = clienteRepo.findAll();
        return clientes.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }
}