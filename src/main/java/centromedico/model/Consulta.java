package centromedico.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Consulta {

    private int idConsulta;
    private LocalDate fecha;
    private Integer edad;
    private BigDecimal peso;
    private BigDecimal talla;
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private String alergias;
    private String vacunacion;
    private String antecedentes;
    private String observaciones;
    private HistoriaClinica historiaClinica;
    private Profesional profesional;

    public Consulta(
            int idConsulta,
            LocalDate fecha,
            Integer edad,
            BigDecimal peso,
            BigDecimal talla,
            String motivo,
            String diagnostico,
            String tratamiento,
            String alergias,
            String vacunacion,
            String antecedentes,
            String observaciones,
            HistoriaClinica historiaClinica,
            Profesional profesional
    ) {
        if (idConsulta < 0) {
            throw new IllegalArgumentException(
                    "El identificador no puede ser negativo."
            );
        }

        this.idConsulta = idConsulta;
        setFecha(fecha);
        setEdad(edad);
        setPeso(peso);
        setTalla(talla);
        setMotivo(motivo);
        setDiagnostico(diagnostico);
        setTratamiento(tratamiento);
        setAlergias(alergias);
        setVacunacion(vacunacion);
        setAntecedentes(antecedentes);
        setObservaciones(observaciones);
        setHistoriaClinica(historiaClinica);
        setProfesional(profesional);
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha de consulta es obligatoria."
            );
        }

        this.fecha = fecha;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        if (edad != null && edad < 0) {
            throw new IllegalArgumentException(
                    "La edad no puede ser negativa."
            );
        }

        this.edad = edad;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        if (peso != null && peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que cero."
            );
        }

        this.peso = peso;
    }

    public BigDecimal getTalla() {
        return talla;
    }

    public void setTalla(BigDecimal talla) {
        if (talla != null && talla.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "La talla debe ser mayor que cero."
            );
        }

        this.talla = talla;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = validarObligatorio(
                motivo, "El motivo de consulta es obligatorio."
        );
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = validarObligatorio(
                diagnostico, "El diagnóstico es obligatorio."
        );
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = validarObligatorio(
                tratamiento, "El tratamiento es obligatorio."
        );
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = normalizarOpcional(alergias);
    }

    public String getVacunacion() {
        return vacunacion;
    }

    public void setVacunacion(String vacunacion) {
        this.vacunacion = normalizarOpcional(vacunacion);
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = normalizarOpcional(antecedentes);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = normalizarOpcional(observaciones);
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        if (historiaClinica == null) {
            throw new IllegalArgumentException(
                    "La consulta debe pertenecer a una historia clínica."
            );
        }

        this.historiaClinica = historiaClinica;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        if (profesional == null) {
            throw new IllegalArgumentException(
                    "La consulta debe identificar a la profesional responsable."
            );
        }

        this.profesional = profesional;
    }

    private String validarObligatorio(
            String valor,
            String mensaje
    ) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }

        return valor.trim();
    }

    private String normalizarOpcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }

        return valor.trim();
    }

    @Override
    public String toString() {
        return "Consulta " + idConsulta
                + " | Fecha: " + fecha
                + " | Motivo: " + motivo
                + " | Profesional: "
                + profesional.getNombre() + " "
                + profesional.getApellido();
    }
}