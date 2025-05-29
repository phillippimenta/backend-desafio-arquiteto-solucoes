package br.com.phillippimenta.desafio.leadmagnet.infrastructure.persistence;

import br.com.phillippimenta.desafio.leadmagnet.domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
