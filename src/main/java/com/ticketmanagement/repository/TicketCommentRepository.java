package com.ticketmanagement.repository;


import com.ticketmanagement.model.TicketComment;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {
    
    

	List<TicketComment> findByTicketId(Long ticketId);
}
