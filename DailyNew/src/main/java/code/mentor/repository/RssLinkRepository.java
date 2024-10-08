package code.mentor.repository;

import code.mentor.models.RssLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssLinkRepository extends JpaRepository<RssLink, Integer> {
    RssLink findByLink(String link);
}
