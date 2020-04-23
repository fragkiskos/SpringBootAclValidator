package acldemo.validation.aclProviding;


import java.util.Optional;

public interface IAclProvider  {
    Long add(Acl acl);
    Acl find(Long id);
    void update(Acl acl);
    void delete(Long id);
    Optional<Long> find(Acl acl);
}
