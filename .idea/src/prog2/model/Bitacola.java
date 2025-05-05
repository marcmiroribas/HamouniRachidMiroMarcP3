package prog2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bitacola implements InBitacola {
    private ArrayList<PaginaBitacola> paginesBit;

    // Constructor
    public Bitacola() {
        this.paginesBit = new ArrayList<>();
    }

    @Override
    public void afegeixPagina(PaginaBitacola p) {
        this.paginesBit.add(p);
    }

    @Override
    public List<PaginaIncidencies> getIncidencies() {
        List<PaginaIncidencies> incidencies = new ArrayList<>();
        Iterator<PaginaBitacola> it = paginesBit.iterator();

        while (it.hasNext()) {
            PaginaBitacola p = it.next();
            if (p instanceof PaginaIncidencies) {
                incidencies.add((PaginaIncidencies) p);
            }
        }

        return incidencies;
    }
}


