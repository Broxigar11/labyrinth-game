package json;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Repository<T> {

    protected Class<T> elementType;

    protected List<T> elements;

    protected Repository(Class<T> elementType) {
        Logger.trace("Repository constructor is called");
        this.elementType = elementType;
        elements = new ArrayList<>();
    }

    public void add(T element) {
        Logger.trace("Adding element to repository");
        elements.add(element);
    }

    public List<T> findAll() {
        Logger.debug("findAll is called");
        return Collections.unmodifiableList(elements);
    }

}
