package formulario_api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import formulario_api.entity.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findAllByOrderByFechaDesc();
    List<Venta> findByFechaBetweenOrderByFechaDesc(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha BETWEEN :desde AND :hasta")
    java.math.BigDecimal sumarTotalEntre(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v")
    java.math.BigDecimal sumarTotalGeneral();

    long countByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    @Query("SELECT v.metodoPago, COALESCE(SUM(v.total), 0) FROM Venta v GROUP BY v.metodoPago")
    List<Object[]> totalPorMetodoPago();

    List<Venta> findTop5ByOrderByFechaDesc();
}
