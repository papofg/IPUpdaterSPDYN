package es.aocana.updater.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import es.aocana.updater.entity.MyHosts;
 
public class WebUtils {
 
    public static String toString(User user) {
        StringBuilder sb = new StringBuilder();
 
        sb.append("UserName:").append(user.getUsername());
 
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            sb.append(" (");
            boolean first = true;
            for (GrantedAuthority a : authorities) {
                if (first) {
                    sb.append(a.getAuthority());
                    first = false;
                } else {
                    sb.append(", ").append(a.getAuthority());
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }
    
	public static boolean enviarPeticionActualizaDynDNS(MyHosts informacionHostPublicada, String ipActual) 
	{
      try 
      {
          URL url = new URL ("https://update.spdyn.de/nic/update?hostname="+informacionHostPublicada.getHostname()+"&myip="+ipActual);
          HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
          connection.setRequestMethod("GET");
          connection.setDoOutput(true);
          connection.setRequestProperty  ("Authorization", "Basic " + informacionHostPublicada.getEncodedLoginData());
          InputStream content = (InputStream)connection.getInputStream();
          BufferedReader in   = new BufferedReader (new InputStreamReader (content));
          String line;
          String salida = "";
          while ((line = in.readLine()) != null) {
              salida += line;
          }
          content.close();
          in.close();
          if(salida != null && salida.contains("good"))
          {
              System.out.println("Update the IP for dynamic DNS "+informacionHostPublicada.getHostname()+": OK ("+salida+")");
              return true;
          }
          else
          {
              System.out.println("Update the IP for dynamic DNS "+informacionHostPublicada.getHostname()+": KO ("+salida+")");
          }
      }
      catch (Throwable e)
      {
          e.printStackTrace();
      }
      return false;
	}
	
	
	public static String obtenerIpPublica()
	{
      try
      {
          URL url = new URL ("https://ifconfig.me");
          HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
          connection.setRequestMethod("GET");
          connection.setDoOutput(true);
          InputStream content = (InputStream)connection.getInputStream();
          InputStreamReader reader = new InputStreamReader (content);
          BufferedReader in   = new BufferedReader (reader);
          String line;
          String ipPublica = null;
          while ((line = in.readLine()) != null) {
              if(line != null && !"".equals(line))
              {
                   ipPublica = new String (line);
              }
          }
          content.close();
          in.close();
          if(ipPublica != null && !"".equals(ipPublica.trim()))
              return ipPublica;
          else
              return null;
      }
      catch (Throwable e)
      {
          e.printStackTrace();
      }
      
      return null;
	}
    

     
}
