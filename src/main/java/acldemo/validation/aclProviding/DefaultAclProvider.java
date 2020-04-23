package acldemo.validation.aclProviding;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class DefaultAclProvider implements IAclProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long add(Acl acl) {
        Optional<Long> aclId = this.find(acl);
        if(aclId.isPresent()) {
            return aclId.get();
        }
        return em.merge(acl).getId();
    }

    @Override
    public Acl find(Long id) {
        return em.find(Acl.class,id);
    }

    @Override
    public void update(Acl acl) {
        em.merge(acl);
    }

    @Override
    public void delete(Long id) {
        Acl acl = em.find(Acl.class, id);
        if(acl != null){
            em.remove(acl);
        }

    }

    @Override
    public Optional<Long> find(Acl acl) {
        String findAppointmentQuery = "Select acl from Acl acl" +
                " where acl.organizationId =:organizationId " +
                " and acl.objectId =:objectId" +
                " and acl.className=:className" +
                " and acl.operator=:operator" +
                " and acl.action:=action";
        Query query = em.createQuery(findAppointmentQuery);
        query.setParameter("objectId", acl.getObjectId());
        query.setParameter("className", acl.getClassName());
        query.setParameter("operator", acl.getOperator());
        query.setParameter("action", acl.getAction());
        query.setParameter("organizationId",acl.getOrganizationId());
        Acl foundAcl = (Acl) query.getSingleResult();
        if(foundAcl != null){
            return Optional.of(foundAcl.getId());
        }else{
            return Optional.empty();
        }
    }
}
