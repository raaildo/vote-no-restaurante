package br.com.chico.votenorestaurante.model.repository;

import br.com.chico.votenorestaurante.model.entity.User;
import br.com.chico.votenorestaurante.model.entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Francisco Almeida
 * @since 11/04/2016
 */
public interface UserVoteRepository extends JpaRepository<UserVote, Long> {
    @Query("SELECT v FROM UserVote v JOIN FETCH v.restaurant WHERE v.user = (:user)")
    List<UserVote> findByUser(@Param("user") User user);

    @Query("SELECT v.restaurant as restaurant , sum(v.total) as total FROM UserVote v INNER JOIN v.restaurant r GROUP BY r.name")
    List<UserVote> findByTotal();
}
