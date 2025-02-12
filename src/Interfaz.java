import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class Interfaz {
    private JTabbedPane tabbedPane1;
    public JPanel panel1;
    private JTextField textFieldCodigoUnico;
    private JTextField textFieldCedula;
    private JTextField textFieldNombres;
    private JTextField textFieldApellidos;
    private JTextField textFieldCapital;
    private JComboBox<String> comboBoxProvincia;
    private JTextField textFieldFechaRegist;
    private JTextField textFieldPagoMatricula;
    private JCheckBox librosCheckBox;
    private JCheckBox certificadosCheckBox;
    private JCheckBox cursoDeIdiomasCheckBox;
    private JCheckBox asignaturaRepetidaCheckBox;
    private JRadioButton efectivoRadioButton;
    private JRadioButton depositoRadioButton;
    private JRadioButton transferenciaRadioButton;
    private JRadioButton tarjetaDeCreditoRadioButton;
    private JButton buttonGuardar;
    private JTextField textFieldFechaNaci;
    private JComboBox comboBoxQuintil;
    private JLabel LabelCodigoUnico;
    private JLabel LabelCedula;
    private JLabel LabelNombres;
    private JLabel LabelApellidos;
    private JLabel LabelProvincia;
    private JLabel LabelCapital;
    private JLabel LabelQuintil;
    private JLabel LabelFechaNaci;
    private JLabel LabelFechaRegist;
    private JLabel LabelPagoMatricula;
    private JLabel LabelSubtotal;
    private JLabel LabelTotal;
    private JTextField textFieldCorreo;
    private JTextField textFieldEdad;
    private JLabel labelCorreo;
    private JLabel LabelEdad;

    // Valores de los servicios adicionales
    private static final double VALOR_LIBROS = 10.0;
    private static final double VALOR_CERTIFICADOS = 40.0;
    private static final double VALOR_CURSO_IDIOMAS = 30.0;
    private static final double VALOR_ASIGNATURA_REPETIDA = 90.0;

    // Mapa de provincias y capitales
    private final Map<String, String> provinciasCapitales = new HashMap<>();


    // Conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3307/registro_matricula";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public Interfaz() {

        // Llenar el mapa con provincias y sus capitales
        provinciasCapitales.put("Azuay", "Cuenca");
        provinciasCapitales.put("Bolívar", "Guaranda");
        provinciasCapitales.put("Cañar", "Azogues");
        provinciasCapitales.put("Carchi", "Tulcán");
        provinciasCapitales.put("Chimborazo", "Riobamba");
        provinciasCapitales.put("Cotopaxi", "Latacunga");
        provinciasCapitales.put("El Oro", "Machala");
        provinciasCapitales.put("Esmeraldas", "Esmeraldas");
        provinciasCapitales.put("Galápagos", "Puerto Baquerizo Moreno");
        provinciasCapitales.put("Guayas", "Guayaquil");
        provinciasCapitales.put("Imbabura", "Ibarra");
        provinciasCapitales.put("Loja", "Loja");
        provinciasCapitales.put("Los Ríos", "Babahoyo");
        provinciasCapitales.put("Manabí", "Portoviejo");
        provinciasCapitales.put("Morona Santiago", "Macas");
        provinciasCapitales.put("Napo", "Tena");
        provinciasCapitales.put("Orellana", "Francisco de Orellana");
        provinciasCapitales.put("Pastaza", "Puyo");
        provinciasCapitales.put("Pichincha", "Quito");
        provinciasCapitales.put("Santa Elena", "Santa Elena");
        provinciasCapitales.put("Santo Domingo", "Santo Domingo");
        provinciasCapitales.put("Sucumbíos", "Nueva Loja");
        provinciasCapitales.put("Tungurahua", "Ambato");
        provinciasCapitales.put("Zamora Chinchipe", "Zamora");

        // Configurar el ComboBox con las provincias
        comboBoxProvincia.setModel(new DefaultComboBoxModel<>(provinciasCapitales.keySet().toArray(new String[0])));
        // Agregar ActionListener para actualizar automáticamente el campo de capital
        comboBoxProvincia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String provinciaSeleccionada = (String) comboBoxProvincia.getSelectedItem();
                if (provinciaSeleccionada != null) {
                    textFieldCapital.setText(provinciasCapitales.get(provinciaSeleccionada));
                }
            }
        });
        comboBoxQuintil.setModel(new DefaultComboBoxModel<>(new String[]{"1", "2", "3", "4", "5"}));

        // Evento para guardar en la base de datos
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatosEnBaseDeDatos();
            }
        });
    }
    // Método para calcular la edad a partir de la fecha de nacimiento
    private int calcularEdad(String fechaNacimiento) {
        try {
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento); // Formato: YYYY-MM-DD
            LocalDate fechaActual = LocalDate.now();
            return Period.between(fechaNac, fechaActual).getYears();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto. Usa YYYY-MM-DD.");
            return -1;
        }
    }

    // Método para guardar los datos en la base de datos
    private void guardarDatosEnBaseDeDatos() {
        String codigoUnico = textFieldCodigoUnico.getText();
        String cedula = textFieldCedula.getText();
        String nombres = textFieldNombres.getText();
        String apellidos = textFieldApellidos.getText();
        String provincia = comboBoxProvincia.getSelectedItem().toString();
        String capital = textFieldCapital.getText();
        String quintil = comboBoxQuintil.getSelectedItem().toString();
        String fechaNacimiento = textFieldFechaNaci.getText();
        String correo = textFieldCorreo.getText(); // Nuevo campo
        String edad = textFieldEdad.getText(); // Nuevo campo
        String fechaRegistro = textFieldFechaRegist.getText();
        String pagoMatricula = textFieldPagoMatricula.getText();


        // Calcular edad real a partir de la fecha de nacimiento
        int edadCalculada = calcularEdad(fechaNacimiento);
        if (edadCalculada == -1) {
            return; // Si hubo un error con la fecha, detener el proceso.
        }

        // Verificar si la edad ingresada coincide con la calculada
        int edadIngresada;
        try {
            edadIngresada = Integer.parseInt(edad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "La edad ingresada no es válida.");
            return;
        }

        if (edadCalculada != edadIngresada) {
            JOptionPane.showMessageDialog(null, "La edad ingresada no coincide con la fecha de nacimiento.");
            return;
        }

        // Verificar si es mayor o igual a 18 años
        if (edadCalculada < 18) {
            JOptionPane.showMessageDialog(null, "Debes ser mayor de 18 años para registrarte.");
            return;
        }

        // Obtener servicio adicional seleccionado
        double valorServicioAdicional = 0.0;
        String servicioAdicional = "No asignado"; // Valor por defecto
        if (librosCheckBox.isSelected()) {
            servicioAdicional = "Libros";
            valorServicioAdicional += VALOR_LIBROS;
        }
        if (certificadosCheckBox.isSelected()) {
            servicioAdicional = "Certificados";
            valorServicioAdicional += VALOR_CERTIFICADOS;
        }
        if (cursoDeIdiomasCheckBox.isSelected()) {
            servicioAdicional = "Curso de Inglés";
            valorServicioAdicional += VALOR_CURSO_IDIOMAS;
        }
        if (asignaturaRepetidaCheckBox.isSelected()) {
            servicioAdicional = "Asignatura Repetida";
            valorServicioAdicional += VALOR_ASIGNATURA_REPETIDA;
        }

        // Validar que el correo contenga un "@"
        if (!correo.contains("@")) {
            JOptionPane.showMessageDialog(null, "El correo electrónico debe contener '@'.");
            return; // Detener la ejecución si el correo no es válido
        }

        // Validar que se haya seleccionado una forma de pago
        String formaPago = "";
        if (efectivoRadioButton.isSelected()) formaPago = "Efectivo";
        if (depositoRadioButton.isSelected()) formaPago = "Deposito";
        if (transferenciaRadioButton.isSelected()) formaPago = "Transferencia";
        if (tarjetaDeCreditoRadioButton.isSelected()) formaPago = "Tarjeta de Crédito";

        if (formaPago.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una forma de pago.");
            return; // Detener la ejecución si no se selecciona ninguna forma de pago
        }
        // Calcular Subtotal y Total + IVA (ejemplo simple)
        double pagomatricula = Double.parseDouble(pagoMatricula);
        double subtotal = Double.parseDouble(pagoMatricula) + valorServicioAdicional;

        // Obtener el quintil y aplicar el porcentaje correspondiente
        double porcentaje = 0;
        switch (quintil) {
            case "5":
                porcentaje = 0.25; // 25%
                break;
            case "4":
                porcentaje = 0.20; // 20%
                break;
            case "3":
                porcentaje = 0.15; // 15%
                break;
            case "2":
                porcentaje = 0.10; // 10%
                break;
            case "1":
                porcentaje = 0.05; // 5%
                break;
            default:
                JOptionPane.showMessageDialog(null, "Quintil no válido.");
                return;
        }

        double porcentajeAplicado = subtotal * porcentaje;
        double nuevoSubtotal = subtotal + porcentajeAplicado;

        // Mostrar el nuevo subtotal y el total con IVA
        LabelSubtotal.setText("Subtotal: " + nuevoSubtotal);
        double totalConIVA = nuevoSubtotal * 1.12; // 12% de IVA
        LabelTotal.setText("Total: " + totalConIVA);


        // Conectar a la base de datos e insertar los datos
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO datosusuarios (id_CodigoUnico, Cedula, Nombres, Apellidos, Provincia, Capital, Quintil, " +
                    "FechaNacimiento, Correo, Edad, FechaRegistro, PagoMatricula, SevicioAdicional, FormaPago, Subtotal, Total) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(codigoUnico));
            preparedStatement.setInt(2, Integer.parseInt(cedula));
            preparedStatement.setString(3, nombres);
            preparedStatement.setString(4, apellidos);
            preparedStatement.setString(5, provincia);
            preparedStatement.setString(6, capital);
            preparedStatement.setString(7, quintil);
            preparedStatement.setDate(8, Date.valueOf(fechaNacimiento));
            preparedStatement.setString(9, correo); // Nuevo campo
            preparedStatement.setInt(10, edadIngresada); // Nuevo campo
            preparedStatement.setDate(11, Date.valueOf(fechaRegistro));
            preparedStatement.setDouble(12, pagomatricula);
            preparedStatement.setString(13, servicioAdicional);
            preparedStatement.setString(14, formaPago);
            preparedStatement.setDouble(15, nuevoSubtotal);
            preparedStatement.setDouble(16, totalConIVA);

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar los datos.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
