import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class server1 {
    public static void main(String[] args) {
        String tiempoInicio = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("Despliegue del servicio: " + tiempoInicio + "\n");

        Random num = new Random();
        int pid = num.nextInt(99999)+1;
        System.out.println("pid = " + pid);
        String pid_str = Integer.toString(pid);

        try {
            int port_send = 15001;
            int port_listen = 15000;
            String port_str = Integer.toString(port_send);
            String port_lis_str = Integer.toString(port_listen);

            System.out.println("port_str = " + port_str);
            System.out.println("port_lis_str = " + port_lis_str);

            DatagramSocket socket = new DatagramSocket();

            InetAddress host = InetAddress.getByName("localhost");
            String host_str = host.toString();
            System.out.println("host = " + host);

            Thread.sleep(3000);
            
//            String [] data = mensaje.split(" , ");
//
//            for(int i = 0; i < data.length; i++){
//                System.out.println(data[i]);
//            }

            System.out.println("Socket de envio iniciado!");

            byte [] buffer = new byte[100];

            String tiempoFin = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println("Fin del servicio: " + tiempoFin + "\n");

            String mensaje = host_str + " , " + port_lis_str + " , " + port_str + " , " + tiempoInicio + " , " + tiempoFin;
            System.out.println("mensaje = " + mensaje);

            byte [] msj_byte = mensaje.getBytes();

            DatagramPacket envio = new DatagramPacket(msj_byte, msj_byte.length, host, port_send);
            socket.send(envio);

            DatagramSocket socket1 = new DatagramSocket(port_listen);
            System.out.println("Socket de recepcion iniciado");
            while(true){

                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socket1.receive(peticion);

                System.out.println(peticion.getClass().getSimpleName());

                String recibido = new String(peticion.getData());
                System.out.println("Mensaje recibido " + recibido);

                String [] recibido_sep = recibido.split(" , ");

                Thread.sleep(3000);

                tiempoFin = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                System.out.println("Fin del servicio: " + tiempoFin + "\n");

                String nuevo_msj = recibido_sep[0] + " , " + port_lis_str +
                        " , " + port_str + " , " + pid_str + tiempoInicio +
                        " , " + tiempoFin;

                msj_byte = nuevo_msj.getBytes();

                envio = new DatagramPacket(msj_byte, msj_byte.length, host, port_send);
                socket.send(envio);

            }

        }catch (Exception e){
            System.out.println("Error tratando de iniciar la comunicacion");
        }
    }
}
