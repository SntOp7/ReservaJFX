package co.edu.uniquindio.reservasfx.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;


public class EnvioEmail {

    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        Email email = EmailBuilder.startingBlank()
                .from("lbrtinstantaneo@gmail.com")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "lbrtinstantaneo@gmail.com", "ofjw roif nuhz mean")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enviarNotificacionConQR(String destinatario, String asunto, String mensaje, String codigoFactura) {
        try {
            byte[] qrBytes = generarQR(codigoFactura);

            String base64Qr = Base64.getEncoder().encodeToString(qrBytes);
            String imagenQrHtml = "<img src='data:image/png;base64," + base64Qr + "'/>";

            String mensajeHTML = "<html><body>" +
                    "<p>" + mensaje + "</p>" +
                    "<p><strong>Código de factura:</strong> " + codigoFactura + "</p>" +
                    "<p><strong>QR:</strong><br/>" + imagenQrHtml + "</p>" +
                    "</body></html>";

            Email email = EmailBuilder.startingBlank()
                    .from("lbrtinstantaneo@gmail.com")
                    .to(destinatario)
                    .withSubject(asunto)
                    .withHTMLText(mensajeHTML)
                    .buildEmail();

            Mailer mailer = MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "lbrtinstantaneo@gmail.com", "ofjw roif nuhz mean")
                    .withTransportStrategy(TransportStrategy.SMTP_TLS)
                    .withDebugLogging(true)
                    .buildMailer();

            mailer.sendMail(email);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] generarQR(String texto) throws Exception {
        int ancho = 250;
        int alto = 250;
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix matrix = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, ancho, alto, hints);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }
}
