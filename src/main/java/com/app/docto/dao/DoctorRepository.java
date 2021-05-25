package com.app.docto.dao;

import com.app.docto.beans.Doctor;
import com.app.docto.models.response.DoctorResp;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
    @Query(value = "select * from (select d.doctorid, d.firstname, d.lastname, d.experience, s.specialityname, t.tagname from doctor d, doctor_speciality ds, speciality s, tag t, speciality_tags st "+
            "where d.doctorid=ds.doctorid and ds.specialityid=s.specialityid and "+
            "s.specialityid=st.specialityid and st.tagid=t.tagid) res "+
            "where res.firstname like %:query% or res.specialityname like %:query% or res.tagname like %:query% " +
            "group by res.doctorid " +
            "order by res.experience",
            nativeQuery = true
    )
    List<Object[]> searchByQuery(@Param("query") String query);


    /*

     */
}
