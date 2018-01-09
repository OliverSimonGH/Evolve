package com.nsa.evolve.dao.impl;

import com.nsa.evolve.dao.ModuleDAO;
import com.nsa.evolve.dto.Module;
import com.nsa.evolve.dto.ModuleJoin;
import com.nsa.evolve.dto.ModuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by c1633899 on 29/11/2017.
 */
@Repository
public class ModuleDAOImpl implements ModuleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Module> findAllModulesByCompany(Integer fkCompany) {
        return jdbcTemplate.query("SELECT * FROM module WHERE fk_company = ? AND deleted = 0",
                new Object[]{fkCompany},
                (rs, rowNum) -> new Module(
                        rs.getInt("id"),
                        rs.getInt("fk_company"),
                        rs.getInt("fk_module"),
                        rs.getInt("deleted")
                ));
    }

    public List<ModuleJoin> findAllModules(Integer fkCompany){
        return jdbcTemplate.query("SELECT mt.id, mt.name, m.id FROM module m JOIN moduletype mt ON mt.id = m.fk_module WHERE m.fk_company = ? AND m.deleted = 0",
                new Object[]{fkCompany},
                (rs, rowNum) -> new ModuleJoin(
                        rs.getInt("mt.id"),
                        rs.getString("mt.name"),
                        rs.getInt("m.id")
                ));
    }

    @Override
    public List<ModuleType> findModulesNotAdded(Integer fkCompany) {
        return jdbcTemplate.query("SELECT * FROM moduletype WHERE id IN (SELECT fk_module FROM module WHERE fk_company = ? AND deleted = 1)",
                new Object[]{fkCompany},
                (rs, rowNum) -> new ModuleType(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
    }

    @Override
    public ModuleJoin findModuleByID(Integer id) {
        return jdbcTemplate.queryForObject("SELECT mt.id, mt.name, m.id FROM module m JOIN moduletype mt ON mt.id = m.fk_module WHERE m.id = ?",
                new Object[]{id},
                (rs, rowNum) -> new ModuleJoin(
                        rs.getInt("mt.id"),
                        rs.getString("mt.name"),
                        rs.getInt("m.id")
                ));
    }

    @Override
    public int deleteModuleById(Integer id, Integer company) {
        return jdbcTemplate.update("UPDATE module SET deleted = 1 WHERE id = ? AND fk_company = ?", id, company);
    }

    @Override
    public int addModule(Integer module, Integer company) {
        return jdbcTemplate.update("UPDATE module SET deleted = 0 WHERE fk_module = ? AND fk_company = ?", module, company);

    }

    @Override
    public Integer getModuleScore(Integer modId) {

        Integer moduleScore;
        try {
            moduleScore = jdbcTemplate.queryForObject("SELECT ROUND(AVG(score)) FROM scores WHERE fk_module = ? AND fk_result IS NULL",
                    new Object[]{modId},Integer.class);
            return moduleScore * 20;
        } catch (NullPointerException e){
            return 0;
        }

    }

    @Override
    public String getModuleRealName(Integer modID){

        try {
            Integer ModuleID = jdbcTemplate.queryForObject("SELECT fk_module FROM module WHERE id = ?",
                    new Object[]{modID},Integer.class);

            String ModuleName = jdbcTemplate.queryForObject("SELECT name FROM moduletype WHERE id = ?",
                    new Object[]{ModuleID},String.class);

            return ModuleName;
        } catch (NullPointerException e){
            return "Module Name Not Found";
        }
    }
}
