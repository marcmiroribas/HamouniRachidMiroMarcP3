package prog2.adaptador;



import prog2.model.Dades;
import prog2.vista.CentralUBException;
import prog2.model.PaginaIncidencies;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class Adaptador {
    private Dades dades;

    public Adaptador() {
        this.dades = new Dades();
    }

    public String mostraIncidencies() {
        StringBuilder sb = new StringBuilder();
        List<PaginaIncidencies> llista = dades.mostraIncidencies();
        Iterator<PaginaIncidencies> it = llista.iterator();

        while (it.hasNext()) {
            PaginaIncidencies inc = it.next();
            sb.append(inc.toString()).append("\n");
        }

        return sb.toString();
    }

    public void guardaDades(String camiDesti) throws CentralUBException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(camiDesti))) {
            out.writeObject(dades);
        } catch (IOException e) {
            throw new CentralUBException("Error al guardar les dades: " + e.getMessage(), e);
        }
    }

    public void carregaDades(String camiOrigen) throws CentralUBException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(camiOrigen))) {
            dades = (Dades) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CentralUBException("Error al carregar les dades: " + e.getMessage(), e);
        }
    }
}

