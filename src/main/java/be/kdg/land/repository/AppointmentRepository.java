package be.kdg.land.repository;

import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.appointment.AppointmentType;
import be.kdg.land.repository.projection.AppointmentCountPerHour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {


    @Query(
            value = """
             SELECT *
             FROM land.appointments a
             WHERE to_char(CAST(a.slot as timestamp), 'YYYY-MM-DD.HH24') = to_char(CAST(:slotTime as timestamp), 'YYYY-MM-DD.HH24')
             """,
            nativeQuery = true
    )
    List<Appointment> findBySlot(@Param("slotTime") LocalDateTime slot);


    List<Appointment> findByLicensePlate(String licensePlate);

    @Query(
        value = """
        SELECT new be.kdg.land.repository.projection.AppointmentCountPerHour(a.slot, count(a.appointmentId))
        FROM Appointment a
        WHERE a.slot >= :fromTime
        GROUP BY a.slot
        """)
    List<AppointmentCountPerHour> CountAppointmentsPerSlot(LocalDateTime fromTime);

    List<Appointment> findAppointmentsByAppointmentTypeAndSlotGreaterThanEqualAndSlotBefore(AppointmentType type, LocalDateTime fromTime, LocalDateTime toTime);

    List<Appointment> findAppointmentsByAppointmentTypeAndSlotGreaterThanEqual(AppointmentType type, LocalDateTime fromTime);

}
