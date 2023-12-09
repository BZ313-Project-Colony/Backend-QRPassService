package com.group1.QRPass.repository;


import com.group1.QRPass.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findAllByEvent_Id(Long eventId);
    Optional<Ticket> findTicketById(Long ticketId);
    Optional<Ticket> findTicketByEvent_IdAndAndParticipantEmail(Long eventId, String email);

    void deleteAllByEvent_Id(Long eventId);
}
