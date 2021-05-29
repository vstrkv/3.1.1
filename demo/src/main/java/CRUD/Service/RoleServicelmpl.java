package CRUD.Service;

import CRUD.Dao.RoleDao;
import CRUD.model.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("roleService")
public class RoleServicelmpl implements RoleService {

    private final RoleDao roleDao;


    public RoleServicelmpl(@Qualifier("roleDao")RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void setRole(Role role) {
        roleDao.setRole(role);
    }
    @Override
    public Set<Role> getAllRoles() {
        Set<Role> roles = roleDao.getAllRoles();
        if (roles.isEmpty()) {
            roleDao.setRole(new Role("USER"));
            roleDao.setRole(new Role("ADMIN"));
        }
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }
}
