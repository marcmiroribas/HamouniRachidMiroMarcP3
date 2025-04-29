package prog2.model;

import java.util.ArrayList;
import java.util.List;

public class Bitacola implements InBitacola{
    private ArrayList<PaginaBitacola> paginesBit;

    @Override
    public void afegeixPagina(PaginaBitacola p) {
        this.paginesBit.add(p);
    }

    @Override
    public List<PaginaIncidencies> getIncidencies() {

    }
}
