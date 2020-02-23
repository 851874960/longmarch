package top.longmarch.sys.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.longmarch.core.utils.tree.TreeUtil;
import top.longmarch.sys.dao.PermissionDao;
import top.longmarch.sys.entity.Permission;
import top.longmarch.sys.entity.vo.PermissionTree;
import top.longmarch.sys.service.IPermissionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限信息 服务实现类
 * </p>
 *
 * @author YuYue
 * @since 2020-01-12
 */
@CacheConfig(cacheNames = {"PermissionService"})
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements IPermissionService {

    @Cacheable(key = "'permission_tree'")
    @Override
    public JSONObject getPermissionTree() {
        JSONObject tree = new JSONObject();
        List<Permission> permissionAllList = this.list(new LambdaQueryWrapper<Permission>().orderByAsc(Permission::getSort));
        List<Permission> menuList = permissionAllList.stream().filter(p -> p.getType() != 2).collect(Collectors.toList());

        List<PermissionTree> permissionAllTreeList = permissionAllList.stream().map(permission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission, permissionTree);
            permissionTree.setPid(permission.getParentId());
            permissionTree.setPids(permission.getParentIds());
            return permissionTree;
        }).collect(Collectors.toList());
        List<PermissionTree> permissionTrees = TreeUtil.list2Tree(permissionAllTreeList);
        tree.put("permissionTrees", permissionTrees);

        List<PermissionTree> menuAllTreeList = menuList.stream().map(permission -> {
            PermissionTree permissionTree = new PermissionTree();
            BeanUtils.copyProperties(permission, permissionTree);
            permissionTree.setPid(permission.getParentId());
            permissionTree.setPids(permission.getParentIds());
            return permissionTree;
        }).collect(Collectors.toList());
        List<PermissionTree> permsList = TreeUtil.list2Tree(menuAllTreeList);
        tree.put("permsList", permsList);
        return tree;
    }

    @CacheEvict(key = "'permission_tree'")
    @Override
    public void updatePermissionById(Permission permission) {
        this.updateById(permission);
    }

    @CacheEvict(key = "'permission_tree'")
    @Override
    public void savePermission(Permission permission) {
        this.save(permission);
    }

    @CacheEvict(key = "'permission_tree'")
    @Override
    public void removePermissionByIds(List<Long> ids) {
        this.removeByIds(ids);
    }


}
