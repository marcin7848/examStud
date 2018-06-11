package co.bdio.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class MyRepo
{
    public JdbcTemplate getJdbcTemplate() {
        return jdbc;
    }

    private JdbcTemplate jdbc;

    public MyRepo() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://ec2-107-20-226-93.compute-1.amazonaws.com:5432/d88s6a36fop901?sslmode=require");
        driverManagerDataSource.setUsername("vroajwprypcidq");
        driverManagerDataSource.setPassword("f359b2cdfe1c92913fe327495e7d189af29805512900dc63a1fd5b6b5358774e");
        this.jdbc = new JdbcTemplate(driverManagerDataSource);
    }



}
